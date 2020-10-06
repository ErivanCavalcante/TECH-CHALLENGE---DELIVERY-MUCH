package com.erivan.santos.deliverymuchtest.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erivan.santos.deliverymuchtest.datasource.repository.RepoRepository
import com.erivan.santos.deliverymuchtest.presentation.model.Repo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RepoViewModel(val repository: RepoRepository) : ViewModel() {
    val list = MutableLiveData<MutableList<Repo>>()
    val loading = MutableLiveData<Boolean>()

    private var page = 0
    private var hasMorePages = true

    init {
        next()
    }

    fun next() {
        viewModelScope.launch(Dispatchers.Main) {
            loading.value = true

            if (hasMorePages)
                page++

            val result = repository.findAll(page).map {
                Repo(it.name, it.description, it.owner.avatarUrl, it.owner.login)
            }

            if (result.isEmpty())
                hasMorePages = false
            else {
                list.value?.addAll(result) ?: list.apply {
                    value =  ArrayList(result)
                }
            }

            loading.value = false
        }
    }

    fun resetPagination() {
        page = 0
        hasMorePages = true
    }

    fun search(name: String) {
        viewModelScope.launch(Dispatchers.Main) {
            loading.value = true

            list.value = ArrayList(repository.findByName(name).map {
                Repo(it.name, it.description, it.owner.avatarUrl, it.owner.login)
            })

            loading.value = false
        }
    }
}