package com.example.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Insights
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.Insights
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.model.Screen
import com.example.ui.screens.BookDetailScreen
import com.example.ui.screens.DiscoverScreen
import com.example.ui.screens.LibraryScreen
import com.example.ui.screens.ReaderScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.screens.StatsScreen

enum class NavItem(
    val title: String,
    val selectedIcon: androidx.compose.ui.graphics.vector.ImageVector,
    val unselectedIcon: androidx.compose.ui.graphics.vector.ImageVector,
    val targetScreen: Screen
) {
    LIBRARY(
        title = "Library",
        selectedIcon = Icons.AutoMirrored.Filled.MenuBook,
        unselectedIcon = Icons.AutoMirrored.Outlined.MenuBook,
        targetScreen = Screen.Library
    ),
    DISCOVER(
        title = "Discover",
        selectedIcon = Icons.Default.Explore,
        unselectedIcon = Icons.Outlined.Explore,
        targetScreen = Screen.Discover
    ),
    STATS(
        title = "Insights",
        selectedIcon = Icons.Default.Insights,
        unselectedIcon = Icons.Outlined.Insights,
        targetScreen = Screen.Stats
    ),
    SETTINGS(
        title = "Settings",
        selectedIcon = Icons.Default.Settings,
        unselectedIcon = Icons.Outlined.Settings,
        targetScreen = Screen.Settings
    )
}

@Composable
fun FolioApp(viewModel: FolioViewModel = viewModel()) {
    val currentScreen by viewModel.currentScreen.collectAsStateWithLifecycle()
    val allBooks by viewModel.allBooks.collectAsStateWithLifecycle()
    val filteredBooks by viewModel.filteredBooks.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val selectedShelf by viewModel.selectedShelf.collectAsStateWithLifecycle()
    val selectedGenre by viewModel.selectedGenre.collectAsStateWithLifecycle()
    val sortOption by viewModel.sortOption.collectAsStateWithLifecycle()
    val isGridView by viewModel.isGridView.collectAsStateWithLifecycle()

    val selectedBook by viewModel.selectedBook.collectAsStateWithLifecycle()
    val selectedBookChapters by viewModel.selectedBookChapters.collectAsStateWithLifecycle()
    val selectedBookBookmarks by viewModel.selectedBookBookmarks.collectAsStateWithLifecycle()

    val readerBook by viewModel.readerBook.collectAsStateWithLifecycle()
    val readerChapters by viewModel.readerChapters.collectAsStateWithLifecycle()
    val currentChapter by viewModel.currentChapter.collectAsStateWithLifecycle()
    val readerChapterIndex by viewModel.readerChapterIndex.collectAsStateWithLifecycle()
    val readerSettings by viewModel.readerSettings.collectAsStateWithLifecycle()

    val allSessions by viewModel.allSessions.collectAsStateWithLifecycle()
    val totalMinutesRead by viewModel.totalMinutesRead.collectAsStateWithLifecycle()

    val isTopLevelScreen = currentScreen in listOf(
        Screen.Library,
        Screen.Discover,
        Screen.Stats,
        Screen.Settings
    )

    Scaffold(
        contentWindowInsets = WindowInsets(0.dp),
        bottomBar = {
            if (isTopLevelScreen) {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                    modifier = Modifier.testTag("main_bottom_nav")
                ) {
                    NavItem.values().forEach { item ->
                        val isSelected = currentScreen == item.targetScreen
                        NavigationBarItem(
                            selected = isSelected,
                            onClick = { viewModel.navigateTo(item.targetScreen) },
                            icon = {
                                Icon(
                                    imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                                    contentDescription = item.title
                                )
                            },
                            label = { Text(item.title) }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (val screen = currentScreen) {
                is Screen.Library -> {
                    LibraryScreen(
                        books = filteredBooks,
                        searchQuery = searchQuery,
                        selectedShelf = selectedShelf,
                        selectedGenre = selectedGenre,
                        sortOption = sortOption,
                        isGridView = isGridView,
                        onSearchChange = { viewModel.setSearchQuery(it) },
                        onShelfChange = { viewModel.setSelectedShelf(it) },
                        onGenreChange = { viewModel.setSelectedGenre(it) },
                        onSortChange = { viewModel.setSortOption(it) },
                        onToggleViewMode = { viewModel.toggleViewMode() },
                        onBookClick = { bookId ->
                            viewModel.navigateTo(Screen.BookDetail(bookId))
                        },
                        onContinueReading = { bookId, chapterIdx ->
                            viewModel.navigateTo(Screen.Reader(bookId, chapterIdx))
                        },
                        onToggleFavorite = { viewModel.toggleFavorite(it) },
                        onAddBook = { title, author, genre, desc, color, emoji, chTitle, chContent ->
                            viewModel.addNewCustomBook(
                                title, author, genre, desc, color, emoji, chTitle, chContent
                            ) { newBookId ->
                                viewModel.navigateTo(Screen.BookDetail(newBookId))
                            }
                        }
                    )
                }

                is Screen.Discover -> {
                    DiscoverScreen(
                        books = allBooks,
                        onBookClick = { bookId ->
                            viewModel.navigateTo(Screen.BookDetail(bookId))
                        },
                        onStartReading = { bookId, chapterIdx ->
                            viewModel.navigateTo(Screen.Reader(bookId, chapterIdx))
                        }
                    )
                }

                is Screen.Stats -> {
                    StatsScreen(
                        books = allBooks,
                        sessions = allSessions,
                        totalMinutesRead = totalMinutesRead
                    )
                }

                is Screen.Settings -> {
                    SettingsScreen(
                        settings = readerSettings,
                        onSettingsChanged = { viewModel.updateReaderSettings(it) }
                    )
                }

                is Screen.BookDetail -> {
                    BookDetailScreen(
                        book = selectedBook,
                        chapters = selectedBookChapters,
                        bookmarks = selectedBookBookmarks,
                        onBack = { viewModel.navigateBack() },
                        onStartReading = { chapterIdx ->
                            viewModel.navigateTo(Screen.Reader(screen.bookId, chapterIdx))
                        },
                        onUpdateShelf = { shelf ->
                            viewModel.updateShelf(screen.bookId, shelf)
                        },
                        onToggleFavorite = {
                            selectedBook?.let { viewModel.toggleFavorite(it) }
                        },
                        onUpdateRating = { rating ->
                            viewModel.updateRating(screen.bookId, rating)
                        },
                        onDeleteBook = {
                            viewModel.deleteBook(screen.bookId)
                        }
                    )
                }

                is Screen.Reader -> {
                    ReaderScreen(
                        book = readerBook,
                        chapters = readerChapters,
                        currentChapter = currentChapter,
                        chapterIndex = readerChapterIndex,
                        bookmarks = selectedBookBookmarks,
                        settings = readerSettings,
                        onSettingsChanged = { viewModel.updateReaderSettings(it) },
                        onBack = { viewModel.navigateBack() },
                        onNextChapter = { viewModel.nextChapter() },
                        onPreviousChapter = { viewModel.previousChapter() },
                        onChapterSelect = { chapterIdx ->
                            viewModel.loadChapter(screen.bookId, chapterIdx)
                        },
                        onScrollProgressChanged = { progress ->
                            viewModel.updateScrollProgress(progress)
                        },
                        onAddBookmark = { snippet, note ->
                            viewModel.addBookmark(snippet, note)
                        },
                        onDeleteBookmark = { id ->
                            viewModel.deleteBookmark(id)
                        }
                    )
                }
            }
        }
    }
}
