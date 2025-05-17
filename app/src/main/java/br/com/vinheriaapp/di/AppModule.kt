package br.com.vinheriaapp.di

import br.com.vinheriaapp.data.RepositoryImpl
import br.com.vinheriaapp.domain.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMyRepository(): Repository = RepositoryImpl()
}
