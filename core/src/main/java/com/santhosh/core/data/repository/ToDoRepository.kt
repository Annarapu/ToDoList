package com.santhosh.core.data.repository

import com.santhosh.core.data.model.ToDo
import kotlinx.coroutines.flow.Flow

interface ToDoRepository {
    fun observeToDos(): Flow<List<ToDo>>
    suspend fun addToDo(toDo: ToDo)
}