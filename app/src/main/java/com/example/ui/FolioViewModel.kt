package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.AppDatabase
import com.example.data.BookEntity
import com.example.data.BookmarkEntity
import com.example.data.BookRepository
import com.example.data.BookShelf
import com.example.data.ChapterEntity
import com.example.data.ReadingSessionEntity
import com.example.ui.model.ReaderSettings
import com.example.ui.model.Screen
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class BookSortOption(val label: String) {
    RECENT("Recently Read"),
    TITLE("Title A-Z"),
    AUTHOR("Author"),
    PROGRESS("Highest Progress")
}

class FolioViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: BookRepository

    init {
        val database = AppDatabase.getDatabase(application)
        repository = BookRepository(database.bookDao())
        viewModelScope.launch {
            repository.initializeDefaultDataIfNeeded()
        }
    }

    // Navigation state with back stack
    private val backStack = mutableListOf<Screen>(Screen.Library)
    private val _currentScreen = MutableStateFlow<Screen>(Screen.Library)
    val currentScreen: StateFlow<Screen> = _currentScreen.asStateFlow()

    // Library filtering & searching
    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _selectedShelf = MutableStateFlow<BookShelf?>(null) // null = all
    val selectedShelf = _selectedShelf.asStateFlow()

    private val _selectedGenre = MutableStateFlow<String?>(null)
    val selectedGenre = _selectedGenre.asStateFlow()

    private val _sortOption = MutableStateFlow(BookSortOption.RECENT)
    val sortOption = _sortOption.asStateFlow()

    private val _isGridView = MutableStateFlow(true)
    val isGridView = _isGridView.asStateFlow()

    val allBooks = repository.allBooks.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val allBookmarks = repository.allBookmarks.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val allSessions = repository.allSessions.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val totalMinutesRead = repository.totalMinutesRead.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 0
    )

    // Filtered books flow
    val filteredBooks: StateFlow<List<BookEntity>> = combine(
        allBooks,
        _searchQuery,
        _selectedShelf,
        _selectedGenre,
        _sortOption
    ) { books, query, shelf, genre, sort ->
        var list = books

        if (query.isNotBlank()) {
            val q = query.trim().lowercase()
            list = list.filter {
                it.title.lowercase().contains(q) ||
                    it.author.lowercase().contains(q) ||
                    it.genre.lowercase().contains(q) ||
                    it.description.lowercase().contains(q)
            }
        }

        if (shelf != null) {
            list = list.filter { it.shelf == shelf }
        }

        if (genre != null) {
            list = list.filter { it.genre.equals(genre, ignoreCase = true) }
        }

        when (sort) {
            BookSortOption.RECENT -> list.sortedByDescending { it.lastReadTimestamp }
            BookSortOption.TITLE -> list.sortedBy { it.title.lowercase() }
            BookSortOption.AUTHOR -> list.sortedBy { it.author.lowercase() }
            BookSortOption.PROGRESS -> list.sortedByDescending { it.progressPercentage }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    // Currently Selected Book for Detail Screen
    private val _selectedBookId = MutableStateFlow<String?>(null)
    val selectedBookId = _selectedBookId.asStateFlow()

    val selectedBook: StateFlow<BookEntity?> = _selectedBookId.flatMapLatest { id ->
        if (id == null) flowOf(null) else repository.getBookById(id)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    val selectedBookChapters: StateFlow<List<ChapterEntity>> = _selectedBookId.flatMapLatest { id ->
        if (id == null) flowOf(emptyList()) else repository.getChaptersForBook(id)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val selectedBookBookmarks: StateFlow<List<BookmarkEntity>> = _selectedBookId.flatMapLatest { id ->
        if (id == null) flowOf(emptyList()) else repository.getBookmarksForBook(id)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    // Reader Screen State
    private val _readerBookId = MutableStateFlow<String?>(null)
    val readerBookId = _readerBookId.asStateFlow()

    private val _readerChapterIndex = MutableStateFlow(0)
    val readerChapterIndex = _readerChapterIndex.asStateFlow()

    private val _readerScrollProgress = MutableStateFlow(0f)
    val readerScrollProgress = _readerScrollProgress.asStateFlow()

    private val _readerSettings = MutableStateFlow(ReaderSettings())
    val readerSettings = _readerSettings.asStateFlow()

    val readerBook: StateFlow<BookEntity?> = _readerBookId.flatMapLatest { id ->
        if (id == null) flowOf(null) else repository.getBookById(id)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    val readerChapters: StateFlow<List<ChapterEntity>> = _readerBookId.flatMapLatest { id ->
        if (id == null) flowOf(emptyList()) else repository.getChaptersForBook(id)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    private val _currentChapter = MutableStateFlow<ChapterEntity?>(null)
    val currentChapter = _currentChapter.asStateFlow()

    // Reading Timer
    private var readingTimerJob: Job? = null
    private var secondsInCurrentSession = 0

    fun navigateTo(screen: Screen) {
        if (_currentScreen.value == screen) return
        backStack.add(screen)
        _currentScreen.value = screen

        when (screen) {
            is Screen.BookDetail -> {
                _selectedBookId.value = screen.bookId
                stopReadingTimer()
            }
            is Screen.Reader -> {
                setupReader(screen.bookId, screen.initialChapterIndex)
                startReadingTimer()
            }
            else -> {
                stopReadingTimer()
            }
        }
    }

    fun navigateBack(): Boolean {
        if (backStack.size > 1) {
            backStack.removeAt(backStack.lastIndex)
            val prev = backStack.last()
            _currentScreen.value = prev

            when (prev) {
                is Screen.BookDetail -> {
                    _selectedBookId.value = prev.bookId
                    stopReadingTimer()
                }
                is Screen.Reader -> {
                    setupReader(prev.bookId, prev.initialChapterIndex)
                    startReadingTimer()
                }
                else -> {
                    stopReadingTimer()
                }
            }
            return true
        }
        return false
    }

    private fun setupReader(bookId: String, initialChapterIndex: Int) {
        _readerBookId.value = bookId
        _readerChapterIndex.value = initialChapterIndex
        viewModelScope.launch {
            val book = repository.getBookByIdSync(bookId)
            val chapterIdx = if (initialChapterIndex != 0) initialChapterIndex else (book?.currentChapterIndex ?: 0)
            _readerChapterIndex.value = chapterIdx
            loadChapter(bookId, chapterIdx)
        }
    }

    fun loadChapter(bookId: String, chapterIndex: Int) {
        viewModelScope.launch {
            _readerChapterIndex.value = chapterIndex
            val chapter = repository.getChapter(bookId, chapterIndex)
            _currentChapter.value = chapter
            repository.updateReadingProgress(bookId, chapterIndex, _readerScrollProgress.value)
        }
    }

    fun nextChapter() {
        val bookId = _readerBookId.value ?: return
        val currentIdx = _readerChapterIndex.value
        val total = readerChapters.value.size
        if (currentIdx + 1 < total) {
            _readerScrollProgress.value = 0f
            loadChapter(bookId, currentIdx + 1)
        } else {
            // Reached end of book!
            viewModelScope.launch {
                repository.updateShelf(bookId, BookShelf.FINISHED)
            }
        }
    }

    fun previousChapter() {
        val bookId = _readerBookId.value ?: return
        val currentIdx = _readerChapterIndex.value
        if (currentIdx > 0) {
            _readerScrollProgress.value = 0f
            loadChapter(bookId, currentIdx - 1)
        }
    }

    fun updateScrollProgress(progress: Float) {
        _readerScrollProgress.value = progress
        val bookId = _readerBookId.value ?: return
        val chapterIdx = _readerChapterIndex.value
        viewModelScope.launch {
            repository.updateReadingProgress(bookId, chapterIdx, progress)
        }
    }

    private fun startReadingTimer() {
        stopReadingTimer()
        secondsInCurrentSession = 0
        readingTimerJob = viewModelScope.launch {
            while (true) {
                delay(1000L)
                secondsInCurrentSession++
                // Automatically log a minute of reading every 60 seconds
                if (secondsInCurrentSession % 60 == 0) {
                    val book = readerBook.value
                    if (book != null) {
                        repository.logReadingSession(book.id, book.title, 1)
                    }
                }
            }
        }
    }

    private fun stopReadingTimer() {
        readingTimerJob?.cancel()
        readingTimerJob = null
        if (secondsInCurrentSession >= 30) {
            val book = readerBook.value
            if (book != null) {
                viewModelScope.launch {
                    val minutes = (secondsInCurrentSession / 60).coerceAtLeast(1)
                    repository.logReadingSession(book.id, book.title, minutes)
                }
            }
        }
        secondsInCurrentSession = 0
    }

    // Filter controls
    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setSelectedShelf(shelf: BookShelf?) {
        _selectedShelf.value = shelf
    }

    fun setSelectedGenre(genre: String?) {
        _selectedGenre.value = genre
    }

    fun setSortOption(sort: BookSortOption) {
        _sortOption.value = sort
    }

    fun toggleViewMode() {
        _isGridView.value = !_isGridView.value
    }

    // Actions
    fun toggleFavorite(book: BookEntity) {
        viewModelScope.launch {
            repository.toggleFavorite(book.id, !book.isFavorite)
        }
    }

    fun updateShelf(bookId: String, shelf: BookShelf) {
        viewModelScope.launch {
            repository.updateShelf(bookId, shelf)
        }
    }

    fun updateRating(bookId: String, rating: Int) {
        viewModelScope.launch {
            repository.updateRating(bookId, rating)
        }
    }

    fun updateReaderSettings(settings: ReaderSettings) {
        _readerSettings.value = settings
    }

    fun addBookmark(snippet: String, note: String) {
        val bookId = _readerBookId.value ?: return
        val chapterIdx = _readerChapterIndex.value
        val chapterTitle = _currentChapter.value?.title ?: "Chapter ${chapterIdx + 1}"
        viewModelScope.launch {
            repository.addBookmark(
                bookId = bookId,
                chapterIndex = chapterIdx,
                chapterTitle = chapterTitle,
                scrollProgress = _readerScrollProgress.value,
                snippet = snippet,
                note = note
            )
        }
    }

    fun deleteBookmark(id: Long) {
        viewModelScope.launch {
            repository.deleteBookmark(id)
        }
    }

    fun addNewCustomBook(
        title: String,
        author: String,
        genre: String,
        description: String,
        coverColorHex: String,
        coverEmoji: String,
        chapterTitle: String,
        chapterContent: String,
        onCreated: (String) -> Unit
    ) {
        viewModelScope.launch {
            val bookId = repository.addNewCustomBook(
                title = title,
                author = author,
                genre = genre,
                description = description,
                coverColorHex = coverColorHex,
                coverEmoji = coverEmoji,
                chapterTitle = chapterTitle,
                chapterContent = chapterContent
            )
            onCreated(bookId)
        }
    }

    fun deleteBook(bookId: String) {
        viewModelScope.launch {
            repository.deleteBook(bookId)
            if (_selectedBookId.value == bookId) {
                navigateBack()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        stopReadingTimer()
    }
}
