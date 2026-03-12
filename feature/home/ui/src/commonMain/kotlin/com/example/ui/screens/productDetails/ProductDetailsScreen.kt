package com.example.ui.screens.productDetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.appynitty.common.events.CommonEvents
import com.appynitty.ui.library.toast.CustomToast
import com.example.domain.events.productDetails.ProductDetailsScreenEvents
import com.example.ui.component.appbar.WithBackBar
import com.example.ui.screens.home.component.ProgressOverlay
import com.example.ui.screens.productDetails.component.ProductDetailRow
import com.example.ui.screens.productDetails.viewModel.ProductDetailsViewModel
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import com.appynitty.ui.library.toast.ToastController
import com.appynitty.ui.library.toast.ToastType
import com.example.ui.screens.home.component.NoInternetConnection
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage
import org.jetbrains.compose.resources.painterResource
import productbrowserapp.feature.home.ui.generated.resources.Res
import productbrowserapp.feature.home.ui.generated.resources.profile

@Composable
fun ProductDetailsScreen(navHostController: NavHostController, id: String) {

    val viewModel = koinViewModel<ProductDetailsViewModel>()

    val state = rememberPullToRefreshState()
    val isInternetOn by viewModel.isInternetOn.collectAsState(initial = false)
    val productList by viewModel.productDetailsList.collectAsStateWithLifecycle()
    val thumbNail by viewModel.thumbNail.collectAsStateWithLifecycle()
    val tittle by viewModel.tittle.collectAsStateWithLifecycle()

    var isLoading by remember { mutableStateOf(false) }
    var isRefresh by remember { mutableStateOf(false) }


    println("id is $id")

//    LaunchedEffect(Unit){
//        viewModel.onEvents(ProductDetailsUiEvents.ProductDetailsApiCall(id.toInt()))
//    }

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
            viewModel.productDetailsChannel.collect { event ->
                when (event) {

                    is ProductDetailsScreenEvents.GetProductDetails -> {
                       viewModel.setProductList(event.result,event?.thumbNail,event.tittle)
                    }
                }
            }
        }

    }

    Scaffold(
        topBar = {
            WithBackBar(tittle ?: "Product Details") {
                navHostController.popBackStack()
            }
        }
    ) { scaffoldPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {

            Column(
                modifier = Modifier
                    .padding(scaffoldPadding)
                    .fillMaxSize()
                    .padding(top = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                PullToRefreshBox(
                    state = state, // Pass the state
                    isRefreshing = isRefresh, // Observe the loading state
                    onRefresh = { isRefresh = false
                        viewModel.onEvents(ProductDetailsUiEvents.SwipeRefresh(id.toInt()))
                    } // Trigger data refresh
                ) {
                    if (isInternetOn) {

                        LazyColumn(
                            modifier = Modifier.fillMaxSize()
                        ) {

                            item {
                                CoilImage(
                                    imageModel = { thumbNail },
                                    imageOptions = ImageOptions(
                                        contentScale = ContentScale.Crop,
                                        alignment = Alignment.Center,
                                        contentDescription = "Image"
                                    ),
                                    failure = {
                                        Image(
                                            painter = painterResource(Res.drawable.profile),
                                            contentDescription = "Default Image",
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(16.dp)
                                                .height(220.dp)
                                                .clip(RoundedCornerShape(8.dp)),
                                            contentScale = ContentScale.Crop
                                        )
                                    },
                                    loading = {
                                        Image(
                                            painter = painterResource(Res.drawable.profile),
                                            contentDescription = "Default Image",
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(16.dp)
                                                .height(220.dp)
                                                .clip(RoundedCornerShape(8.dp)),
                                            contentScale = ContentScale.Crop
                                        )
                                    },
                                    modifier =Modifier.fillMaxWidth()
                                        .padding(16.dp)
                                        .height(220.dp).clip(RoundedCornerShape(8.dp)),
                                )
                            }

                            items(productList ?: emptyList()) { pair ->
                                ProductDetailRow(
                                    title = pair.first ?: "",
                                    value = pair.second ?: ""
                                )
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