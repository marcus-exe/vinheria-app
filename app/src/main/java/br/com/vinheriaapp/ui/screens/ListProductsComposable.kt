package br.com.vinheriaapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.Flow

@Composable
fun ListProductsComposable(
    modifier: Modifier = Modifier,
    productState: ProductState,
    effect: Flow<ProductEffect>,
    onEvent: (ProductEvent) -> Unit,
    navigateSingleProduct: () -> Unit,
) {
    LaunchedEffect(effect) {
        effect.collect {
            when (it) {
                ProductEffect.GoToSingleProduct -> { navigateSingleProduct() }
                else -> Unit
            }
        }
    }

    if (productState.productList.isEmpty()) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "There are no products registered yet :(")
        }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(productState.productList) { product ->
                ProductCardComposable(
                    product = product,
                    modifier = Modifier.clickable {
                        onEvent(ProductEvent.GoToSingleImageScreen(product, Mode.VIEW))
                    }
                )
            }
        }
    }
}

