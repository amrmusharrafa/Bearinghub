package com.example.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ui.screens.SearchScreen
import com.example.viewmodel.SearchViewModel

object Destinations {
    const val SEARCH_ROUTE = "search"
}

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Destinations.SEARCH_ROUTE,
        modifier = modifier
    ) {
        composable(Destinations.SEARCH_ROUTE) {
            val viewModel: SearchViewModel = viewModel(factory = SearchViewModel.Factory)
            SearchScreen(viewModel = viewModel)
        }
    }
}
