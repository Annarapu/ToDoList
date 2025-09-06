package com.santhosh.todolist.fake

import com.santhosh.core.data.model.ToDo
import com.santhosh.core.data.repository.ToDoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeToDoRepository : ToDoRepository {
    private val todos = mutableListOf<ToDo>()
    private val todosFlow = MutableStateFlow<List<ToDo>>(emptyList())

    override fun observeToDos(): Flow<List<ToDo>> = todosFlow
    override suspend fun addToDo(toDo: ToDo) {
        todos.add(toDo)
        todosFlow.value = todos.toList()
    }
}