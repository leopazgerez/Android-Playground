package com.lpg.lunchtrayapp.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.lpg.lunchtrayapp.ui.viewmodel.OrderViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.lpg.lunchtrayapp.R
import com.lpg.lunchtrayapp.data.DataSource
import com.lpg.lunchtrayapp.enum.ScreenEnum
import com.lpg.lunchtrayapp.ui.screen.AccompanimentMenuScreen
import com.lpg.lunchtrayapp.ui.screen.CheckoutScreen
import com.lpg.lunchtrayapp.ui.screen.EntreeMenuScreen
import com.lpg.lunchtrayapp.ui.screen.SideDishMenuScreen
import com.lpg.lunchtrayapp.ui.screen.StartOrderScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LunchTrayAppBar(
    title: String,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(title) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

@Composable
fun LunchTrayApp() {
    val navController: NavHostController = rememberNavController()
    val viewModel: OrderViewModel = viewModel()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = ScreenEnum.valueOf(
        backStackEntry?.destination?.route ?: ScreenEnum.Start.name
    )
    Scaffold(
        topBar = {
            LunchTrayAppBar(
                title = stringResource(currentScreen.title),
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() })
        }
    ) { innerPadding ->
        val uiState by viewModel.uiState.collectAsState()

        // TODO: Navigation host
        NavHost(
            navController = navController,
            startDestination = ScreenEnum.Start.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = ScreenEnum.Start.name) {
                StartOrderScreen(
                    onStartOrderButtonClicked = {
                        navController.navigate(ScreenEnum.Entree.name)
                    },
                    modifier = Modifier
                        .padding(dimensionResource(R.dimen.padding_medium))
                        .fillMaxSize()
                )
            }
            composable(route = ScreenEnum.Entree.name) {
                EntreeMenuScreen(
                    options = DataSource.entreeMenuItems,
                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(viewModel, navController)
                    },
                    onNextButtonClicked = {
                        navController.navigate(ScreenEnum.SideDish.name)
                    },
                    onSelectionChanged = {
                        viewModel.updateEntree(it)
                    },
                )
            }
            composable(route = ScreenEnum.SideDish.name) {
                SideDishMenuScreen(
                    options = DataSource.sideDishMenuItems,
                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(viewModel, navController)
                    },
                    onNextButtonClicked = {
                        navController.navigate(ScreenEnum.Accompaniment.name)
                    },
                    onSelectionChanged = {
                        viewModel.updateSideDish(it)
                    },
                )
            }
            composable(route = ScreenEnum.Accompaniment.name) {
                AccompanimentMenuScreen(
                    options = DataSource.accompanimentMenuItems,
                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(viewModel, navController)
                    },
                    onNextButtonClicked = {
                        navController.navigate(ScreenEnum.Checkout.name)
                    },
                    onSelectionChanged = {
                        viewModel.updateAccompaniment(it)
                    },
                )
            }
            composable(route = ScreenEnum.Checkout.name) {
                CheckoutScreen(
                    orderUiState = uiState,
                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(viewModel, navController)
                    },
                    onNextButtonClicked = {
                        viewModel.resetOrder()
                        navController.navigate(ScreenEnum.Start.name)
                    },
                    modifier = Modifier
                        .padding(dimensionResource(R.dimen.padding_medium))
                        .fillMaxSize()
                )
            }
        }

    }
}

private fun cancelOrderAndNavigateToStart(
    viewModel: OrderViewModel,
    navController: NavHostController
) {
    viewModel.resetOrder()
    navController.popBackStack(ScreenEnum.Start.name, inclusive = false)
}