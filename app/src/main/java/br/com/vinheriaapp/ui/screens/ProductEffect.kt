package br.com.vinheriaapp.ui.screens

import br.com.graest.retinografo.base.arch.UIEffect

sealed interface ProductEffect : UIEffect{
    data object GoToAddProduct: ProductEffect
    data object GoToListProduct: ProductEffect
}