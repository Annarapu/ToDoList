package com.santhosh.todolist.viewmodel

import android.os.Bundle
import androidx.navigation.NavDestination
import com.santhosh.core.common.viewmodel.BaseViewModel
import com.santhosh.core.model.TopBarData
import com.santhosh.todolist.ui.navigation.AddToDoDestination
import com.santhosh.todolist.ui.navigation.MainToDoDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ToDoListActivityViewModel @Inject constructor() : BaseViewModel() {
    private val _topBarState: MutableStateFlow<TopBarData> = MutableStateFlow(TopBarData())
    val topBarState: StateFlow<TopBarData> = _topBarState

    private val _errorBannerState: MutableStateFlow<String> = MutableStateFlow("")
    val errorBannerState: MutableStateFlow<String> = _errorBannerState

    fun setTopBarData(destination: NavDestination, arguments: Bundle?) = launch {
        val destinationRoute = destination.route
        when (destinationRoute) {
            MainToDoDestination.route -> {
                _topBarState.value = TopBarData("ToDo List")
            }

            AddToDoDestination.route -> {
                _topBarState.value = TopBarData("Add ToDo")
            }

            else -> {
                _topBarState.value = TopBarData("ToDo List")
            }
        }
    }
}