package com.example.ui.model

import androidx.compose.ui.graphics.Color

enum class ReaderTheme(
    val title: String,
    val backgroundColor: Color,
    val textColor: Color,
    val accentColor: Color,
    val surfaceColor: Color
) {
    PARCHMENT(
        title = "Parchment",
        backgroundColor = Color(0xFFFBF8F1),
        textColor = Color(0xFF262421),
        accentColor = Color(0xFF8B5CF6),
        surfaceColor = Color(0xFFF1EAD9)
    ),
    SEPIA(
        title = "Sepia",
        backgroundColor = Color(0xFFF4ECD8),
        textColor = Color(0xFF433422),
        accentColor = Color(0xFFB45309),
        surfaceColor = Color(0xFFE8DCC0)
    ),
    SLATE(
        title = "Slate Dark",
        backgroundColor = Color(0xFF1E222A),
        textColor = Color(0xFFE2E8F0),
        accentColor = Color(0xFF818CF8),
        surfaceColor = Color(0xFF282F3B)
    ),
    MIDNIGHT(
        title = "Midnight OLED",
        backgroundColor = Color(0xFF000000),
        textColor = Color(0xFFCBD5E1),
        accentColor = Color(0xFF60A5FA),
        surfaceColor = Color(0xFF18181B)
    ),
    SAGE(
        title = "Forest Sage",
        backgroundColor = Color(0xFF1E2E25),
        textColor = Color(0xFFD6E6DB),
        accentColor = Color(0xFF34D399),
        surfaceColor = Color(0xFF283D32)
    )
}

enum class ReaderFont(val displayName: String) {
    SERIF("Classic Serif"),
    SANS_SERIF("Modern Sans"),
    MONOSPACE("Clean Mono"),
    CURSIVE("Literary Script")
}

data class ReaderSettings(
    val theme: ReaderTheme = ReaderTheme.PARCHMENT,
    val fontSizeSp: Float = 18f,
    val lineSpacingMultiplier: Float = 1.6f,
    val font: ReaderFont = ReaderFont.SERIF,
    val horizontalPaddingDp: Int = 20,
    val isZenMode: Boolean = false
)

sealed interface Screen {
    data object Library : Screen
    data object Discover : Screen
    data object Stats : Screen
    data object Settings : Screen
    data class BookDetail(val bookId: String) : Screen
    data class Reader(val bookId: String, val initialChapterIndex: Int = 0) : Screen
}
