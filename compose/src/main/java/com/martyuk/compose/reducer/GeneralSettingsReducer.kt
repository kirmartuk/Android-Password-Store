package com.martyuk.compose.reducer

import com.martyuk.compose.event.GeneralSettingsUiEvent
import com.martyuk.compose.state.GeneralSettingsState
import com.martyuk.compose.widget.WidgetItem
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