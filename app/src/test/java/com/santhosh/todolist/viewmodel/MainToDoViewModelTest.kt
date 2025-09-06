package com.santhosh.todolist.viewmodel

import com.santhosh.core.data.model.ToDo
import com.santhosh.todolist.fake.FakeToDoRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MainToDoViewModelTest : BaseViewModelTest() {

    private lateinit var repository: FakeToDoRepository
    private lateinit var viewModel: MainToDoViewModel

    @Before
    fun setup() {
        repository = FakeToDoRepository()
        viewModel = MainToDoViewModel(repository)
    }

    @Test
    fun initialStateIsEmptyList() = runTest {
        val result = viewModel.todos.value
        assertEquals(0, result.size)
    }

    @Test
    fun addingToDoUpdateState() = runTest {
        repository.addToDo(ToDo(title = "Test Task"))
        val result = viewModel.todos.value
        assertEquals(1, result.size)
        assertEquals("Test Task", result[0].title)
    }

    @Test
    fun searchQueryFiltersToDosBeforeAfterDebounce() = runTest {
        repository.addToDo(ToDo(title = "Santhosh Reddy"))
        repository.addToDo(ToDo(title = "Android"))

        viewModel.onSearchQueryChange("santhosh")
        val resultBefore = viewModel.todos.value
        assertEquals(2, resultBefore.size)
        advanceTimeBy(3000L)
        val resultAfter = viewModel.todos.value
        assertEquals(1, resultAfter.size)
        assertEquals("Santhosh Reddy", resultAfter[0].title)
    }
}