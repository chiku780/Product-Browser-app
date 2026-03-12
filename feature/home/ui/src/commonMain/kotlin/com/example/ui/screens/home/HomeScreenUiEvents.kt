package com.example.ui.screens.home


sealed class HomeScreenUiEvents {

    data class OnItemClicked(val id:Int?) : HomeScreenUiEvents()
    data class OnSearchQuery(val query: String) : HomeScreenUiEvents()
    object SwipeRefresh : HomeScreenUiEvents()

}