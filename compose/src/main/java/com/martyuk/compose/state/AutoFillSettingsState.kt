package com.martyuk.compose.state

import androidx.compose.runtime.Immutable
import com.martyuk.compose.reducer.UiState
import com.martyuk.compose.widget.WidgetItem

@Immutable
data class AutoFillSettingsState(
  val data: List<WidgetItem>
) : UiState {

  companion object {

    fun initial(): AutoFillSettingsState {
      return AutoFillSettingsState(emptyList())
    }
  }
}