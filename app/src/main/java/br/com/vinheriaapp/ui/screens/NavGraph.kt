package br.com.vinheriaapp.ui.screens

import android.annotation.SuppressLint
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
import java.net.URLDecoder
import java.nio.charset.StandardCharsets


@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = NavGraphRoutes.ListProducts.route
    ) {
        composable(NavGraphRoutes.ListProducts.route) {
            val viewModel: ProductViewModel = hiltViewModel()
            val state by viewModel.state.collectAsState()
            ListProductsComposable(
                productState = state,
                effect = viewModel.effect,
                onEvent = viewModel::onEvent,
            ) { navController.navigate(NavGraphRoutes.SingleProduct.route) }
        }
        composable(NavGraphRoutes.SingleProduct.route) {
            val parentRoute = NavGraphRoutes.ListProducts.route
            val viewModel: ProductViewModel = navController.sharedViewModel(parentRoute)
            val state by viewModel.state.collectAsState()
            SingleProductComposable(
                onEvent = viewModel::onEvent,
                effect = viewModel.effect,
                productState = state,
            ) { navController.navigate(NavGraphRoutes.ListProducts.route) }
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
