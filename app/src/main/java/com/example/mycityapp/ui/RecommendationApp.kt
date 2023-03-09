package com.example.mycityapp.ui

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.NavigationRail
import androidx.compose.material.NavigationRailItem
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.currentCompositionErrors
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mycityapp.R
import com.example.mycityapp.data.DataProvider
import com.example.mycityapp.data.Recommendation
import com.example.mycityapp.data.RecommendationType
import com.example.mycityapp.ui.theme.MyCityAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecommendationScreen(
    navigationItemContentList: List<NavigationItemContent>,
    uiState: AppUiState,
    navigationType: ReplyNavigationType,
    contentType: ReplyContentType,
    onNavClicked : (RecommendationType) -> Unit,
    onTabClicked : (Recommendation) -> Unit,
    onBackPressed: () -> Unit
){
    androidx.compose.material.Scaffold(
        bottomBar = {
            if(navigationType ==  ReplyNavigationType.BOTTOM_NAVIGATION)
            {
                BottomNavBar(
                    navigationItemContentList = navigationItemContentList,
                    onClick = onNavClicked,
                    currentType = uiState.currentRecommendationType
                )
            }
        }
    ) { padding ->

        if(contentType == ReplyContentType.LIST_ONLY){
            Row(modifier = Modifier.padding(padding)) {
                if(navigationType ==  ReplyNavigationType.NAVIGATION_RAIL){
                    navRail(
                        navigationItemContentList = navigationItemContentList,
                        currentType = uiState.currentRecommendationType,
                        onNavRailClicked = onNavClicked
                    )
                }
                if(uiState.isShowingMainPage){
                    RecommendationListView(
                        recommendations = uiState.currentRecommendations,
                        modifier = Modifier.padding(padding),
                        navigationType = navigationType,
                        onRecommendationClicked = onTabClicked
                    )
                }
                else{
                    RecommendationDetailsView(
                        recommendation = uiState.currentRecommendation,
                        modifier = Modifier.padding(padding),
                        onBackPressed = onBackPressed
                    )
                }
            }
        }
        else{

            val activity = LocalContext.current as Activity
            if(navigationType ==  ReplyNavigationType.PERMANENT_NAVIGATION_DRAWER){
                PermanentNavigationDrawer(
                    drawerContent = {
                        PermanentDrawerSheet(Modifier.width(240.dp)) {
                            NavDrawer(
                                onNavClicked = onNavClicked,
                                currentType = uiState.currentRecommendationType,
                                navigationItemContentList = navigationItemContentList
                            )
                        }
                    }
                ) {

                    RecommendationListDetailView(
                        recommendations = uiState.currentRecommendations,
                        navigationType = navigationType,
                        recommendation = uiState.currentRecommendation,
                        modifier = Modifier.padding(padding),
                        onRecommendationClicked = onTabClicked,
                        onBackPressed = {activity.finish()}
                    )
                  }
            }
        }

    }
}


@Composable
fun RecommendationApp(
    windowSize: WindowWidthSizeClass
)
{
    val viewModel: AppViewModel = viewModel()
    val navigationItemContentList = listOf(
        NavigationItemContent(
            recommendationType = RecommendationType.Park,
            icon = Icons.Default.Place,
            text = stringResource(id = R.string.park_name)
        ),
        NavigationItemContent(
            recommendationType = RecommendationType.Restaurant,
            icon = Icons.Filled.Place,
            text = stringResource(id = R.string.restaurant_name)
        )
    )
    val uiState = viewModel.uiState.collectAsState().value

    val navigationType : ReplyNavigationType
    val contentType: ReplyContentType
    when (windowSize) {
        WindowWidthSizeClass.Compact -> {
            navigationType = ReplyNavigationType.BOTTOM_NAVIGATION
            contentType = ReplyContentType.LIST_ONLY
        }
        WindowWidthSizeClass.Medium -> {
            navigationType = ReplyNavigationType.NAVIGATION_RAIL
            contentType = ReplyContentType.LIST_ONLY
        }
        WindowWidthSizeClass.Expanded -> {
            navigationType = ReplyNavigationType.PERMANENT_NAVIGATION_DRAWER
            contentType = ReplyContentType.LIST_AND_DETAIL
        }
        else -> {
            navigationType = ReplyNavigationType.BOTTOM_NAVIGATION
            contentType = ReplyContentType.LIST_ONLY
        }
    }

    RecommendationScreen(
        navigationItemContentList = navigationItemContentList,
        uiState = uiState,
        navigationType = navigationType,
        contentType = contentType,
        onNavClicked = {
            viewModel.updateCurrentRecommendationType(it)
        },
        onTabClicked = {
            viewModel.updateCurrentRecommendation(it)
        },
        onBackPressed = {
            viewModel.resetCurrentState()
        }
    )
}

@Composable
fun RecommendationListDetailView(
    modifier: Modifier = Modifier,
    recommendations: List<Recommendation>,
    navigationType: ReplyNavigationType,
    recommendation: Recommendation,
    onRecommendationClicked: (Recommendation) -> Unit,
    onBackPressed: () -> Unit
){
    Row(modifier = modifier) {
        RecommendationListView(
            recommendations = recommendations,
            navigationType = navigationType,
            modifier = Modifier.weight(1f),
            onRecommendationClicked = onRecommendationClicked
        )
        RecommendationDetailsView(
            recommendation = recommendation,
            modifier = Modifier.weight(1f),
            onBackPressed = onBackPressed
        )
    }
    
}


