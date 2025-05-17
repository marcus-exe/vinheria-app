package br.com.vinheriaapp.data

import br.com.vinheriaapp.domain.Repository
import javax.inject.Inject

class RepositoryImpl @Inject constructor() : Repository {
    override fun getGreeting(): String = "Hello from MyRepositoryImpl!"
}
