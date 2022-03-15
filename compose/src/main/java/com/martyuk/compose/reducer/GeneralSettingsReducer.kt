package com.martyuk.compose.reducer

import com.martyuk.compose.state.GeneralSettingsState
import com.martyuk.compose.event.GeneralSettingsUiEvent
import javax.inject.Singleton

@Singleton
class GeneralSettingsReducer(
  generalSettingsState: GeneralSettingsState
) : Reducer<GeneralSettingsState, GeneralSettingsUiEvent>(generalSettingsState) {

  override fun reduce(oldState: GeneralSettingsState, event: GeneralSettingsUiEvent) {
    when (event) {
      is GeneralSettingsUiEvent.ShowData -> {
        setState(oldState.copy(data = event.items))
      }
      is GeneralSettingsUiEvent.Update -> {
        val oldStateItems = oldState.data.toMutableMap()
        oldStateItems[event.key] = event.value
        setState(oldState.copy(data = oldStateItems))
      }
    }
  }
}