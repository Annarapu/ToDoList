package com.santhosh.todolist.viewmodel

import com.santhosh.todolist.fake.FakeToDoRepository
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AddToDoViewModelTest : BaseViewModelTest() {
    private lateinit var repository: FakeToDoRepository
    private lateinit var viewModel: AddToDoViewModel

    @Before
    fun setup() {
        repository = FakeToDoRepository()
        viewModel = AddToDoViewModel(repository)
    }

    @Test
    fun initialStateHasEmptyTodo() = runTest {
        val state = viewModel.uiState.value
        assertEquals("", state.toDo.title)
        assertEquals("", state.toDo.description)
        assertFalse(state.isLoading)
    }

    @Test
    fun onTitleChangeUpdatesTitle() = runTest {
        viewModel.onTitleChange("New Task")
        assertEquals("New Task", viewModel.uiState.value.toDo.title)
    }

    @Test
    fun onDescriptionChangeUpdatesDescription() = runTest {
        viewModel.onDescriptionChange("Description")
        assertEquals("Description", viewModel.uiState.value.toDo.description)
    }

    @Test
    fun addToDoResetsLoadingAndNavigateBack() = runTest {
        var navigatedBack = false
        viewModel.onTitleChange("Valid")
        viewModel.onDescriptionChange("Desc")
        viewModel.addTodo { navigatedBack = true }
        advanceUntilIdle()
        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertTrue(navigatedBack)
    }

}