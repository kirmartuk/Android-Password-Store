package com.martyuk.compose.reducer

import com.martyuk.compose.event.PasswordSettingsUiEvent
import com.martyuk.compose.state.PasswordSettingsState
import com.martyuk.compose.widget.WidgetItem
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
        val oldStateItems: MutableList<WidgetItem> = oldState.data.toMutableList()
        val index: Int = oldStateItems.indexOfFirst { widgetItem ->
          widgetItem.widgetName == event.updatedWidget.widgetName
        }
        oldStateItems[index] = event.updatedWidget
        setState(oldState.copy(data = oldStateItems))
      }
    }
  }
}