package com.santhosh.todolist.viewmodel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before

@OptIn(ExperimentalCoroutinesApi::class)
abstract class BaseViewModelTest {
    // We use UnconfinedTestDispatcher for immediate execution of coroutines.
    val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setupDispatchers() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDownDispatchers() {
        Dispatchers.resetMain()
    }
}