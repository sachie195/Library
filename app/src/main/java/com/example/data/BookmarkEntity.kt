package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarks")
data class BookmarkEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val bookId: String,
    val chapterIndex: Int,
    val chapterTitle: String,
    val scrollProgress: Float = 0f,
    val snippet: String,
    val note: String = "",
    val timestamp: Long = System.currentTimeMillis()
)
