package com.erivan.santos.deliverymuchtest.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.erivan.santos.deliverymuchtest.presentation.model.Repo

class RepoDetailViewModelFactory(val repo: Repo) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RepoDetailViewModel(
            repo
        ) as T
    }
}