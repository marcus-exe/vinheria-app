package br.com.vinheriaapp.ui.screens

import br.com.graest.retinografo.base.arch.UIState
import br.com.vinheriaapp.data.model.Product
import java.util.UUID

enum class Mode{
    EDIT,
    ADD,
    VIEW
}

data class ProductState(
    val productList: List<Product> = emptyList(),
    val productId: UUID? = null,
    val productName: String = "",
    val productPrice: String = "",
    val productStock: String = "",
    val productDescription: String = "",
    val productImgSrc: String = "",
    val mode: Mode = Mode.ADD
) : UIState