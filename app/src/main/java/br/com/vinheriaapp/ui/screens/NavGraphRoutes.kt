package br.com.vinheriaapp.ui.screens

sealed class NavGraphRoutes(val route: String) {
    object ListProducts : NavGraphRoutes("list_products")
    object SingleProduct : NavGraphRoutes("single_product")
}