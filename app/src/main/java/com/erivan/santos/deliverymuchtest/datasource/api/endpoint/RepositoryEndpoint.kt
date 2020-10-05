package com.erivan.santos.deliverymuchtest.datasource.api.endpoint

import com.erivan.santos.deliverymuchtest.datasource.api.model.Repo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RepositoryEndpoint {

    @GET("repositories?per_page=30")
    fun getAllPlubicRepositories(@Query("page") page: Int): Call<List<Repo>>

    @GET("repositories")
    fun searchForName(@Query("name") name: String): Call<List<Repo>>
}