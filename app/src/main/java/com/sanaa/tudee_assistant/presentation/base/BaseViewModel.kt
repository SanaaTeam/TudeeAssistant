package com.sanaa.tudee_assistant.presentation.base


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<T>(
    initialState: T,
    val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ViewModel() {
    protected val _state: MutableStateFlow<T> by lazy { MutableStateFlow(initialState) }
    val state: StateFlow<T> by lazy { _state.asStateFlow() }

    protected fun <T> tryToExecute(
        callee: suspend () -> T,
        onSuccess: (T) -> Unit = {},
        onError: (exception: Exception) -> Unit = {},
        dispatcher: CoroutineDispatcher = defaultDispatcher,
    ) {
        viewModelScope.launch(dispatcher) {
            try {
                val result = callee()
                onSuccess(result)
            } catch (exception: Exception) {
                onError(exception)
            }
        }
    }

}