package com.example.ui.screens.home.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.connectivityStatus.ConnectivityStatus
import com.example.domain.events.homeScreen.AllProductList
import com.example.domain.useCases.GetAllProductListSearchUseCases
import com.example.domain.useCases.GetAllProductListUseCases
import com.example.domain.useCases.GetAllProductUseCases
import com.example.domain.useCases.SearchProductUseCases
import com.example.ui.events.UiEvent
import com.example.ui.events.flowHandle.collectWithUiHandling
import com.example.ui.screens.home.HomeScreenEvent
import com.example.ui.screens.home.HomeScreenUiEvents
import com.example.ui.screens.home.HomeScreenUiState
import com.example.ui.screens.share.NavArgsShare
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeScreenViewmodel(
    private val getAllProductUseCases: GetAllProductUseCases,
    private val searchProductUseCases: SearchProductUseCases,
    connectivityStatus : ConnectivityStatus,
    private val navArgsShare : NavArgsShare,
    private val getAllProductListUseCases: GetAllProductListUseCases,
    private val getAllProductListSearchUseCases: GetAllProductListSearchUseCases
) : ViewModel() {

    private val _uiState: MutableStateFlow<HomeScreenUiState> = MutableStateFlow(HomeScreenUiState())
    val uiState = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<UiEvent>()
    val event = _event.asSharedFlow()

    private val _homeScreenEvent = MutableSharedFlow<HomeScreenEvent>()
    val homeScreenEvent = _homeScreenEvent.asSharedFlow()


    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    val isInternetOn: Flow<Boolean> = connectivityStatus.isConnected()

    init {
        getAllProducts()
        observerQuery()
        getAllProduct()
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


    private fun getAllProduct(){
        viewModelScope.launch {
            getAllProductListUseCases().collect { list ->
                _uiState.update {
                    it.copy(
                        productList = list
                    )
                }
            }
        }
    }

    fun onEvents(events: HomeScreenUiEvents){
        when (events) {
            is HomeScreenUiEvents.OnItemClicked -> {
                viewModelScope.launch {
                   navArgsShare.id = events.id
                    _homeScreenEvent.emit(HomeScreenEvent.OnProductClicked(events.id))
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
       searchProductLocal(search)
       searchProductRefresh(search)
    }

    private fun searchProductLocal(search: String){
        viewModelScope.launch {
            getAllProductListSearchUseCases(search).collect { list ->
                println("show the lis $list")
                _uiState.update {
                    it.copy(
                        productList = list
                    )
                }
            }
        }
    }

    private fun searchProductRefresh(search: String){
        viewModelScope.launch {
            searchProductUseCases(search).collectWithUiHandling(
                scope = this,
                onLoading = { loading -> _uiState.update { it.copy(showLoading = loading) } },
                uiError = { message -> _event.emit(UiEvent.ShowUiError(message)) },
                onSuccess = { data ->
                    val result = data as? AllProductList ?: return@collectWithUiHandling
//                    _uiState.update {
//                        it.copy(
//                            productList = result.productList
//                        )
//                    }
                },
            )
        }
    }

    private fun getAllProducts(){
        viewModelScope.launch {
            getAllProductUseCases().collectWithUiHandling(
                scope = this,
                onLoading = { loading -> _uiState.update { it.copy(showLoading = loading) } },
                uiError = { message -> _event.emit(UiEvent.ShowUiError(message)) }
            )
        }
    }

}