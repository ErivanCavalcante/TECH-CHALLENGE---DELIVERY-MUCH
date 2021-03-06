package com.erivan.santos.deliverymuchtest.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.erivan.santos.deliverymuchtest.R
import com.erivan.santos.deliverymuchtest.databinding.ActivityRepoDetailBinding
import com.erivan.santos.deliverymuchtest.presentation.model.Repo
import com.erivan.santos.deliverymuchtest.presentation.viewmodel.RepoDetailViewModel

class RepoDetailActivity : AppCompatActivity() {
    lateinit var viewModel: RepoDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityRepoDetailBinding>(this, R.layout.activity_repo_detail)

        viewModel = createViewModel()

        binding.viewModel = viewModel
    }

    private fun createViewModel(): RepoDetailViewModel {
        val repo = intent.getSerializableExtra("repo") as? Repo
            ?: throw Exception("")

        return ViewModelProvider(this, RepoDetailViewModel.RepoDetailViewModelFactory(repo))
            .get(RepoDetailViewModel::class.java)
    }
}