package com.erivan.santos.deliverymuchtest.datasource.api.endpoint

import com.erivan.santos.deliverymuchtest.datasource.api.model.Query as QueryModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RepositoryEndpoint {

    @GET("search/repositories?q=is:public&per_page=30")
    fun getAllPlubicRepositories(@Query("page") page: Int): Call<QueryModel>

    @GET("search/repositories?per_page=30")
    fun searchForName(@Query("q") name: String, @Query("page") page: Int): Call<QueryModel>
}