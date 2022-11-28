package com.ihexep.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.core.view.WindowCompat
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.ihexep.presentation.common.Screen
import com.ihexep.presentation.features.downloads.DownloadsScreen
import com.ihexep.presentation.features.search.SearchScreen
import com.ihexep.presentation.theme.GithubRepositoryFinderTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_GithubRepositoryFinder)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            GithubRepositoryFinderTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberAnimatedNavController()
                    AnimatedNavHost(
                        navController = navController,
                        startDestination = Screen.SearchScreen.route
                    ) {
                        composable(route = Screen.SearchScreen.route,
                            enterTransition = { slideInHorizontally() },
                            exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) }
                        ) {
                            SearchScreen(navController)
                        }
                        composable(route = Screen.DownloadsScreen.route,
                            enterTransition = { slideInHorizontally(initialOffsetX = { it }) },
                            exitTransition = { slideOutHorizontally(targetOffsetX = { it }) }
                        ) {
                            DownloadsScreen(navController)
                        }
                    }
                }
            }
        }
    }
}