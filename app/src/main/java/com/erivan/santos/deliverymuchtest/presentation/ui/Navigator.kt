package com.erivan.santos.deliverymuchtest.presentation.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.erivan.santos.deliverymuchtest.presentation.model.Repo

object Navigator {
    @JvmStatic
    fun navigateToRepoDetail(activity: AppCompatActivity, repo: Repo) {
        val bundle = Bundle().apply {
            putSerializable("repo", repo)
        }

        navigateTo(activity, RepoDetailActivity::class.java, false, bundle)
    }

    @JvmStatic
    private fun navigateTo(activity: AppCompatActivity, where: Class<*>, finalizarActivity: Boolean, bundle: Bundle? = null) {
        val it = Intent(activity, where)

        //Se tem algum extra adiciona
        if (bundle != null)
            it.putExtras(bundle)

        activity.startActivity(it)

        if (finalizarActivity)
            activity.finish()
    }
}