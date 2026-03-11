package com.example.ui.screens.home.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appynitty.common.events.CommonEvents
import com.example.domain.events.homeScreen.HomeScreenEvents
import com.example.domain.model.allProducts.ProductsItem
import com.example.domain.useCases.GetAllProductUseCases
import com.example.ui.screens.home.HomeScreenUiEvents
import io.ktor.events.Events
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeScreenViewmodel(
    private val getAllProductUseCases: GetAllProductUseCases
) : ViewModel() {

    private val _commonEventChannel = Channel<CommonEvents>()
    val commonEvents = _commonEventChannel.receiveAsFlow()

    private val _homeChannel = Channel<HomeScreenEvents>()
    val homeChannel = _homeChannel.receiveAsFlow()

    private val _productList = MutableStateFlow<List<ProductsItem?>?>(emptyList())
    val productList = _productList.asStateFlow()

    init {
        getAllProducts()
        _productList.value =   listOf(ProductsItem(thumbnail = "https://cdn.dummyjson.com/product-images/groceries/dog-food/thumbnail.webp", title = "hii", price = 00.5,id=1),
            ProductsItem(thumbnail = "https://cdn.dummyjson.com/product-images/groceries/dog-food/thumbnail.webp", title = "hii", price = 00.5,id=2),
                    ProductsItem(thumbnail = "https://cdn.dummyjson.com/product-images/groceries/dog-food/thumbnail.webp", title = "hii", price = 00.5,id=3),
            ProductsItem(thumbnail = "https://cdn.dummyjson.com/product-images/groceries/dog-food/thumbnail.webp", title = "hii", price = 00.5,id=4))
    }

    fun setProductList(data:List<ProductsItem?>?){
        _productList.value = data
    }

    fun onEvents(events: HomeScreenUiEvents){
        when (events) {
            is HomeScreenUiEvents.OnItemClicked -> {
                viewModelScope.launch {
                    _homeChannel.send(HomeScreenEvents.OnProductClicked(events.id))
                }
            }
        }
    }

    fun getAllProducts(){
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