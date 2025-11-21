package com.example.soundaction

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
class GameViewModel : ViewModel() {
    private val _tiles = mutableStateListOf<TileState>()
    val tiles: List<TileState> = _tiles

    val stepTimes = 2857
    val noteTime = stepTimes / 5
    private var scoreData:List<Note> = emptyList()
    private var startTime = 0L

    fun setScore(score: List<Note>) {
        scoreData = score
    }
    fun startGame(maxHeight: Dp) {
        startTime = System.currentTimeMillis()
        _tiles.clear()

        // Note から TileState を初期化（非アクティブ）
        scoreData.forEach { note ->
            val id = note.order.toLong() // order を id に使う
            _tiles.add(TileState(id, note.lane, 0.dp, false))
        }

        viewModelScope.launch {
            while (true) {
                val currentTime = System.currentTimeMillis() - startTime

                _tiles.forEachIndexed { index, tile ->
                    val note = scoreData.getOrNull(index) ?: return@forEachIndexed
                    val noteStart = note.order * noteTime
                    val noteEnd = noteStart + stepTimes

                    if (currentTime in noteStart..noteEnd) {
                        val progress = (currentTime - noteStart).toFloat() / stepTimes
                        val newY = maxHeight * progress
                        updateTile(index, newY, true)
                    } else if (currentTime > noteEnd && tile.isActive) {
                        updateTile(index, 0.dp, false)
                    }
                }

                delay(16) // 約60fps
            }
        }
    }

    private fun updateTile(index: Int, newY: Dp, active: Boolean) {
        val current = _tiles[index]
        if (current.y != newY || current.isActive != active) {
            _tiles[index] = current.copy(y = newY, isActive = active)
        }
    }

    fun deactivateTile(index: Int) {
        if (index in _tiles.indices) {
            _tiles[index] = _tiles[index].copy(isActive = false)
        }
    }
}

data class TileState(
    val id: Long,
    val lane: Int,
    val y: Dp,
    val isActive: Boolean
)