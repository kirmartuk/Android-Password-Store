package com.martyuk.compose.reducer

import com.martyuk.compose.event.AutoFillSettingsUiEvent
import com.martyuk.compose.state.AutoFillSettingsState
import com.martyuk.compose.widget.WidgetItem
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
        val oldStateItems: MutableList<WidgetItem> = oldState.data.toMutableList()
        val index: Int = oldStateItems.indexOfFirst { widgetItem -> widgetItem.widgetName == event.updatedWidget.widgetName }
        oldStateItems[index] = event.updatedWidget
        setState(oldState.copy(data = oldStateItems))
      }
    }
  }
}