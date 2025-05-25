package br.com.vinheriaapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import br.com.vinheriaapp.data.model.Product
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal
import java.util.UUID

@Composable
fun SingleProductComposable(
    onEvent: (ProductEvent) -> Unit,
    effect: Flow<ProductEffect>,
    productState: ProductState,
    modifier: Modifier = Modifier,
    navigateListProduct: () -> Unit,
){
    LaunchedEffect(effect) {
        effect.collect {
            when (it) {
                ProductEffect.GoToListProduct -> { navigateListProduct() }
                else -> Unit
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
        ,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (productState.mode == Mode.ADD) {
            Text(
                text = "Add Product",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.align(Alignment.Start)
            )
        } else  {
            Text(
                text = "Edit Product",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.align(Alignment.Start)
            )
        }

//        Row {
//
//            ImageFromFileComposable(imagePath = productState.productImgSrc, modifier.weight(3f))
//            Button(
//                onClick = { TODO() },
//                modifier = Modifier.weight(2f)
//            ) {
//                Text(text = "Add/Change Image")
//            }
//        }

        OutlinedTextField(
            value = productState.productName,
            onValueChange = { onEvent(ProductEvent.SetProductName(it.trim())) },
            placeholder = { Text(text = "Name") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )

        OutlinedTextField(
            value = productState.productPrice,
            onValueChange = { onEvent(ProductEvent.SetProductPrice(it.trim())) },
            placeholder = { Text(text = "Price") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )
        OutlinedTextField(
            value = productState.productStock,
            onValueChange = { onEvent(ProductEvent.SetProductStock(it.trim())) },
            placeholder = { Text(text = "Stock") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )

        OutlinedTextField(
            value = productState.productDescription,
            onValueChange = { onEvent(ProductEvent.SetProductDescription(it.trim())) },
            placeholder = { Text(text = "Description") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .height(100.dp)
        )

        Button(
            onClick = {
                val listOfInputsForCreatingProduct = listOf(
                    productState.productName,
                    productState.productPrice,
                    productState.productStock,
                    productState.productDescription
                )
                if ( listOfInputsForCreatingProduct.all { it.isNotBlank() } ) {
                    onEvent(ProductEvent.SaveProduct(
                        product = Product(
                            id = UUID.randomUUID(),
                            name = productState.productName,
                            price = BigDecimal(productState.productPrice),
                            stock = productState.productStock.toInt(),
                            description = productState.productDescription,
                            imgSrc = productState.productImgSrc
                        )
                    ))
                }
            },
            modifier = Modifier
                .align(Alignment.End)
                .padding(vertical = 8.dp)
        ) {
            if (productState.mode == Mode.ADD) {
                Text("Create")
            } else {
                Text("Save")
            }
        }

    }
}

