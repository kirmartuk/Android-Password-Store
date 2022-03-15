package com.martyuk.compose.event

import androidx.compose.runtime.Immutable
import com.martyuk.compose.reducer.UiEvent
import com.martyuk.compose.widget.WidgetItem
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Immutable
sealed class AutoFillSettingsUiEvent : UiEvent() {

  @Parcelize
  data class Update(val key: String, val value: @RawValue WidgetItem) : AutoFillSettingsUiEvent()

  @Parcelize
  data class ShowData(val items: @RawValue Map<String, WidgetItem>) : AutoFillSettingsUiEvent()
}