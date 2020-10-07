package com.erivan.santos.deliverymuchtest.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.erivan.santos.deliverymuchtest.datasource.api.model.Owner
import com.erivan.santos.deliverymuchtest.datasource.api.model.Query
import com.erivan.santos.deliverymuchtest.datasource.api.model.Repo
import com.erivan.santos.deliverymuchtest.datasource.repository.RepoRepositoryContract
import com.erivan.santos.deliverymuchtest.presentation.model.Repo as RepoPresentation
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.*
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TestRepoViewModel {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val ruleCoroutines = CoroutinesTestRule()

    lateinit var viewModel: RepoViewModel

    @Mock
    lateinit var repoRepository: RepoRepositoryContract

    @Mock
    lateinit var listObservable: Observer<MutableList<RepoPresentation>>

    @Mock
    lateinit var loadMoreObservable: Observer<Event<MutableList<RepoPresentation>>>

    fun createViewModel() {
        viewModel = RepoViewModel(repoRepository)

        viewModel.list.observeForever(listObservable)
        viewModel.loadMoreList.observeForever(loadMoreObservable)
    }

    @Before
    fun setup() {
        createViewModel()
    }

    @Test
    fun hasObserver() {
        Assert.assertTrue(viewModel.list.hasObservers())
        Assert.assertTrue(viewModel.loadMoreList.hasObservers())
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testLoadWithoutFilter() = runBlockingTest {
        viewModel.resetPagination()

        val loadList = ArrayList<Repo>().apply {
            add(Repo(1, "Nome 1", "Nome completo 1", "Descrição 1",
                Owner("http://avatarurl", "Dono nome 1")))
        }

        val loadMoreList = ArrayList<Repo>().apply {
            add(Repo(2, "Nome 2", "Nome completo 2", "Descrição 2",
                Owner("http://avatarurl2", "Dono nome 12")))
        }

        whenever(repoRepository.findAll(1))
            .thenReturn(Query(1, loadList))

        whenever(repoRepository.findAll(2))
            .thenReturn(Query(1, loadMoreList))


        viewModel.load()

        Assert.assertNotNull(viewModel.list.value)
        assert(viewModel.list.value!!.count() == 1)

        //Load more
        viewModel.load()

        Assert.assertNotNull(viewModel.loadMoreList.value)
        assert(viewModel.loadMoreList.value!!.pegaContentSeNaoFoiHandled()!!.count() == 1)
        assert(viewModel.list.value!!.count() == 2)
    }

//    @ExperimentalCoroutinesApi
//    @Test
//    fun testLoadWithFilter() = runBlockingTest {
//        viewModel.resetPagination()
//
//        val loadList = ArrayList<Repo>().apply {
//            add(Repo(1, "Search Nome 1", "Search Nome completo 1", "Search Descrição 1",
//                Owner("http://avatarurl", "Search Dono nome 1")))
//        }
//
//        whenever(repoRepository.findByName("Search teste", 1))
//            .thenReturn(Query(1, loadList))
//
//        viewModel.load("Search")
//
//        Assert.assertNotNull(viewModel.list.value)
//        assert(viewModel.list.value!!.count() == 1)
//    }
}