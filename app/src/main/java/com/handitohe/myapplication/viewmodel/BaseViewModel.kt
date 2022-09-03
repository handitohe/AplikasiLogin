package com.handitohe.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.stateIn

abstract class BaseViewModel<STATE, EVENT>(
    initialState: STATE,
) : ViewModel() {

    private companion object {
        const val EXTRA_BUFFER_CAPACITY = 10
    }

    private val eventFlow = MutableSharedFlow<EVENT>(onBufferOverflow = BufferOverflow.DROP_OLDEST, extraBufferCapacity = EXTRA_BUFFER_CAPACITY)

    @OptIn(FlowPreview::class)
    val state: StateFlow<STATE> = eventFlow.flatMapConcat {
        mapEventToState(it)
    }.stateIn(viewModelScope, SharingStarted.Eagerly, initialState)

    fun invoke(event: EVENT) {
        eventFlow.tryEmit(event)
    }

    abstract suspend fun mapEventToState(event: EVENT): Flow<STATE>

}

