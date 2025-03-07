package com.lpg.unscrambleapp.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.lpg.unscrambleapp.data.MAX_NO_OF_WORDS
import com.lpg.unscrambleapp.data.allWords
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import com.lpg.unscrambleapp.data.SCORE_INCREASE

class GameViewModel : ViewModel() {
    //    De esta manera nos aseguramos que el unico que manipule este objeto es esta clase
    private val _uiState = MutableStateFlow(GameUiState())

    //    Inmutable que solo sirve como getter, es lo que voy  usar fuera de esta clase
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()
    private lateinit var currentWord: String
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
        val words = word.split(" ")
        val shuffledWords = words.map { shuffleWord(it) }
        return shuffledWords.joinToString(" ")
    }

    private fun shuffleWord(word: String): String {
        val tempWord = word.toCharArray()
        tempWord.shuffle()
        while (String(tempWord) == word) {
            tempWord.shuffle()
        }
        return String(tempWord)
    }

    fun updateUserGuess(userInput: String) {
        userGuess = userInput
    }

    fun checkUserGuess() {
        if (userGuess.equals(currentWord, ignoreCase = true)) {
            _uiState.update { currentState ->
                currentState.copy(isGuessedWordWrong = false)
            }
            val updatedScore = _uiState.value.score.plus(SCORE_INCREASE)
            updateGameState(updatedScore)
        } else {
            _uiState.update { currentState ->
                currentState.copy(isGuessedWordWrong = true)
            }
            // Reset user guess
        }
        updateUserGuess("")
    }

    private fun updateGameState(updatedScore: Int) {
        if (usedWords.size == MAX_NO_OF_WORDS){
            //Last round in the game, update isGameOver to true, don't pick a new word
            _uiState.update { currentState ->
                currentState.copy(
                    isGuessedWordWrong = false,
                    score = updatedScore,
                    isGameOver = true
                )
            }
        } else{
            // Normal round in the game
            _uiState.update { currentState ->
                currentState.copy(
                    isGuessedWordWrong = false,
                    currentScrambledWord = pickRandomWordAndShuffle(),
                    currentWordCount = currentState.currentWordCount.inc(),
                    score = updatedScore
                )
            }
        }
    }

    fun skipWord() {
        updateGameState(_uiState.value.score)
        // Reset user guess
        updateUserGuess("")
    }
}