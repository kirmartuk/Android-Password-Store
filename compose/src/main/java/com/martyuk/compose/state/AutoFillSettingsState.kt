package com.martyuk.compose.state

import androidx.compose.runtime.Immutable
import com.martyuk.compose.viewmodel.UiState

@Immutable
data class AutoFillSettingsState(
  val data: Map<String, AutoFillSettingsItem>
) : UiState {

  companion object {

    fun initial(): AutoFillSettingsState {
      return AutoFillSettingsState(emptyMap())
    }
  }
}