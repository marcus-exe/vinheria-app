package br.com.vinheriaapp.ui.screens

import androidx.lifecycle.viewModelScope
import br.com.graest.retinografo.base.arch.CoreViewModel
import br.com.vinheriaapp.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : CoreViewModel<ProductState, ProductEffect>(ProductState()) {

    init {
        viewModelScope.launch {
            productRepository.getAllProducts().collect { products ->
                setState {
                    it.copy(productList = products)
                }
            }
        }
    }

    fun onEvent(event: ProductEvent) {
        when(event){
            is ProductEvent.SetProductDescription -> setState { it.copy(productDescription = event.productDescription) }
            is ProductEvent.SetProductName -> setState { it.copy(productName = event.productName) }
            is ProductEvent.SetProductImageSrc -> setState { it.copy(productImgSrc = event.productImageSrc ) }
            is ProductEvent.SetProductPrice -> setState { it.copy(productPrice = event.productPrice) }
            is ProductEvent.SetProductStock -> setState { it.copy(productStock = event.productStock) }
            is ProductEvent.GoToSingleImageScreen -> {
                when (event.mode) {
                    Mode.EDIT -> {
                        if (event.product != null) {
                            setState { it.copy(
                                productName = event.product.name,
                                productPrice = event.product.price.toString(),
                                productStock = event.product.stock.toString(),
                                productDescription = event.product.description,
                                productImgSrc = event.product.imgSrc ?: "",
                                mode = Mode.EDIT
                            ) }
                        }
                    }
                    Mode.ADD -> {
                            setState { it.copy(mode = Mode.ADD) }
                    }
                    Mode.VIEW -> {
                        if (event.product != null) {
                            setState { it.copy(
                                productName = event.product.name,
                                productPrice = event.product.price.toString(),
                                productStock = event.product.stock.toString(),
                                productDescription = event.product.description,
                                productImgSrc = event.product.imgSrc ?: "",
                                mode = Mode.VIEW
                            ) }
                        }
                    }
                }
                sendEffect(ProductEffect.GoToSingleProduct)
            }
            is ProductEvent.SaveProduct -> {
                viewModelScope.launch {
                    productRepository.insertProduct(event.product)
                    sendEffect(ProductEffect.GoToListProduct)
                }
            }
            is ProductEvent.SetMode -> {
                setState { it.copy(mode = event.mode) }
            }
        }
    }

}