package com.ihexep.presentation.common

sealed class Screen(val route: String) {
    object SearchScreen : Screen("search_screen")
    object DownloadsScreen : Screen("downloads_screen")
}