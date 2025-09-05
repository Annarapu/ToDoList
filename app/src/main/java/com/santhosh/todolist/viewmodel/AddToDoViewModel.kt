package com.santhosh.todolist.viewmodel

import com.santhosh.core.common.viewmodel.BaseViewModel
import com.santhosh.core.data.model.ToDo
import com.santhosh.core.data.repository.ToDoRepository
import com.santhosh.todolist.ui.states.AddToDoUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddToDoViewModel @Inject constructor(private val repository: ToDoRepository) :
    BaseViewModel() {
    private val _uiState =
        MutableStateFlow(AddToDoUiState(toDo = ToDo(title = "", description = "")))
    val uiState: StateFlow<AddToDoUiState> = _uiState.asStateFlow()

    fun onTitleChange(newTitle: String) {
        _uiState.update { state ->
            state.copy(toDo = state.toDo.copy(title = newTitle))
        }
    }

    fun onDescriptionChange(newDesc: String) {
        _uiState.update { state ->
            state.copy(toDo = state.toDo.copy(description = newDesc))
        }
    }

    fun addTodo(navigateBack: () -> Unit) = launch {
        _uiState.update { it.copy(isLoading = true) }
        val title = _uiState.value.toDo.title.trim()
        if (title.isEmpty() || title.equals("Error", ignoreCase = true)) {
            _uiState.update { it.copy(isLoading = false) }
            navigateBack()
            throw IllegalArgumentException("Failed to add TODO")
        } else {
            repository.addToDo(_uiState.value.toDo)
            delay(3000)
            _uiState.value = _uiState.value.copy(isLoading = false)
            navigateBack()
        }

    }
}