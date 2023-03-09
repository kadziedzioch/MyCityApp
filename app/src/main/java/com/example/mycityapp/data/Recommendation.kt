package com.example.mycityapp.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Recommendation(
    val id: Int,
    @StringRes val name: Int,
    @StringRes val description: Int,
    @DrawableRes val img: Int,
    val recommendationType: RecommendationType = RecommendationType.Park
)