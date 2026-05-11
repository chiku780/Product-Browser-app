package com.example.ui.screens.home

sealed class HomeScreenEvent {
    data class OnProductClicked(val id:Int?) : HomeScreenEvent()
}