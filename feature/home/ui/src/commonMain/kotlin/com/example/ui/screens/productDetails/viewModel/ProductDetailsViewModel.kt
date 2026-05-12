package com.example.ui.screens.productDetails.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.connectivityStatus.ConnectivityStatus
import com.example.domain.events.productDetails.GetProductDetails
import com.example.domain.useCases.ProductDetailsUseCases
import com.example.ui.events.UiEvent
import com.example.ui.events.flowHandle.collectWithUiHandling
import com.example.ui.screens.productDetails.ProductDetailsUiEvents
import com.example.ui.screens.productDetails.ProductScreenEvent
import com.example.ui.screens.productDetails.ProductScreenUiState
import com.example.ui.screens.share.NavArgsShare
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductDetailsViewModel (
    private val productDetailsUseCases: ProductDetailsUseCases,
    connectivityStatus : ConnectivityStatus,
    private val navArgsShare : NavArgsShare
) : ViewModel() {

    init {
//        navArgsShare?.id?.let { getProductDetails(it) }
    }

    private val _uiState: MutableStateFlow<ProductScreenUiState> = MutableStateFlow(ProductScreenUiState())
    val uiState = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<UiEvent>()
    val event = _event.asSharedFlow()

    private val _productScreenEvent = MutableSharedFlow<ProductScreenEvent>()
    val productScreenEvent = _productScreenEvent.asSharedFlow()

    val isInternetOn: Flow<Boolean> = connectivityStatus.isConnected()



    fun onEvents(events: ProductDetailsUiEvents){
        when (events) {

            is ProductDetailsUiEvents.SwipeRefresh -> {
                navArgsShare?.id?.let { getProductDetails(it) }
            }

            ProductDetailsUiEvents.Init -> navArgsShare?.id?.let { getProductDetails(it) }
        }
    }

    private fun getProductDetails(id: Int){
        viewModelScope.launch {
            productDetailsUseCases(id).collectWithUiHandling(
                scope = this,
                onLoading = { loading -> _uiState.update { it.copy(showLoading = loading) } },
                uiError = { message -> _event.emit(UiEvent.ShowUiError(message)) },
                onSuccess = { data ->
                    val result = data as? GetProductDetails ?: return@collectWithUiHandling
                    _uiState.update {
                        it.copy(
                            productDetails = result.result, thumbnail = result.thumbNail ?: "", title = result.tittle ?: ""
                        )
                    }
                },
            )
        }
    }

}