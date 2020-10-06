package com.erivan.santos.deliverymuchtest.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.erivan.santos.deliverymuchtest.datasource.repository.RepoRepository

class RepoViewModelFactory(val repository: RepoRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RepoViewModel(
            repository
        ) as T
    }
}