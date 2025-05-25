package br.com.graest.retinografo.base.arch

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

abstract class StateViewModel<STATE : UIState>(state: STATE) : ViewModel() {

    private val _state: MutableStateFlow<STATE> = MutableStateFlow(state)
    val state: StateFlow<STATE> get() = _state
    private var uiState = state

    protected fun setState(block: (STATE) -> STATE) {
        uiState = state.value.run(block) ?: return
        try {
            _state.update { uiState }
        } catch (_: IllegalStateException) {
            _state.value = uiState
        }
    }
}