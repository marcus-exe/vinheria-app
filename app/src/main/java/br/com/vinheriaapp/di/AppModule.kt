package br.com.vinheriaapp.di

import android.content.Context
import androidx.room.Room
import br.com.vinheriaapp.data.local.ProductDao
import br.com.vinheriaapp.data.repository.Database
import br.com.vinheriaapp.data.repository.ProductRepository
import br.com.vinheriaapp.data.repository.ProductRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    //region Database
    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        Database::class.java,
        "appDb",
    )
        .createFromAsset("appDb.db")
        .build()

    @Singleton
    @Provides
    fun provideProductDao(db: Database) = db.productDao
    //endregion

    //region Repository
    @Provides
    @Singleton
    fun provideMyRepository(
        productDao: ProductDao
    ): ProductRepository {
        return ProductRepositoryImpl(productDao)
    }
    // endregion
}
