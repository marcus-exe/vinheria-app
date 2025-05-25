package br.com.vinheriaapp.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.vinheriaapp.data.model.Product
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal
import java.util.UUID

@Composable
fun ListProductsComposable(
    productState: ProductState,
    effect: Flow<ProductEffect>,
    onEvent: (ProductEvent) -> Unit,
    navigateSingleProduct: () -> Unit,
) {
    LaunchedEffect(effect) {
        effect.collect {
            when (it) {
                ProductEffect.GoToAddProduct -> { navigateSingleProduct() }
                else -> Unit
            }
        }
    }
    LaunchedEffect(Unit) {
        delay(2000)
        Log.d("UI_PRODUCTS", "Initial size: ${productState.productList.size}")
    }

    LaunchedEffect(productState.productList) {
        Log.d("RECOMPOSE_CHECK", "Product list changed: ${productState.productList.size}")
    }

    if (productState.productList.isEmpty()) {
        val testList = listOf(
            Product(UUID.randomUUID(), "Vinho 1", BigDecimal(12.111), 12, "No description"),
            Product(UUID.randomUUID(), "Vinho 1", BigDecimal(12.111), 12, "No description"),
            Product(UUID.randomUUID(), "Vinho 1", BigDecimal(12.111), 12, "No description"),
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(testList) { product ->
                ProductCardComposable(product)
            }
        }

//        Column(
//            modifier = Modifier.fillMaxSize(),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Text(text = "There are no products registered yet :(")
//
//            Spacer(modifier = Modifier.padding(30.dp))
//
//            Button(onClick = { onEvent(ProductEvent.GoToEditImageScreen) }) {
//                Text(text = "Add Product")
//            }
//        }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(productState.productList) { product ->
                ProductCardComposable(product)
            }
        }
    }


}