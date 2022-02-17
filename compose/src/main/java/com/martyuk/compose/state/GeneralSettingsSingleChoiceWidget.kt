package com.martyuk.compose.state

data class GeneralSettingsSingleChoiceWidget(
  val title: String,
  val subTitle: String
) : GeneralSettingsItem

data class GeneralSettingsCheckboxWidget(
  val title: String,
  val subTitle: String,
  val isSelected: Boolean
) : GeneralSettingsItem


interface GeneralSettingsItem
interface SettingsItem