package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class BookShelf(val displayName: String) {
    READING("Currently Reading"),
    WANT_TO_READ("Want to Read"),
    FINISHED("Finished"),
    UNREAD("To Explore")
}

@Entity(tableName = "books")
data class BookEntity(
    @PrimaryKey val id: String,
    val title: String,
    val author: String,
    val genre: String,
    val description: String,
    val coverColorHex: String,
    val coverEmoji: String,
    val shelf: BookShelf = BookShelf.UNREAD,
    val isFavorite: Boolean = false,
    val currentChapterIndex: Int = 0,
    val scrollProgress: Float = 0f,
    val totalChapters: Int = 1,
    val wordsCount: Int = 0,
    val estimatedMinutes: Int = 0,
    val lastReadTimestamp: Long = 0L,
    val rating: Int = 0,
    val isCustom: Boolean = false
) {
    val progressPercentage: Int
        get() {
            if (totalChapters <= 0) return 0
            if (shelf == BookShelf.FINISHED) return 100
            val chapterFraction = (currentChapterIndex.toFloat() + scrollProgress.coerceIn(0f, 1f)) / totalChapters.toFloat()
            return (chapterFraction * 100).toInt().coerceIn(0, 100)
        }
}
