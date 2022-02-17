package com.martyuk.compose.event

import androidx.compose.runtime.Immutable
import com.martyuk.compose.state.AutoFillSettingsItem
import com.martyuk.compose.viewmodel.UiEvent

@Immutable
@Parcelize
sealed class AutoFillSettingsUiEvent : UiEvent {

  data class Update(val key: String, val value: AutoFillSettingsItem) : AutoFillSettingsUiEvent()
  data class ShowData(val items: Map<String, AutoFillSettingsItem>) : AutoFillSettingsUiEvent()
  object DismissDialog : AutoFillSettingsUiEvent()
}