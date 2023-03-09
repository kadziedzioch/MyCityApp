package com.example.mycityapp.ui

import com.example.mycityapp.data.Recommendation
import com.example.mycityapp.data.RecommendationType

data class AppUiState(
    val currentRecommendation : Recommendation = Recommendation(-1,0,0,0),
    val currentRecommendationType: RecommendationType = RecommendationType.Restaurant,
    val isShowingMainPage : Boolean = true,
    val recommendations : Map<RecommendationType, List<Recommendation>> = emptyMap())
{
    val currentRecommendations : List<Recommendation>  by lazy { recommendations[currentRecommendationType]!! }
}


