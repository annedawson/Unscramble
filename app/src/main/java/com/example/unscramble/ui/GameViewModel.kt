package com.example.unscramble.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.unscramble.data.allWords
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

// in build.gradle(Module: app) add the following line to dependencies:
// implementation "androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1"

// https://developer.android.com/codelabs/basic-android-kotlin-compose-viewmodel-and-state?continue=https%3A%2F%2Fdeveloper.android.com%2Fcourses%2Fpathways%2Fandroid-basics-compose-unit-4-pathway-1%23codelab-https%3A%2F%2Fdeveloper.android.com%2Fcodelabs%2Fbasic-android-kotlin-compose-viewmodel-and-state#4

class GameViewModel : ViewModel() {
    // Game UI state
    private val _uiState = MutableStateFlow(GameUiState()) // backing property
    //uiState is public
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow() // property - represented as read only
    private lateinit var currentWord: String
    // Set of words used in the game
    private var usedWords: MutableSet<String> = mutableSetOf()
    var userGuess by mutableStateOf("")
        private set

    init {
        resetGame()
    }


    fun resetGame() {
        usedWords.clear()
        _uiState.value = GameUiState(currentScrambledWord = pickRandomWordAndShuffle())
    }

    fun updateUserGuess(guessedWord: String){
        userGuess = guessedWord
    }
    fun checkUserGuess() {

        // import kotlinx.coroutines.flow.update
        if (userGuess.equals(currentWord, ignoreCase = true)) {
        } else {
            // User's guess is wrong, show an error
            _uiState.update { currentState ->
                currentState.copy(isGuessedWordWrong = true)
            }
        }
        // Reset user guess
        updateUserGuess("")
    }



    // helper functions should be private -
    private fun pickRandomWordAndShuffle(): String {
        // Continue picking up a new random word until you get one that hasn't been used before
        currentWord = allWords.random()
        if (usedWords.contains(currentWord)) {
            return pickRandomWordAndShuffle()
        } else {
            usedWords.add(currentWord)
            return shuffleCurrentWord(currentWord)
        }
    }

    private fun shuffleCurrentWord(word: String): String {
        val tempWord = word.toCharArray()
        // Scramble the word
        tempWord.shuffle()
        while (String(tempWord).equals(word)) {
            tempWord.shuffle()
        }
        return String(tempWord)
    }

}
