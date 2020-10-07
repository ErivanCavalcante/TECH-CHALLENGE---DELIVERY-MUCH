package com.erivan.santos.deliverymuchtest.datasource.repository

import com.erivan.santos.deliverymuchtest.datasource.api.endpoint.RepositoryEndpoint
import com.erivan.santos.deliverymuchtest.datasource.api.model.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call

class RepoRepository(val api: RepositoryEndpoint) {

    suspend fun findAll(page: Int): Query? = withContext(Dispatchers.IO)  {
        val request = api.getAllPlubicRepositories(page)

        executeRequest(request)
    }

    suspend fun findByName(name: String, page: Int): Query? = withContext(Dispatchers.IO) {
        val request = api.searchForName("${name} in:name", page)

        executeRequest(request)
    }

    private fun executeRequest(request: Call<Query>): Query? {
        val ret = request.execute()

        if (ret.isSuccessful) {
            return ret.body()
        }

        when (ret.code()) {
            401 -> throw Exception("Acesso não autorizado")
            else -> throw Exception("Erro desconhecido")
        }
    }
}