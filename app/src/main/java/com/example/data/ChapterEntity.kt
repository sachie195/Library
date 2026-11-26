package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chapters")
data class ChapterEntity(
    @PrimaryKey val id: String, // bookId_chapterIndex
    val bookId: String,
    val chapterIndex: Int,
    val title: String,
    val content: String,
    val wordCount: Int = 0
)
