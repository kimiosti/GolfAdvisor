package com.example.golfadvisor.ui.screens.scoring_trend

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.yml.charts.common.model.Point
import com.example.golfadvisor.data.database.entities.User
import com.example.golfadvisor.data.repository.ScoringTrendRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class ScoringTrendState(
    val userInfo: User?,
    val scorePoints: List<Point>,
    val maxScore: Float?,
    val minScore: Float?
)

interface ScoringTrendAction {
    fun loadScores(username: String)
}

class ScoringTrendViewModel(private val scoringTrendRepository: ScoringTrendRepository): ViewModel() {
    private val _state = MutableStateFlow(ScoringTrendState(
        userInfo = null,
        scorePoints = listOf(),
        maxScore = null,
        minScore = null
    ))
    val state = _state.asStateFlow()

    val actions = object : ScoringTrendAction {
        override fun loadScores(username: String) {
            viewModelScope.launch {
                val user = scoringTrendRepository.getUserInfo(username)
                val scores = scoringTrendRepository.getLatestReviewsWithScore(username = username, num = 10)

                var maxScore: Float? = null
                var minScore: Float? = null
                var i = 1
                val scorePoints = ArrayList<Point>()
                scores.forEach {

                    val score = it.score!! * it.accessType.scoreMultiplier
                    // score is guaranteed to be non-null by the query

                    scorePoints.add(Point(
                        x = i.toFloat(),
                        y = score
                    ))

                    if (maxScore == null || score > maxScore!!) {
                        maxScore = score
                    }
                    if (minScore == null || score < minScore!!) {
                        minScore = score
                    }

                    i++
                }
                _state.value = ScoringTrendState(
                    userInfo = user,
                    scorePoints = scorePoints,
                    maxScore = maxScore,
                    minScore = minScore
                )
            }
        }
    }
}