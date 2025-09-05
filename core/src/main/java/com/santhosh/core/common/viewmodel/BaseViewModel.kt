package com.santhosh.core.common.viewmodel

import androidx.lifecycle.ViewModel
import com.santhosh.core.common.AppEventBus
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

open class BaseViewModel : ViewModel(), CoroutineScope {
    // Job that manages the lifecycle of coroutines in the ViewModel
    private val scopeJob: Job = SupervisorJob()

    // CoroutineExceptionHandler for handling uncaught exceptions in the coroutine scope
    private val errorHandler = CoroutineExceptionHandler { _, throwable ->
        sendFailureMessage(throwable.localizedMessage ?: "Failed to add TODO")
    }

    // Coroutine context combining the main dispatcher and the error handler
    override val coroutineContext: CoroutineContext = scopeJob + Dispatchers.Main + errorHandler

    override fun onCleared() {
        // Cancel all coroutines when the ViewModel is cleared
        coroutineContext.cancelChildren()
        super.onCleared()
    }

    private fun sendFailureMessage(message: String) = launch {
        AppEventBus.emitError(message)
    }
}