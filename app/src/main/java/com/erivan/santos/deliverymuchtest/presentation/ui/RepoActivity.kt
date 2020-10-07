package com.erivan.santos.deliverymuchtest.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.erivan.santos.deliverymuchtest.R
import com.erivan.santos.deliverymuchtest.config.AppApplication
import com.erivan.santos.deliverymuchtest.datasource.api.endpoint.RepositoryEndpoint
import com.erivan.santos.deliverymuchtest.datasource.repository.RepoRepository
import com.erivan.santos.deliverymuchtest.presentation.model.Repo
import com.erivan.santos.deliverymuchtest.presentation.viewmodel.RepoViewModel
import com.mikepenz.fastadapter.adapters.FastItemAdapter
import com.mikepenz.fastadapter.adapters.GenericFastItemAdapter
import com.mikepenz.fastadapter.adapters.GenericItemAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.mikepenz.fastadapter.scroll.EndlessRecyclerOnScrollListener
import com.mikepenz.fastadapter.ui.items.ProgressItem
import kotlinx.android.synthetic.main.activity_repo.*
import kotlinx.coroutines.Dispatchers

class RepoActivity : AppCompatActivity() {

    protected lateinit var adapter: GenericFastItemAdapter
    protected lateinit var footerAdapter: GenericItemAdapter

    lateinit var viewModel: RepoViewModel

    var quering = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repo)

        viewModel = createViewModel()

        adapter = FastItemAdapter()
        footerAdapter = ItemAdapter.items()

        adapter.addAdapter(1, footerAdapter)

        setupRecyclerView(rvRepo)

        bindEvents()
    }

    private fun bindEvents() {

        viewModel.loading.observe(this, Observer {
            pgProgress.visibility = if (it) View.VISIBLE else View.GONE
        })

        viewModel.list.observe(this, Observer {
            adapter.set(it)
        })

        viewModel.loadMoreList.observe(this, Observer {
            footerAdapter.clear()

            it.pegaContentSeNaoFoiHandled()?.let { ev ->
                adapter.add(ev)
            }
        })

        viewModel.clearList.observe(this, Observer {
            it.pegaContentSeNaoFoiHandled()?.let { ev ->
                footerAdapter.clear()
                adapter.clear()
            }
        })

        rvRepo.addOnScrollListener(object : EndlessRecyclerOnScrollListener() {
            override fun onLoadMore(currentPage: Int) {
                footerAdapter.clear()
                val progressItem = ProgressItem()
                progressItem.isEnabled = false
                footerAdapter.add(progressItem)

                viewModel.load()
            }
        })

        adapter.onClickListener = { view, adapter, item, position ->
            Navigator.navigateToRepoDetail(this, item as Repo)
            true
        }
    }

    private fun createViewModel(): RepoViewModel {
        val api = AppApplication.getInstance().getApi().criarService(RepositoryEndpoint::class.java)

        return ViewModelProvider(this, RepoViewModel.RepoViewModelFactory(RepoRepository(api)))
            .get(RepoViewModel::class.java)
    }

    //Retorna o tipo de layout manager usado na activity
    private fun createRecyclerViewLayoutManager() : RecyclerView.LayoutManager {
        return LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    private fun createRecyclerViewDecorator() : DividerItemDecoration? {
        return null
    }

    //Seta o recyclerview
    fun setupRecyclerView(recyclerView: RecyclerView) {
        //Limita o numero do recyclerview
        recyclerView.setHasFixedSize(true)

        //Pega o layout manager
        val mng = createRecyclerViewLayoutManager()

        //Deve adicionar um decorator?
        createRecyclerViewDecorator()?.let {
            recyclerView.addItemDecoration(it)
        }

        //Layout manager
        recyclerView.layoutManager = mng

        //Seta o adapter
        recyclerView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_repo, menu)

        val searchItem = menu?.findItem(R.id.menu_search)
        val searchView = searchItem?.actionView as? SearchView

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrBlank()) {
                    if (!quering) {
                        viewModel.resetPagination()
                        quering = true
                    }

                    viewModel.load(query)

                    searchItem.collapseActionView()
                    return true
                }

                quering = false

                searchItem.collapseActionView()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                return true
            }
        })

        searchView?.setOnCloseListener {
            if (!quering) {
                viewModel.resetPagination()
                viewModel.load()
            }

            true
        }

        return super.onCreateOptionsMenu(menu)
    }
}