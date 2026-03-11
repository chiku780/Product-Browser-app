package com.example.ui.screens.home


sealed class HomeScreenUiEvents {


    data class OnItemClicked(val id:Int?) : HomeScreenUiEvents()

}