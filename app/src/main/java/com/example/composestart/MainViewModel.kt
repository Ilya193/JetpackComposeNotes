package com.example.composestart

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

data class Note(
    private val id: Int,
    var text: String,
    var isFavorite: Boolean = false,
)

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    private var list = mutableListOf<Note>()
    private val _words = MutableStateFlow<List<Note>>(listOf())
    val words: MutableStateFlow<List<Note>> get() = _words

    private var id = 0

    fun insert(textInput: String) {
        val newNote = Note(id++, textInput)
        list.add(newNote)
        val temp = list.map { it.copy() }
        _words.value = temp
    }

    fun delete(index: Int) {
        list.removeAt(index)
        val temp = list.map { it.copy() }
        _words.value = temp
    }

    fun setFavorite(index: Int) {
        list[index] = list[index].copy(isFavorite = !list[index].isFavorite)
        list.sortBy { !it.isFavorite }
        val temp = list.map { it.copy() }
        _words.value = temp
    }
}