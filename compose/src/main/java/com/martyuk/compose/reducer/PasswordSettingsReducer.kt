package com.martyuk.compose.reducer

import com.martyuk.compose.event.PasswordSettingsUiEvent
import com.martyuk.compose.state.PasswordSettingsState
import javax.inject.Singleton

@Singleton
class PasswordSettingsReducer(
  generalSettingsState: PasswordSettingsState
) : Reducer<PasswordSettingsState, PasswordSettingsUiEvent>(generalSettingsState) {

  override fun reduce(oldState: PasswordSettingsState, event: PasswordSettingsUiEvent) {
    when (event) {
      is PasswordSettingsUiEvent.ShowData -> {
        setState(oldState.copy(data = event.items))
      }
      is PasswordSettingsUiEvent.Update -> {
        val oldStateItems = oldState.data.toMutableMap()
        oldStateItems[event.key] = event.value
        setState(oldState.copy(data = oldStateItems))
      }
    }
  }
}