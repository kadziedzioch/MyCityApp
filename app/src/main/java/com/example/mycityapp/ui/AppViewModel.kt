package com.example.mycityapp.ui

import androidx.lifecycle.ViewModel
import com.example.mycityapp.data.DataProvider
import com.example.mycityapp.data.Recommendation
import com.example.mycityapp.data.RecommendationType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AppViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(AppUiState())
    val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()

    init {
        initializeState()
    }

    private fun initializeState() {
        var recommendations = DataProvider.recommendaions.groupBy { it.recommendationType }
        _uiState.value = AppUiState(
            recommendations = recommendations,
            currentRecommendation = recommendations[RecommendationType.Restaurant]?.get(0) ?:
            Recommendation(
                id = -1,
                name = 0,
                description = 0,
                img = 0
            )
        )
    }

    fun updateCurrentRecommendation(recommendation: Recommendation) {
        _uiState.update {
            it.copy(
                currentRecommendation = recommendation,
                isShowingMainPage = false
            )
        }
    }

    fun updateCurrentRecommendationType(recommendationType: RecommendationType){
        _uiState.update {
            it.copy(
                currentRecommendationType = recommendationType
            )
        }
    }

    fun resetCurrentState(){
        _uiState.update {
            it.copy(
                isShowingMainPage = true
            )
        }
    }



}