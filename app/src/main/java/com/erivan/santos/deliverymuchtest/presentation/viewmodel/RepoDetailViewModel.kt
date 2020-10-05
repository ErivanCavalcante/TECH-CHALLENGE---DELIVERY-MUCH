package com.erivan.santos.deliverymuchtest.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.erivan.santos.deliverymuchtest.presentation.model.Repo

class RepoDetailViewModel(val repo: Repo) : ViewModel() {
    val imgAvatar = MutableLiveData<String>()
    val ownerName = MutableLiveData<String>()
    val repoName = MutableLiveData<String>()
    val repoDesc = MutableLiveData<String>()

    init {
        imgAvatar.value = repo.avatarUrl
        ownerName.value = repo.login
        repoName.value = repo.name
        repoDesc.value = repo.description
    }
}