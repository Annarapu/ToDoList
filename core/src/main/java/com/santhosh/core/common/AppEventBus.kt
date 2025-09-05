package com.santhosh.core.common

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object AppEventBus {
    private val _errors = MutableSharedFlow<String>(extraBufferCapacity = 10)
    val errors = _errors.asSharedFlow()

    suspend fun emitError(message: String) {
        _errors.emit(message)
    }
}