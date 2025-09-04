package com.santhosh.core.data.repository

import com.santhosh.core.data.database.dao.ToDoDao
import com.santhosh.core.data.database.entity.ToDoEntity
import com.santhosh.core.data.model.ToDo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ToDoRepositoryImpl @Inject constructor(private val dao: ToDoDao) : ToDoRepository {
    override fun observeToDos(): Flow<List<ToDo>> {
        return dao.observeAll()
            .map { todos ->
                todos.map { toDo ->
                    ToDo(
                        id = toDo.id,
                        title = toDo.title,
                        description = toDo.description ?: ""
                    )
                }
            }
    }

    override suspend fun addToDo(title: String, description: String?) {
        dao.insert(
            ToDoEntity(
                title = title,
                description = description
            )
        )
    }
}