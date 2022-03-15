package com.martyuk.compose.reducer

import android.os.Parcelable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class Reducer<S : UiState, E : UiEvent>(initialVal: S) {

  private val _state: MutableStateFlow<S> = MutableStateFlow(initialVal)
  val state: StateFlow<S>
    get() = _state

  fun sendEvent(event: E) {
    reduce(_state.value, event)
  }

  fun setState(newState: S) {
    val success = _state.tryEmit(newState)
  }

  abstract fun reduce(oldState: S, event: E)
}

interface UiState
abstract class UiEvent: Parcelable {
  companion object {
    const val name = "UiEvent"
  }
}