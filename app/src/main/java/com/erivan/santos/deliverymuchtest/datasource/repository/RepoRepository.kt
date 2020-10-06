package com.erivan.santos.deliverymuchtest.datasource.repository

import com.erivan.santos.deliverymuchtest.datasource.api.endpoint.RepositoryEndpoint
import com.erivan.santos.deliverymuchtest.datasource.api.model.Repo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call

class RepoRepository(val api: RepositoryEndpoint) {

    suspend fun findAll(page: Int): List<Repo> = withContext(Dispatchers.IO)  {
        val request = api.getAllPlubicRepositories(page)

        return@withContext executeRequest(request)
    }

    suspend fun findByName(name: String): List<Repo> = withContext(Dispatchers.IO) {
        val request = api.searchForName(name)

        return@withContext executeRequest(request)
    }

    private fun executeRequest(request: Call<List<Repo>>): List<Repo> {
        val ret = request.execute()

        if (ret.isSuccessful) {
            return ret.body()?.let {
                ArrayList(it)
            } ?: ArrayList()
        }

        when (ret.code()) {
            401 -> throw Exception("Acesso não autorizado")
            else -> throw Exception("Erro desconhecido")
        }
    }
}