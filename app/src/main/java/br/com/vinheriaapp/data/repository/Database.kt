package br.com.vinheriaapp.data.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.vinheriaapp.data.local.ProductDao
import br.com.vinheriaapp.data.model.Product
import br.com.vinheriaapp.utils.Converters

@Database(
    entities =[Product::class],
    exportSchema =true,
    version = 3
)
@TypeConverters(Converters::class)
abstract class Database : RoomDatabase() {
    abstract val productDao: ProductDao
}