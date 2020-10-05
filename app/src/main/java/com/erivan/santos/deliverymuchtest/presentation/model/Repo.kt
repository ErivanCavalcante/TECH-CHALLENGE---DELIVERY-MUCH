package com.erivan.santos.deliverymuchtest.presentation.model

import android.view.View
import android.widget.TextView
import com.erivan.santos.deliverymuchtest.R
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import java.io.Serializable

class Repo(val name: String,
           val description: String,
           val avatarUrl: String,
           val login: String) : AbstractItem<Repo.RepoViewHolder>(), Serializable {

    override val layoutRes: Int
        get() = R.layout.list_item_repo
    override val type: Int
        get() = 0

    override fun getViewHolder(v: View): RepoViewHolder {
        return RepoViewHolder(v)
    }

    class RepoViewHolder(itemView: View) : FastAdapter.ViewHolder<Repo>(itemView) {
        val txtRepoTitle = itemView.findViewById<TextView>(R.id.txtRepo)
        val txtOwner = itemView.findViewById<TextView>(R.id.txtOwner)

        override fun bindView(item: Repo, payloads: MutableList<Any>) {
            txtRepoTitle.text = item.name
            txtOwner.text = item.login
        }

        override fun unbindView(item: Repo) {
            txtRepoTitle.text = ""
            txtOwner.text = ""
        }
    }
}