package com.example.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.appynitty.common.events.CommonEvents
import com.appynitty.ui.colors.CustomColorsConstant
import com.appynitty.ui.library.toast.CustomToast
import com.appynitty.ui.library.toast.ToastController
import com.appynitty.ui.library.toast.ToastType
import com.example.common.navigation.NavigationHomeRoute
import com.example.domain.events.homeScreen.HomeScreenEvents
import com.example.domain.repository.ProductRepository
import com.example.ui.navigation.navigateSingleTop
import com.example.ui.screens.home.component.NoDataFound
import com.example.ui.screens.home.component.NoInternetConnection
import com.example.ui.screens.home.component.ProductCard
import com.example.ui.screens.home.component.ProgressOverlay
import com.example.ui.screens.home.component.SearchTopBar
import com.example.ui.screens.home.viewModel.HomeScreenViewmodel
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(navHostController: NavHostController) {

    val viewModel = koinViewModel<HomeScreenViewmodel>()
    val state = rememberPullToRefreshState()
    val isInternetOn by viewModel.isInternetOn.collectAsState(initial = false)
    var isLoading by remember { mutableStateOf(false) }
    var isRefresh by remember { mutableStateOf(false) }

    val productList by viewModel.productList.collectAsStateWithLifecycle()
    val searchQuery by viewModel.query.collectAsStateWithLifecycle()

println("product lsit $productList")

    LaunchedEffect(Unit) {

        launch {
            viewModel.commonEvents.collect { event ->
                when (event) {
                    is CommonEvents.IsLoading -> {
                        isLoading = event.isLoading
                    }

                    is CommonEvents.ShowMessage -> {
                        println("message is ${event.msg}")
                        event?.msg?.let { ToastController.show(it, ToastType.WARNING) }
                    }

                    is CommonEvents.ErrorMessage -> {
                        println("message is ${event.errorMessage}")
                        event?.errorMessage?.let { ToastController.show(it, ToastType.ERROR) }
                    }

                    is CommonEvents.ShowSuccessMessage -> {
                        event?.msg?.let { ToastController.show(it, ToastType.SUCCESS) }
                    }

                }
            }
        }

        launch {
            viewModel.homeChannel.collect { event ->
                when (event) {

                    is HomeScreenEvents.GetAllProductList -> {
                     viewModel.setProductList(event.result)
                    }

                    is HomeScreenEvents.OnProductClicked -> {
                        navHostController.navigateSingleTop("${NavigationHomeRoute.ProductDetails.route}/${event.id}")
                    }
                }
            }
        }

    }

    Scaffold(
        topBar = {
            SearchTopBar(
                query = searchQuery,
                viewModel
            )
        }
    ) { scaffoldPadding ->
    Box(modifier = Modifier.fillMaxSize().padding(scaffoldPadding).background(CustomColorsConstant.White)) {



        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            Spacer(modifier = Modifier.size(12.dp))

            PullToRefreshBox(
                state = state, // Pass the state
                isRefreshing = isRefresh, // Observe the loading state
                onRefresh = { isRefresh = false
                    viewModel.onEvents(HomeScreenUiEvents.SwipeRefresh)
                } // Trigger data refresh
            ) {
                if (isInternetOn) {
                    if (productList?.isEmpty() == true) {
                        if (!isLoading) NoDataFound()
                    } else {
                        LazyColumn {

                            items(
                                items = productList.orEmpty(),
                                key = { product -> product?.id ?: 0 }
                            ) { product ->

                                product?.let {
                                    ProductCard(
                                        name = it.title,
                                        price = it.price,
                                        thumbnail = it.thumbnail,
                                        id = it.id,
                                        viewModel
                                    )
                                }

                            }

                        }
                    }
                } else {
                    NoInternetConnection()
                }
            }

        }

        if (isLoading) {
            ProgressOverlay()
        }
        CustomToast()
    }
    }
}