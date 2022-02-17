package com.martyuk.compose.state

import androidx.compose.runtime.Immutable
import com.martyuk.compose.viewmodel.UiState

@Immutable
data class GeneralSettingsState(
  val data: Map<String, GeneralSettingsItem>
) : UiState {

  companion object {

    fun initial(): GeneralSettingsState {
      return GeneralSettingsState(emptyMap())
    }
  }
}