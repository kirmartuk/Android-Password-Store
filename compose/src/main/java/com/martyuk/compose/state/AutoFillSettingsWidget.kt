package com.martyuk.compose.state

data class AutoFillSettingsSwitchWidget(
  val title: String,
  val isEnabled: Boolean
) : AutoFillSettingsItem

data class AutoFillSettingsTextWithSubtitle(
  val title: String,
  val subtitle: String
) : AutoFillSettingsItem

interface AutoFillSettingsItem
