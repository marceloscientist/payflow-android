package io.payflow.android.core.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.payflow.android.core.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<T> : ViewModel() {

    protected val _uiState =
        MutableStateFlow<UiState<T>>(UiState.Loading)

    val uiState: StateFlow<UiState<T>>
        get() = _uiState.asStateFlow()

    protected fun updateState(
        state: UiState<T>
    ) {
        _uiState.value = state
    }

    protected fun launch(
        block: suspend () -> Unit
    ) {
        viewModelScope.launch {
            block()
        }
    }
}
