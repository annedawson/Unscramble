package com.example.unscramble.ui

// UI state definition

data class GameUiState(
    val currentScrambledWord: String = "",
    val isGuessedWordWrong: Boolean = false,)

