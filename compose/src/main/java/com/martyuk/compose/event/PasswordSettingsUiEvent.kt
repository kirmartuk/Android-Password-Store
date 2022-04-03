package com.martyuk.compose.event

import androidx.compose.runtime.Immutable
import com.martyuk.compose.reducer.UiEvent
import com.martyuk.compose.widget.WidgetItem
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Immutable
sealed class PasswordSettingsUiEvent : UiEvent() {
  @Parcelize
  data class Update(val updatedWidget: @RawValue WidgetItem) : PasswordSettingsUiEvent()

  @Parcelize
  data class ShowData(val items: @RawValue List<WidgetItem>) : PasswordSettingsUiEvent()
}