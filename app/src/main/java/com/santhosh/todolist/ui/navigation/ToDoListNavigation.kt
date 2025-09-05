package com.santhosh.todolist.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.santhosh.todolist.ui.route.AddToDoRoute
import com.santhosh.todolist.ui.route.MainToDoRoute


fun NavController.navigateToAddToDoRoute() {
    navigate(AddToDoDestination.route)
}

fun NavGraphBuilder.todoListGraph(
    navigateBack: () -> Unit,
    navigateToAddToDo: () -> Unit
) {

    composable(MainToDoDestination.route) {
        MainToDoRoute(onFabClick = navigateToAddToDo)
    }

    composable(AddToDoDestination.route) {
        AddToDoRoute(navigateBack = navigateBack)
    }
}