package com.erivan.santos.deliverymuchtest.datasource.repository

import com.erivan.santos.deliverymuchtest.datasource.api.model.Query

interface RepoRepositoryContract {
    suspend fun findAll(page: Int): Query?

    suspend fun findByName(name: String, page: Int): Query?
}