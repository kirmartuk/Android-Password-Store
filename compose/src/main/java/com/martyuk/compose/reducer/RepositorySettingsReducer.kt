package com.martyuk.compose.reducer

import com.martyuk.compose.event.RepositorySettingsUiEvent
import com.martyuk.compose.state.RepositorySettingsState
import javax.inject.Singleton

@Singleton
class RepositorySettingsReducer(
  repositorySettingsState: RepositorySettingsState
) : Reducer<RepositorySettingsState, RepositorySettingsUiEvent>(repositorySettingsState) {

  override fun reduce(oldState: RepositorySettingsState, event: RepositorySettingsUiEvent) {
    when (event) {
      is RepositorySettingsUiEvent.ShowData -> {
        setState(oldState.copy(data = event.items))
      }
      is RepositorySettingsUiEvent.Update -> {
        val oldStateItems = oldState.data.toMutableMap()
        oldStateItems[event.key] = event.value
        setState(oldState.copy(data = oldStateItems))
      }
    }
  }
}