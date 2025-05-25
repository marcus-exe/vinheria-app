package br.com.vinheriaapp.data.repository

import br.com.vinheriaapp.data.local.ProductDao
import br.com.vinheriaapp.data.model.Product
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productDao: ProductDao
) : ProductRepository {
    override suspend fun insertProduct(product: Product) {
        return productDao.insertProduct(product)
    }

    override suspend fun insertAll(products: List<Product>) {
        return productDao.insertAll(products)
    }

    override fun getAllProducts(): Flow<List<Product>> {
        return productDao.getAllProducts()
    }

    override suspend fun getProductById(id: UUID): Product? {
        return productDao.getProductById(id)
    }

    override suspend fun updateProduct(product: Product) {
        return productDao.updateProduct(product)
    }

    override suspend fun deleteProduct(product: Product) {
        return productDao.deleteProduct(product)
    }

    override suspend fun deleteProductById(id: UUID) {
        return productDao.deleteProductById(id)
    }

    override suspend fun deleteAllProducts() {
        return productDao.deleteAllProducts()
    }

}