package com.example.composestart

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.Collections
import javax.inject.Inject

data class Note(
    private val id: Int,
    var text: String,
    var isFavorite: Boolean = false
)

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    private var listWords = mutableStateListOf<Note>()
    private val _words = MutableStateFlow<List<Note>>(listWords)
    val words: StateFlow<List<Note>> get() = _words

    private var id = 0
    private var indexSwap = 0

    fun insert(textInput: String) {
        val newNote = Note(id++, textInput)
        listWords.add(newNote)
    }

    fun delete(index: Int) {
        listWords.removeAt(index)
    }

    fun setFavorite(index: Int) {
        listWords[index] = listWords[index].copy(isFavorite = !listWords[index].isFavorite)
        Collections.swap(listWords, index, indexSwap++)
    }
}