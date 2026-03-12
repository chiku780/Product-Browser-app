package com.example.ui.screens.productDetails.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appynitty.common.events.CommonEvents
import com.example.common.connectivityStatus.ConnectivityStatus
import com.example.domain.events.productDetails.ProductDetailsScreenEvents
import com.example.domain.useCases.ProductDetailsUseCases
import com.example.ui.screens.productDetails.ProductDetailsUiEvents
import com.example.ui.screens.share.NavArgsShare
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductDetailsViewModel (
    private val productDetailsUseCases: ProductDetailsUseCases,
    connectivityStatus : ConnectivityStatus,
    private val navArgsShare : NavArgsShare
) : ViewModel() {

    init {
        navArgsShare?.id?.let { getProductDetails(it) }
    }
    private val _commonEventChannel = Channel<CommonEvents>()
    val commonEvents = _commonEventChannel.receiveAsFlow()

    private val _productDetailsChannel = Channel<ProductDetailsScreenEvents>()
    val productDetailsChannel = _productDetailsChannel.receiveAsFlow()

    private val _productDetailsList = MutableStateFlow<List<Pair<String?, String?>>?>(emptyList())
    val productDetailsList = _productDetailsList.asStateFlow()

    private val _thumbNail = MutableStateFlow<String?>(null)
    val thumbNail = _thumbNail.asStateFlow()

    private val _tittle = MutableStateFlow<String?>(null)
    val tittle = _tittle.asStateFlow()


    val isInternetOn: Flow<Boolean> = connectivityStatus.isConnected()



    fun onEvents(events: ProductDetailsUiEvents){
        when (events) {
            is ProductDetailsUiEvents.ProductDetailsApiCall -> {
                println("working")
                events?.id?.let { getProductDetails(it) }
            }
            is ProductDetailsUiEvents.SwipeRefresh -> {
                navArgsShare?.id?.let { getProductDetails(it) }
            }
        }
    }

    fun setProductList(data: List<Pair<String?, String?>>?, thumbNail: String?, tittle: String?){
        _productDetailsList.value = data
        _thumbNail.value = thumbNail
        _tittle.value = tittle
    }

    private fun getProductDetails(id: Int){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                productDetailsUseCases(id).collect {
                    it?.let { it1 ->
                        when (it1) {
                            is CommonEvents -> {
                                _commonEventChannel.send(it1)
                            }
                            is ProductDetailsScreenEvents -> {
                                _productDetailsChannel.send(it1)
                            }
                        }
                    }
                }

            }
        }
    }

}