@Composable
fun RecommendationListView(
    modifier: Modifier = Modifier,
    recommendations : List<Recommendation>,
    navigationType: ReplyNavigationType,
    onRecommendationClicked : (Recommendation) -> Unit
){
   LazyColumn(modifier = modifier){
       items(recommendations, key = {recommendation -> recommendation.id}){
           recommendation ->
           RecommendationListItem(
               recommendation = recommendation,
               onCardClicked = {
                   onRecommendationClicked(recommendation)
               }
           )
       }
   }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RecommendationListItem(
    recommendation: Recommendation,
    modifier: Modifier = Modifier,
    onCardClicked : () -> Unit
){
    Card(
        elevation = 4.dp,
        modifier = modifier
            .padding(10.dp),
        onClick = onCardClicked
    ) {
        Column(modifier = Modifier.padding(10.dp)) {

            Image(
                painter = painterResource(id = recommendation.img),
                contentDescription = null,
                modifier = Modifier.clip(CutCornerShape(topEnd = 40.dp))
            )

            Text(
                text = stringResource(id = recommendation.name),
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                textAlign = TextAlign.Center,

            )
        }
    }

}

@Composable
private fun BottomNavBar(
    navigationItemContentList: List<NavigationItemContent>,
    modifier: Modifier = Modifier,
    onClick: (RecommendationType) -> Unit,
    currentType: RecommendationType
){
    BottomNavigation(modifier = modifier.fillMaxWidth()) {
        for(navItem in navigationItemContentList){
            BottomNavigationItem(
                selected = currentType == navItem.recommendationType,
                onClick = {onClick(navItem.recommendationType)},
                icon = {
                    Icon(
                        imageVector = navItem.icon,
                        contentDescription = navItem.text)
                }
            )
        }
    }
}

@Composable
private fun navRail(
    navigationItemContentList: List<NavigationItemContent>,
    modifier: Modifier = Modifier,
    onNavRailClicked: (RecommendationType) -> Unit,
    currentType: RecommendationType
)
{
    NavigationRail(modifier = modifier.fillMaxHeight()) {
        for(navItem in navigationItemContentList){
            NavigationRailItem(
                selected = currentType == navItem.recommendationType,
                onClick = {onNavRailClicked(navItem.recommendationType)},
                icon = {
                    Icon(
                        imageVector = navItem.icon,
                        contentDescription = navItem.text)
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NavDrawer(
    navigationItemContentList: List<NavigationItemContent>,
    modifier: Modifier = Modifier,
    onNavClicked: (RecommendationType) -> Unit,
    currentType: RecommendationType
){
    Column(
        modifier
            .wrapContentWidth()
            .fillMaxHeight()
            .background(androidx.compose.material3.MaterialTheme.colorScheme.inverseOnSurface)
            .padding(12.dp)
    ) {

        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.h3,
            color = MaterialTheme.colors.onSurface,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            textAlign = TextAlign.Center,
        )

        for (navItem in navigationItemContentList) {
            NavigationDrawerItem(
                selected = currentType == navItem.recommendationType,
                label = {
                    androidx.compose.material3.Text(
                        text = navItem.text,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                },
                icon = {
                    androidx.compose.material3.Icon(
                        imageVector = navItem.icon,
                        contentDescription = navItem.text
                    )
                },
                colors = NavigationDrawerItemDefaults.colors(
                    unselectedContainerColor = Color.Transparent
                ),
                onClick = {onNavClicked(navItem.recommendationType)}
            )
        }
    }



}


@Composable
fun RecommendationDetailsView(
    recommendation: Recommendation,
    modifier: Modifier = Modifier,
    onBackPressed : ()-> Unit
){
    val scrollState = rememberScrollState()
    Column(modifier = modifier
        .padding(10.dp)
        .verticalScroll(scrollState)
    ) {
        Image(
            painter = painterResource(id = recommendation.img),
            contentDescription = null,
            modifier = Modifier.clip(CutCornerShape(topEnd = 30.dp))
        )
        Text(
            text = stringResource(id = recommendation.name),
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.onSurface,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            textAlign = TextAlign.Center,
            )
        Text(
            text = stringResource(id = recommendation.description),
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.onSurface,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            textAlign = TextAlign.Justify
            )

    }
    BackHandler() {
        onBackPressed()
    }

}




@Preview(showBackground = true)
@Composable
fun RecommendationListViewPreview(){
    MyCityAppTheme() {
        RecommendationListView(
            recommendations = DataProvider.recommendaions,
            navigationType = ReplyNavigationType.BOTTOM_NAVIGATION,
            onRecommendationClicked = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RecommendationDetailsViewPreview(){
    MyCityAppTheme() {
        RecommendationDetailsView(
            recommendation = DataProvider.recommendaions[0],
            onBackPressed = {}
        )
    }
}

data class NavigationItemContent(
    val recommendationType: RecommendationType,
    val icon: ImageVector,
    val text: String
)

enum class ReplyNavigationType {
    BOTTOM_NAVIGATION, NAVIGATION_RAIL, PERMANENT_NAVIGATION_DRAWER
}

enum class ReplyContentType {
    LIST_ONLY, LIST_AND_DETAIL
}
