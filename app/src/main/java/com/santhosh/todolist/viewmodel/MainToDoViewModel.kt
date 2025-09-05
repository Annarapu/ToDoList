package com.santhosh.todolist.viewmodel

import com.santhosh.core.common.viewmodel.BaseViewModel
import com.santhosh.core.data.model.ToDo
import com.santhosh.core.data.repository.ToDoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@HiltViewModel
class MainToDoViewModel @Inject constructor(private val repository: ToDoRepository) :
    BaseViewModel() {
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _todos = MutableStateFlow<List<ToDo>>(emptyList())
    val todos: StateFlow<List<ToDo>> = _todos.asStateFlow()

    init {
        launch {
            val debouncedSearchQuery = _searchQuery
                .debounce(2000L)
                .distinctUntilChanged()
                .onStart { emit("") }
            combine(
                repository.observeToDos(),
                debouncedSearchQuery
            ) { todos, query ->
                if (query.isBlank()) {
                    todos
                } else {
                    todos.filter { it.title.contains(query, ignoreCase = true) }
                }
            }.collectLatest { filtered ->
                _todos.value = filtered
            }
        }
    }

    fun onSearchQueryChange(newQuery: String) {
        _searchQuery.value = newQuery
    }
}