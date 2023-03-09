package com.example.mycityapp.data

import androidx.compose.ui.res.stringResource
import com.example.mycityapp.R

object DataProvider {

    val recommendaions = listOf(
        Recommendation(
            id = 1,
            name = R.string.paprocany_name,
            description = R.string.paprocany_desc,
            img = R.drawable.paprocany
        ),
        Recommendation(
            id = 2,
            name = R.string.park_gorniczy_name,
            description = R.string.park_gorniczy_desc,
            img = R.drawable.park_gorniczy
        ),
        Recommendation(
            id = 3,
            name = R.string.park_niedzwiadkow_name,
            description = R.string.park_niedzwiadkow_desc,
            img = R.drawable.park_niedzwiadkow
        ),
        Recommendation(
            id = 4,
            name = R.string.park_polnocny_name,
            description = R.string.park_polnocny_desc,
            img = R.drawable.park_polnocny
        ),
        Recommendation(
            id = 5,
            name = R.string.park_poludniowy_name,
            description = R.string.park_poludniowy_desc,
            img = R.drawable.park_poludniowy
        ),
        Recommendation(
            id = 6,
            name = R.string.suble_name,
            description = R.string.suble_desc,
            img = R.drawable.suble
        ),
        Recommendation(
            id = 7,
            name = R.string.avanti_name,
            description = R.string.avanti_desc,
            img = R.drawable.avanti,
            recommendationType = RecommendationType.Restaurant
        ),
        Recommendation(
            id = 8,
            name = R.string.bialy_obrus_name,
            description = R.string.bialy_obrus_desc,
            img = R.drawable.bialy_obrus,
            recommendationType = RecommendationType.Restaurant
        ),
        Recommendation(
            id = 9,
            name = R.string.dom_bawarski_desc,
            description = R.string.dom_bawarski_name,
            img = R.drawable.dom_bawarski,
            recommendationType = RecommendationType.Restaurant
        ),
        Recommendation(
            id = 10,
            name = R.string.stara_poczta_name,
            description = R.string.stara_poczta_desc,
            img = R.drawable.stara_poczta,
            recommendationType = RecommendationType.Restaurant
        )

    )
}