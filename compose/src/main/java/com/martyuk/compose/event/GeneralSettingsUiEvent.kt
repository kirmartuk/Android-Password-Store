package com.martyuk.compose.event

import androidx.compose.runtime.Immutable
import com.martyuk.compose.state.GeneralSettingsItem
import com.martyuk.compose.viewmodel.UiEvent

@Immutable
sealed class GeneralSettingsUiEvent : UiEvent {

  data class Update(val key: String, val value: GeneralSettingsItem) : GeneralSettingsUiEvent()
  data class ShowData(val items: Map<String, GeneralSettingsItem>) : GeneralSettingsUiEvent()
  object DismissDialog : GeneralSettingsUiEvent()
}