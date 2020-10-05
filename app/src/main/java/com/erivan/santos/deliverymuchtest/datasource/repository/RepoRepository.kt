package com.erivan.santos.deliverymuchtest.datasource.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.erivan.santos.deliverymuchtest.datasource.api.endpoint.RepositoryEndpoint
import com.erivan.santos.deliverymuchtest.datasource.api.model.Repo
import retrofit2.Call

class RepoRepository(val api: RepositoryEndpoint) {

    fun findAll(page: Int): LiveData<List<Repo>> {
        val request = api.getAllPlubicRepositories(page)

        return executeRequest(request)
    }

    fun findByName(name: String): LiveData<List<Repo>> {
        val request = api.searchForName(name)

        return executeRequest(request)
    }

    private fun executeRequest(request: Call<List<Repo>>): LiveData<List<Repo>> {
        val ret = request.execute()

        if (ret.isSuccessful) {
            return ret.body()?.let {
                MutableLiveData<List<Repo>>().apply {
                    value = it
                }
            } ?: MutableLiveData<List<Repo>>().apply {
                value = ArrayList<Repo>()
            }
        }

        when (ret.code()) {
            401 -> throw Exception("Acesso não autorizado")
            else -> throw Exception("Erro desconhecido")
        }
    }
}