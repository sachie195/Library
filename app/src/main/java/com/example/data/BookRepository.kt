package com.example.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.util.UUID

class BookRepository(private val bookDao: BookDao) {

    val allBooks: Flow<List<BookEntity>> = bookDao.getAllBooks()
    val allBookmarks: Flow<List<BookmarkEntity>> = bookDao.getAllBookmarks()
    val allSessions: Flow<List<ReadingSessionEntity>> = bookDao.getAllSessions()
    val totalMinutesRead: Flow<Int> = bookDao.getTotalMinutesRead()

    suspend fun initializeDefaultDataIfNeeded() = withContext(Dispatchers.IO) {
        val count = bookDao.getBooksCount()
        if (count == 0) {
            bookDao.insertBooks(DefaultBooksData.getDefaultBooks())
            bookDao.insertChapters(DefaultBooksData.getDefaultChapters())
            // Insert a few initial sample reading sessions for realistic stats
            val now = System.currentTimeMillis()
            bookDao.insertSession(
                ReadingSessionEntity(
                    bookId = "gatsby",
                    bookTitle = "The Great Gatsby",
                    minutesRead = 24,
                    timestamp = now - 3600000L * 2
                )
            )
            bookDao.insertSession(
                ReadingSessionEntity(
                    bookId = "sherlock_holmes",
                    bookTitle = "A Study in Scarlet",
                    minutesRead = 18,
                    timestamp = now - 86400000L
                )
            )
            bookDao.insertSession(
                ReadingSessionEntity(
                    bookId = "alice_in_wonderland",
                    bookTitle = "Alice in Wonderland",
                    minutesRead = 35,
                    timestamp = now - 86400000L * 2
                )
            )
            // Insert a sample bookmark
            bookDao.insertBookmark(
                BookmarkEntity(
                    bookId = "gatsby",
                    chapterIndex = 0,
                    chapterTitle = "Chapter I: West Egg",
                    scrollProgress = 0.15f,
                    snippet = "Whenever you feel like criticizing any one, just remember...",
                    note = "Classic opening advice from father"
                )
            )
        }
    }

    fun getBookById(id: String): Flow<BookEntity?> = bookDao.getBookById(id)

    suspend fun getBookByIdSync(id: String): BookEntity? = withContext(Dispatchers.IO) {
        bookDao.getBookByIdSync(id)
    }

    fun getChaptersForBook(bookId: String): Flow<List<ChapterEntity>> =
        bookDao.getChaptersForBook(bookId)

    suspend fun getChapter(bookId: String, chapterIndex: Int): ChapterEntity? =
        withContext(Dispatchers.IO) {
            bookDao.getChapter(bookId, chapterIndex)
        }

    fun getBookmarksForBook(bookId: String): Flow<List<BookmarkEntity>> =
        bookDao.getBookmarksForBook(bookId)

    suspend fun updateReadingProgress(
        bookId: String,
        chapterIndex: Int,
        scrollProgress: Float,
        shelf: BookShelf = BookShelf.READING
    ) = withContext(Dispatchers.IO) {
        bookDao.updateReadingProgress(
            bookId = bookId,
            chapterIndex = chapterIndex,
            scrollProgress = scrollProgress,
            lastRead = System.currentTimeMillis(),
            shelf = shelf
        )
    }

    suspend fun toggleFavorite(bookId: String, isFavorite: Boolean) = withContext(Dispatchers.IO) {
        bookDao.updateFavorite(bookId, isFavorite)
    }

    suspend fun updateShelf(bookId: String, shelf: BookShelf) = withContext(Dispatchers.IO) {
        bookDao.updateShelf(bookId, shelf)
    }

    suspend fun updateRating(bookId: String, rating: Int) = withContext(Dispatchers.IO) {
        bookDao.updateRating(bookId, rating)
    }

    suspend fun addBookmark(
        bookId: String,
        chapterIndex: Int,
        chapterTitle: String,
        scrollProgress: Float,
        snippet: String,
        note: String
    ) = withContext(Dispatchers.IO) {
        bookDao.insertBookmark(
            BookmarkEntity(
                bookId = bookId,
                chapterIndex = chapterIndex,
                chapterTitle = chapterTitle,
                scrollProgress = scrollProgress,
                snippet = snippet,
                note = note
            )
        )
    }

    suspend fun deleteBookmark(id: Long) = withContext(Dispatchers.IO) {
        bookDao.deleteBookmark(id)
    }

    suspend fun logReadingSession(bookId: String, bookTitle: String, minutes: Int) =
        withContext(Dispatchers.IO) {
            if (minutes > 0) {
                bookDao.insertSession(
                    ReadingSessionEntity(
                        bookId = bookId,
                        bookTitle = bookTitle,
                        minutesRead = minutes
                    )
                )
            }
        }

    suspend fun addNewCustomBook(
        title: String,
        author: String,
        genre: String,
        description: String,
        coverColorHex: String,
        coverEmoji: String,
        chapterTitle: String,
        chapterContent: String
    ): String = withContext(Dispatchers.IO) {
        val bookId = "custom_" + UUID.randomUUID().toString().take(8)
        val wordCount = chapterContent.split(Regex("\\s+")).filter { it.isNotBlank() }.size
        val estimatedMins = (wordCount / 200).coerceAtLeast(1)

        val book = BookEntity(
            id = bookId,
            title = title.ifBlank { "Untitled Book" },
            author = author.ifBlank { "Anonymous" },
            genre = genre.ifBlank { "Personal" },
            description = description.ifBlank { "A custom story added to your personal library." },
            coverColorHex = coverColorHex,
            coverEmoji = coverEmoji,
            shelf = BookShelf.READING,
            isFavorite = false,
            currentChapterIndex = 0,
            scrollProgress = 0f,
            totalChapters = 1,
            wordsCount = wordCount,
            estimatedMinutes = estimatedMins,
            lastReadTimestamp = System.currentTimeMillis(),
            rating = 5,
            isCustom = true
        )
        val chapter = ChapterEntity(
            id = "${bookId}_0",
            bookId = bookId,
            chapterIndex = 0,
            title = chapterTitle.ifBlank { "Chapter I" },
            content = chapterContent,
            wordCount = wordCount
        )

        bookDao.insertBook(book)
        bookDao.insertChapter(chapter)
        bookId
    }

    suspend fun deleteBook(bookId: String) = withContext(Dispatchers.IO) {
        bookDao.deleteBookById(bookId)
        bookDao.deleteChaptersForBook(bookId)
    }
}
