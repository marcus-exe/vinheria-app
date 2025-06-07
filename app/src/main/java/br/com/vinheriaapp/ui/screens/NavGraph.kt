package br.com.vinheriaapp.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import br.com.vinheriaapp.ui.screens.resources.AppBarComposable
import br.com.vinheriaapp.ui.screens.resources.ProductOverflowMenu
import java.net.URLDecoder
import java.nio.charset.StandardCharsets


@Composable
fun NavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = NavGraphRoutes.ListProducts.route
    ) {
        composable(NavGraphRoutes.ListProducts.route) {
            val viewModel: ProductViewModel = hiltViewModel()
            val state by viewModel.state.collectAsState()

            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    AppBarComposable("List Wines")
                },
                floatingActionButton = {
                    FloatingActionButton(onClick = {
                        navController.navigate(NavGraphRoutes.SingleProduct.route)
                        viewModel.onEvent(ProductEvent.SetMode(Mode.ADD))
                    }) {
                        Icon(Icons.Default.Add, contentDescription = "Add")
                    }
                }
            ) { innerPadding ->
                ListProductsComposable(
                    modifier = Modifier.padding(innerPadding),
                    productState = state,
                    effect = viewModel.effect,
                    onEvent = viewModel::onEvent,
                ) { navController.navigate(NavGraphRoutes.SingleProduct.route) }
            }
        }
        composable(NavGraphRoutes.SingleProduct.route) {
            val parentRoute = NavGraphRoutes.ListProducts.route
            val viewModel: ProductViewModel = navController.sharedViewModel(parentRoute)
            val state by viewModel.state.collectAsState()

            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    AppBarComposable(
                        "Wines",
                        true,
                        onBackClick = { navController.popBackStack() },
                        actions = {
                            if (state.mode == Mode.VIEW) {
                                ProductOverflowMenu(
                                    state = state,
                                    onEditClickEvent = viewModel::onEvent,
                                    onDeleteClickEvent = viewModel::onEvent                              )
                            }
                        }
                    )
                }
            ) { innerPadding ->
                SingleProductComposable(
                    modifier = Modifier.padding(innerPadding),
                    onEvent = viewModel::onEvent,
                    effect = viewModel.effect,
                    productState = state,
                ) { navController.navigate(NavGraphRoutes.ListProducts.route) }
            }
        }
    }
}

@Composable
fun getCurrentRoute(navController: NavController, argument: String? = null): Route {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val decode =  runCatching {
        argument?.let {
            val json = navBackStackEntry?.arguments?.getString(argument)
            URLDecoder.decode(json, StandardCharsets.UTF_8.toString())
        }
    }.getOrNull()
    return Route(navBackStackEntry?.destination?.route, decode)
}

class Route(val path: String?, val argument: String?)

@SuppressLint("UnrememberedGetBackStackEntry")
@Composable
inline fun <reified T : ViewModel> NavController.sharedViewModel(parentRoute: String): T {
    val parentEntry =
        remember(this) {
            getBackStackEntry(parentRoute)
        }
    return hiltViewModel(parentEntry)
}
