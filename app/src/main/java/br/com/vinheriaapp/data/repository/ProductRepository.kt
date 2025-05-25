package br.com.vinheriaapp.data.repository

import br.com.vinheriaapp.data.model.Product
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface ProductRepository {
    suspend fun insertProduct(product: Product)
    suspend fun insertAll(products: List<Product>)
    fun getAllProducts(): Flow<List<Product>>
    suspend fun getProductById(id: UUID): Product?
    suspend fun updateProduct(product: Product)
    suspend fun deleteProduct(product: Product)
    suspend fun deleteProductById(id: UUID)
    suspend fun deleteAllProducts()
}