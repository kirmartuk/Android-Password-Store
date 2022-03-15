package com.martyuk.compose.reducer

import com.martyuk.compose.state.AutoFillSettingsState
import com.martyuk.compose.event.AutoFillSettingsUiEvent
import javax.inject.Singleton

@Singleton
class AutoFillSettingsReducer(autoFillSettingsState: AutoFillSettingsState)
  : Reducer<AutoFillSettingsState, AutoFillSettingsUiEvent>(autoFillSettingsState) {

  override fun reduce(oldState: AutoFillSettingsState, event: AutoFillSettingsUiEvent) {
    when (event) {
      is AutoFillSettingsUiEvent.ShowData -> {
        setState(oldState.copy(data = event.items))
      }
      is AutoFillSettingsUiEvent.Update -> {
        val oldStateItems = oldState.data.toMutableMap()
        oldStateItems[event.key] = event.value
        setState(oldState.copy(data = oldStateItems))
      }
    }
  }
}