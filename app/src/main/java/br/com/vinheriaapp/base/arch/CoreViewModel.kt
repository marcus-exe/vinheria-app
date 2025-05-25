package br.com.graest.retinografo.base.arch

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

open class CoreViewModel<STATE : UIState, EFFECT: UIEffect>(state: STATE): StateViewModel<STATE>(state) {
    private val _effects = Channel<EFFECT>(capacity = Channel.CONFLATED)
    val effect = _effects.receiveAsFlow()

    protected fun sendEffect(effect: EFFECT) {
        _effects.trySend(effect)
    }
}