package com.santhosh.todolist.ui.states

import com.santhosh.core.data.model.ToDo

data class AddToDoUiState(
    val toDo: ToDo,
    val isLoading: Boolean = false,
)