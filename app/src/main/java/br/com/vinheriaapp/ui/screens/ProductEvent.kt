package br.com.vinheriaapp.ui.screens

import br.com.vinheriaapp.data.model.Product

sealed interface ProductEvent {
    data class SetProductName(
        val productName: String
    ) : ProductEvent
    data class SetProductPrice(
        val productPrice: String
    ) : ProductEvent
    data class SetProductStock(
        val productStock: String
    ) : ProductEvent
    data class SetProductDescription(
        val productDescription: String
    ) : ProductEvent
    data class SetProductImageSrc(
        val productImageSrc: String
    ) : ProductEvent
    data object GoToEditImageScreen: ProductEvent
    data class SaveProduct(
        val product: Product
    ) : ProductEvent

}