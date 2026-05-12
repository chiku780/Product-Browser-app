package com.example.ui.screens.productDetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.navigation.NavHostController
import com.appynitty.ui.library.toast.CustomToast
import com.example.ui.component.appbar.WithBackBar
import com.example.ui.events.HandleUiEvents
import com.example.ui.screens.home.component.ProgressOverlay
import com.example.ui.screens.productDetails.component.ProductDetailRow
import com.example.ui.screens.productDetails.viewModel.ProductDetailsViewModel
import org.koin.compose.viewmodel.koinViewModel
import com.example.ui.screens.home.component.NoInternetConnection
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage
import org.jetbrains.compose.resources.painterResource
import productbrowserapp.feature.home.ui.generated.resources.Res
import productbrowserapp.feature.home.ui.generated.resources.profile

@Composable
fun ProductDetailsScreen(navHostController: NavHostController) {

    val viewModel = koinViewModel<ProductDetailsViewModel>()

    val state = rememberPullToRefreshState()
    val isInternetOn by viewModel.isInternetOn.collectAsState(initial = false)
    val uiState by viewModel.uiState.collectAsState()

    var isLoading by remember { mutableStateOf(false) }
    var isRefresh by remember { mutableStateOf(false) }

    LaunchedEffect(Unit){
        viewModel.onEvents(ProductDetailsUiEvents.Init)
    }


    isLoading = uiState.showLoading
    HandleUiEvents(viewModel.event)

    Scaffold(
        topBar = {
            WithBackBar(uiState.title ?: "Product Details") {
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
                        viewModel.onEvents(ProductDetailsUiEvents.SwipeRefresh)
                    } // Trigger data refresh
                ) {
                    if (isInternetOn) {

                        LazyColumn(
                            modifier = Modifier.fillMaxSize()
                        ) {

                            item {
                                CoilImage(
                                    imageModel = { uiState.thumbnail },
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

                            items(uiState.productDetails ?: emptyList()) { pair ->
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