package com.example.ui.components

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import java.util.Locale

class FolioTtsManager(context: Context) {
    private var tts: TextToSpeech? = null
    private var isInitialized = false

    val isSpeaking = mutableStateOf(false)
    val speechRate = mutableFloatStateOf(1.0f)

    init {
        tts = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                tts?.language = Locale.getDefault()
                isInitialized = true
                tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                    override fun onStart(utteranceId: String?) {
                        isSpeaking.value = true
                    }

                    override fun onDone(utteranceId: String?) {
                        isSpeaking.value = false
                    }

                    override fun onError(utteranceId: String?) {
                        isSpeaking.value = false
                    }
                })
            }
        }
    }

    fun speak(text: String) {
        if (!isInitialized) return
        stop()
        tts?.setSpeechRate(speechRate.floatValue)
        // Clean text of markdown or special characters
        val clean = text.replace(Regex("[#*_]"), "").take(4000)
        tts?.speak(clean, TextToSpeech.QUEUE_FLUSH, null, "folio_reading_utterance")
        isSpeaking.value = true
    }

    fun stop() {
        tts?.stop()
        isSpeaking.value = false
    }

    fun setRate(rate: Float) {
        speechRate.floatValue = rate
        tts?.setSpeechRate(rate)
    }

    fun shutdown() {
        stop()
        tts?.shutdown()
        tts = null
    }
}

@Composable
fun rememberTtsManager(): FolioTtsManager {
    val context = LocalContext.current
    val manager = remember { FolioTtsManager(context) }

    DisposableEffect(Unit) {
        onDispose {
            manager.shutdown()
        }
    }

    return manager
}
