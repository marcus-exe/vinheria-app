package br.com.vinheriaapp.ui.screens

import br.com.graest.retinografo.base.arch.UIState
import br.com.vinheriaapp.data.model.Product

enum class Mode{
    EDIT,
    ADD
}

data class ProductState(
    val productList: List<Product> = emptyList(),
    val productName: String = "",
    val productPrice: String = "",
    val productStock: String = "",
    val productDescription: String = "",
    val productImgSrc: String = "",
    val mode: Mode = Mode.ADD
) : UIState