package br.com.vinheriaapp.data.local
import androidx.room.*
import br.com.vinheriaapp.data.model.Product
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface ProductDao {

    // CREATE
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: Product)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(products: List<Product>)

    // READ
    @Query("SELECT * FROM Product")
    fun getAllProducts(): Flow<List<Product>>

    @Query("SELECT * FROM Product WHERE id = :id")
    suspend fun getProductById(id: UUID): Product?

    // UPDATE
    @Update
    suspend fun updateProduct(product: Product)

    // DELETE
    @Delete
    suspend fun deleteProduct(product: Product)

    @Query("DELETE FROM Product WHERE id = :id")
    suspend fun deleteProductById(id: UUID)

    @Query("DELETE FROM Product")
    suspend fun deleteAllProducts()
}
