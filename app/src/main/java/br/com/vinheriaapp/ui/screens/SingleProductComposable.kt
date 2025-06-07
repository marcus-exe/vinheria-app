package br.com.vinheriaapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import br.com.vinheriaapp.data.model.Product
import br.com.vinheriaapp.ui.screens.resources.ImageFromFileComposable
import br.com.vinheriaapp.ui.screens.resources.ImagePickerWithCamera
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal
import java.util.UUID

@Composable
fun SingleProductComposable(
    modifier: Modifier = Modifier,
    onEvent: (ProductEvent) -> Unit,
    effect: Flow<ProductEffect>,
    productState: ProductState,
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
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row (
            horizontalArrangement = Arrangement.Start
        ) {
            if (productState.mode != Mode.VIEW) {
                ImagePickerWithCamera(onEvent)
            } else {
                Row {
                    ImageFromFileComposable(
                        imagePath = productState.productImgSrc
                    )
                }
            }
        }


        OutlinedTextField(
            value = productState.productName,
            onValueChange = { onEvent(ProductEvent.SetProductName(it.trim())) },
            placeholder = { Text(text = "Name") },
            singleLine = true,
            readOnly = productState.mode == Mode.VIEW,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )

        OutlinedTextField(
            value = productState.productPrice,
            onValueChange = { onEvent(ProductEvent.SetProductPrice(it.trim())) },
            placeholder = { Text(text = "Price") },
            singleLine = true,
            readOnly = productState.mode == Mode.VIEW,
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
            readOnly = productState.mode == Mode.VIEW,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )

        OutlinedTextField(
            value = productState.productDescription,
            onValueChange = { onEvent(ProductEvent.SetProductDescription(it.trim())) },
            placeholder = { Text(text = "Description") },
            readOnly = productState.mode == Mode.VIEW,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .height(100.dp)
        )

        if (productState.mode != Mode.VIEW) {
            SaveButton(
                productState,
                onEvent,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(vertical = 8.dp)
            )
        }

    }
}

@Composable
private fun SaveButton(
    productState: ProductState,
    onEvent: (ProductEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = {
            val listOfInputsForCreatingProduct = listOf(
                productState.productName,
                productState.productPrice,
                productState.productStock,
                productState.productDescription
            )
            if (listOfInputsForCreatingProduct.all { it.isNotBlank() }) {
                onEvent(
                    ProductEvent.SaveProduct(
                        product = Product(
                            id = productState.productId ?: UUID.randomUUID(),
                            name = productState.productName,
                            price = BigDecimal(productState.productPrice),
                            stock = productState.productStock.toInt(),
                            description = productState.productDescription,
                            imgSrc = productState.productImgSrc
                        )
                    )
                )

            }
        },
        modifier = modifier
    ) {
        if (productState.mode == Mode.ADD) {
            Text("Create")
        } else {
            Text("Save")
        }
    }
}
