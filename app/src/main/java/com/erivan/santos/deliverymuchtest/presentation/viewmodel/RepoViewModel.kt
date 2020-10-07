package com.erivan.santos.deliverymuchtest.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.erivan.santos.deliverymuchtest.datasource.repository.RepoRepository
import com.erivan.santos.deliverymuchtest.presentation.model.Repo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RepoViewModel(val repository: RepoRepository) : ViewModel() {
    val list = MutableLiveData<MutableList<Repo>>()
    val loadMoreList = MutableLiveData<Event<MutableList<Repo>>>()
    val clearList = MutableLiveData<Event<Unit>>()
    val loading = MutableLiveData<Boolean>()

    private var page = 0
    private var hasMorePages = true

    init {
        load()
    }

    fun resetPagination() {
        page = 0
        hasMorePages = true
        clearList.value = Event(Unit)
    }

    fun load(name: String? = null) {
        viewModelScope.launch(Dispatchers.Main) {
            loading.value = true

            if (hasMorePages)
                page++

            val result = if (name == null) repository.findAll(page) else repository.findByName(name, page)

            result?.let {
                if (it.items.isEmpty())
                    hasMorePages = false
                else {
                    val listConverted = ArrayList(it.items.map {
//                      Repo(it.name, it.description, it.owner.avatarUrl, it.owner.login)
                        Repo(it.name, it.description ?: "", it.owner.avatarUrl, it.owner.login)
                    })

                    list.value?.let {
                        it.addAll(listConverted)
                        loadMoreList.value = Event(listConverted)
                    } ?: list.apply {
                        value =  listConverted
                    }
                }
            }

            loading.value = false
        }
    }

    @SuppressWarnings("UNCHECKED_CAST")
    class RepoViewModelFactory(val repository: RepoRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(RepoViewModel::class.java))
                RepoViewModel(
                repository
            ) as T else throw Exception("Viewmodel deconhecido")
        }
    }
}