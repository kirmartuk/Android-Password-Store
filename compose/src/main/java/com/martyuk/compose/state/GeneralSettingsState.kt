package com.martyuk.compose.state

import androidx.compose.runtime.Immutable
import com.martyuk.compose.reducer.UiState
import com.martyuk.compose.widget.WidgetItem

@Immutable
data class GeneralSettingsState(
  val data: List<WidgetItem>
) : UiState {

  companion object {

    fun initial(): GeneralSettingsState {
      return GeneralSettingsState(emptyList())
    }
  }
}

