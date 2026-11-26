package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.BookmarkAdd
import androidx.compose.material.icons.filled.FormatListNumbered
import androidx.compose.material.icons.filled.NavigateBefore
import androidx.compose.material.icons.filled.NavigateNext
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.BookEntity
import com.example.data.BookmarkEntity
import com.example.data.ChapterEntity
import com.example.ui.components.BookmarksSheet
import com.example.ui.components.ReaderControlsSheet
import com.example.ui.components.TableOfContentsSheet
import com.example.ui.components.rememberTtsManager
import com.example.ui.model.ReaderFont
import com.example.ui.model.ReaderSettings
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun ReaderScreen(
    book: BookEntity?,
    chapters: List<ChapterEntity>,
    currentChapter: ChapterEntity?,
    chapterIndex: Int,
    bookmarks: List<BookmarkEntity>,
    settings: ReaderSettings,
    onSettingsChanged: (ReaderSettings) -> Unit,
    onBack: () -> Unit,
    onNextChapter: () -> Unit,
    onPreviousChapter: () -> Unit,
    onChapterSelect: (Int) -> Unit,
    onScrollProgressChanged: (Float) -> Unit,
    onAddBookmark: (snippet: String, note: String) -> Unit,
    onDeleteBookmark: (Long) -> Unit
) {
    BackHandler { onBack() }

    val tts = rememberTtsManager()
    DisposableEffect(Unit) {
        onDispose {
            tts.stop()
        }
    }

    // Toggle HUD controls on user screen tap
    var isControlsVisible by remember { mutableStateOf(true) }
    var showControlsSheet by remember { mutableStateOf(false) }
    var showTocSheet by remember { mutableStateOf(false) }
    var showBookmarksSheet by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    // Observe scroll progress and report to ViewModel
    LaunchedEffect(scrollState) {
        snapshotFlow {
            if (scrollState.maxValue > 0) {
                scrollState.value.toFloat() / scrollState.maxValue.toFloat()
            } else 0f
        }.distinctUntilChanged().collect { progress ->
            onScrollProgressChanged(progress)
        }
    }

    // Reset scroll when chapter changes
    LaunchedEffect(chapterIndex) {
        scrollState.scrollTo(0)
    }

    val currentTheme = settings.theme
    val fontFamily = remember(settings.font) {
        when (settings.font) {
            ReaderFont.SERIF -> FontFamily.Serif
            ReaderFont.SANS_SERIF -> FontFamily.SansSerif
            ReaderFont.MONOSPACE -> FontFamily.Monospace
            ReaderFont.CURSIVE -> FontFamily.Cursive
        }
    }

    val progressFraction by remember {
        derivedStateOf {
            if (scrollState.maxValue > 0) {
                (scrollState.value.toFloat() / scrollState.maxValue.toFloat()).coerceIn(0f, 1f)
            } else 0f
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(currentTheme.backgroundColor)
            .testTag("reader_screen")
    ) {
        // Scrollable reading content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    isControlsVisible = !isControlsVisible
                }
                .padding(
                    start = settings.horizontalPaddingDp.dp,
                    end = settings.horizontalPaddingDp.dp,
                    top = 80.dp,
                    bottom = 120.dp
                )
        ) {
            // Chapter Header
            Text(
                text = currentChapter?.title ?: "Chapter ${chapterIndex + 1}",
                fontSize = (settings.fontSizeSp * 1.35f).sp,
                fontWeight = FontWeight.Bold,
                fontFamily = fontFamily,
                color = currentTheme.textColor,
                lineHeight = (settings.fontSizeSp * 1.6f).sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // TTS playback indicator bar
            if (tts.isSpeaking.value) {
                Surface(
                    color = currentTheme.accentColor.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.VolumeUp,
                            contentDescription = null,
                            tint = currentTheme.accentColor,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Reading aloud...",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = currentTheme.accentColor
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = "${tts.speechRate.floatValue}x",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = currentTheme.accentColor
                        )
                    }
                }
            }

            // Paragraphs of the Chapter
            val content = currentChapter?.content ?: "Loading chapter..."
            val paragraphs = remember(content) {
                content.split("\n\n").filter { it.isNotBlank() }
            }

            paragraphs.forEach { paragraph ->
                Text(
                    text = paragraph.trim(),
                    fontSize = settings.fontSizeSp.sp,
                    fontFamily = fontFamily,
                    color = currentTheme.textColor,
                    lineHeight = (settings.fontSizeSp * settings.lineSpacingMultiplier).sp,
                    modifier = Modifier.padding(bottom = (settings.fontSizeSp * 0.8f).dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // End of Chapter Navigation Card
            Card(
                colors = CardDefaults.cardColors(containerColor = currentTheme.surfaceColor),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Text(
                        text = if (chapterIndex + 1 < chapters.size) "End of Chapter ${chapterIndex + 1}" else "End of Book",
                        fontWeight = FontWeight.Bold,
                        color = currentTheme.textColor,
                        fontSize = 14.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        if (chapterIndex > 0) {
                            Surface(
                                color = currentTheme.accentColor.copy(alpha = 0.2f),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .clickable { onPreviousChapter() }
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                                ) {
                                    Icon(imageVector = Icons.Default.NavigateBefore, contentDescription = null, tint = currentTheme.textColor)
                                    Text("Previous", color = currentTheme.textColor, fontSize = 13.sp)
                                }
                            }
                        } else {
                            Spacer(modifier = Modifier.width(1.dp))
                        }

                        if (chapterIndex + 1 < chapters.size) {
                            Surface(
                                color = currentTheme.accentColor,
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .clickable { onNextChapter() }
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                                ) {
                                    Text("Next Chapter", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                                    Icon(imageVector = Icons.Default.NavigateNext, contentDescription = null, tint = Color.White)
                                }
                            }
                        }
                    }
                }
            }
        }

        // TOP HUD BAR
        AnimatedVisibility(
            visible = isControlsVisible,
            enter = slideInVertically(initialOffsetY = { -it }) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { -it }) + fadeOut(),
            modifier = Modifier.align(Alignment.TopCenter)
        ) {
            Surface(
                color = currentTheme.surfaceColor.copy(alpha = 0.95f),
                shadowElevation = 6.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    IconButton(onClick = onBack, modifier = Modifier.testTag("reader_back_button")) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = currentTheme.textColor
                        )
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = book?.title ?: "Reader",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Serif,
                            color = currentTheme.textColor,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "Chapter ${chapterIndex + 1} of ${chapters.size}",
                            style = MaterialTheme.typography.bodySmall,
                            color = currentTheme.textColor.copy(alpha = 0.7f)
                        )
                    }

                    // Top Action Icons
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        // TTS Button
                        IconButton(
                            onClick = {
                                if (tts.isSpeaking.value) {
                                    tts.stop()
                                } else {
                                    val textToRead = currentChapter?.content ?: ""
                                    tts.speak(textToRead)
                                }
                            },
                            modifier = Modifier.testTag("tts_button")
                        ) {
                            Icon(
                                imageVector = if (tts.isSpeaking.value) Icons.Default.Stop else Icons.Default.RecordVoiceOver,
                                contentDescription = "Text to speech",
                                tint = if (tts.isSpeaking.value) currentTheme.accentColor else currentTheme.textColor
                            )
                        }

                        // Bookmark Button
                        IconButton(onClick = { showBookmarksSheet = true }) {
                            Icon(
                                imageVector = Icons.Default.BookmarkAdd,
                                contentDescription = "Bookmarks",
                                tint = currentTheme.textColor
                            )
                        }

                        // Appearance Controls
                        IconButton(
                            onClick = { showControlsSheet = true },
                            modifier = Modifier.testTag("appearance_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Palette,
                                contentDescription = "Reader Settings",
                                tint = currentTheme.textColor
                            )
                        }
                    }
                }
            }
        }

        // BOTTOM HUD BAR
        AnimatedVisibility(
            visible = isControlsVisible,
            enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { it }) + fadeOut(),
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Surface(
                color = currentTheme.surfaceColor.copy(alpha = 0.95f),
                shadowElevation = 8.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    // Chapter Scrubber / Progress
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "${(progressFraction * 100).toInt()}%",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = currentTheme.textColor
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Slider(
                            value = progressFraction,
                            onValueChange = { targetFraction ->
                                val targetScroll = (targetFraction * scrollState.maxValue).toInt()
                                // Note: Coroutine scope can be triggered or instant scroll
                            },
                            colors = SliderDefaults.colors(
                                thumbColor = currentTheme.accentColor,
                                activeTrackColor = currentTheme.accentColor,
                                inactiveTrackColor = currentTheme.textColor.copy(alpha = 0.2f)
                            ),
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Ch. ${chapterIndex + 1}/${chapters.size}",
                            fontSize = 11.sp,
                            color = currentTheme.textColor.copy(alpha = 0.7f)
                        )
                    }

                    // Navigation Buttons
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        IconButton(
                            onClick = onPreviousChapter,
                            enabled = chapterIndex > 0
                        ) {
                            Icon(
                                imageVector = Icons.Default.NavigateBefore,
                                contentDescription = "Previous Chapter",
                                tint = if (chapterIndex > 0) currentTheme.textColor else currentTheme.textColor.copy(alpha = 0.3f)
                            )
                        }

                        // Table of Contents trigger
                        Surface(
                            color = currentTheme.accentColor.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .clickable { showTocSheet = true }
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.FormatListNumbered,
                                    contentDescription = null,
                                    tint = currentTheme.accentColor,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Chapters",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = currentTheme.accentColor
                                )
                            }
                        }

                        IconButton(
                            onClick = onNextChapter,
                            enabled = chapterIndex + 1 < chapters.size
                        ) {
                            Icon(
                                imageVector = Icons.Default.NavigateNext,
                                contentDescription = "Next Chapter",
                                tint = if (chapterIndex + 1 < chapters.size) currentTheme.textColor else currentTheme.textColor.copy(alpha = 0.3f)
                            )
                        }
                    }
                }
            }
        }

        // Bottom Sheets
        if (showControlsSheet) {
            ReaderControlsSheet(
                settings = settings,
                onSettingsChanged = onSettingsChanged,
                onDismiss = { showControlsSheet = false }
            )
        }

        if (showTocSheet) {
            TableOfContentsSheet(
                chapters = chapters,
                currentChapterIndex = chapterIndex,
                onChapterSelected = { onChapterSelect(it) },
                onDismiss = { showTocSheet = false }
            )
        }

        if (showBookmarksSheet) {
            BookmarksSheet(
                bookmarks = bookmarks,
                currentChapterSnippet = currentChapter?.content?.take(150) ?: "",
                onAddBookmark = onAddBookmark,
                onBookmarkClicked = { bookmark ->
                    onChapterSelect(bookmark.chapterIndex)
                },
                onDeleteBookmark = onDeleteBookmark,
                onDismiss = { showBookmarksSheet = false }
            )
        }
    }
}
