package com.example.ui.screens.home.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appynitty.common.events.CommonEvents
import com.example.common.connectivityStatus.ConnectivityStatus
import com.example.domain.events.homeScreen.HomeScreenEvents
import com.example.domain.events.homeScreen.HomeScreenEvents.*
import com.example.domain.model.allProducts.ProductsItem
import com.example.domain.useCases.GetAllProductUseCases
import com.example.domain.useCases.SearchProductUseCases
import com.example.ui.screens.home.HomeScreenUiEvents
import com.example.ui.screens.share.NavArgsShare
import io.ktor.events.Events
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeScreenViewmodel(
    private val getAllProductUseCases: GetAllProductUseCases,
    private val searchProductUseCases: SearchProductUseCases,
    connectivityStatus : ConnectivityStatus,
    private val navArgsShare : NavArgsShare
) : ViewModel() {

    private val _commonEventChannel = Channel<CommonEvents>()
    val commonEvents = _commonEventChannel.receiveAsFlow()

    private val _homeChannel = Channel<HomeScreenEvents>()
    val homeChannel = _homeChannel.receiveAsFlow()

    private val _productList = MutableStateFlow<List<ProductsItem?>?>(emptyList())
    val productList = _productList.asStateFlow()

    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    val isInternetOn: Flow<Boolean> = connectivityStatus.isConnected()

    init {
        getAllProducts()
        observerQuery()
    }

    private fun observerQuery(){
        viewModelScope.launch {
            _query
                .debounce(500)
                .distinctUntilChanged()
                .flatMapLatest { query ->

                    when {
                        query.isEmpty() -> flow { emit(getAllProducts()) }
                        query.length >= 2 -> flow { emit(searchProducts(query)) }
                        else -> emptyFlow()
                    }

                }
                .collect()
        }
    }

    fun setProductList(data:List<ProductsItem?>?){
        _productList.value = data
    }

    fun onEvents(events: HomeScreenUiEvents){
        when (events) {
            is HomeScreenUiEvents.OnItemClicked -> {
                viewModelScope.launch {
                   navArgsShare.id = events.id
                    _homeChannel.send(OnProductClicked(events.id))
                }
            }
            is HomeScreenUiEvents.OnSearchQuery -> {
                updateQuery(events.query)
            }

            HomeScreenUiEvents.SwipeRefresh -> {
                if (_query.value.length>0){
                    searchProducts(_query.value)
                }else{
                    getAllProducts()
                }
            }
        }
    }


   private fun updateQuery(newQuery: String) {
        _query.value = newQuery
    }

   private fun searchProducts(search: String){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {


                searchProductUseCases(search).collect {
                    it?.let { it1 ->
                        when (it1) {
                            is CommonEvents -> {
                                _commonEventChannel.send(it1)
                            }

                            is HomeScreenEvents -> {
                                _homeChannel.send(it1)
                            }
                        }
                    }
                }

            }
        }
    }

    private fun getAllProducts(){
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    getAllProductUseCases().collect {
                        it?.let { it1 ->
                            when (it1) {
                                is CommonEvents -> {
                                    _commonEventChannel.send(it1)
                                }

                                is HomeScreenEvents -> {
                                    _homeChannel.send(it1)
                                }
                            }
                        }
                    }
                }
            }
    }

}