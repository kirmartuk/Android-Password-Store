package com.martyuk.compose.screen

import com.martyuk.utils.extensions.PreferenceKeys

enum class Screen {
  Settings,
  Welcome,
  GeneralSettings,
  AutoFillSettings,
  PasswordSettings,
  RepositorySettings,
  SshKeyGenerator,
  EnableAutoFillDialog,
  SingleChoiceDialog,
  UserInputDialog
}

fun getScreenItems(screen: Screen): List<String> {
  return when (screen) {
    Screen.AutoFillSettings -> {
      listOf(
        PreferenceKeys.AUTOFILL_ENABLE,
        PreferenceKeys.OREO_AUTOFILL_DIRECTORY_STRUCTURE,
        PreferenceKeys.OREO_AUTOFILL_DEFAULT_USERNAME,
        PreferenceKeys.OREO_AUTOFILL_CUSTOM_PUBLIC_SUFFIXES
      )
    }
    Screen.GeneralSettings -> {
      listOf(
        PreferenceKeys.APP_THEME,
        PreferenceKeys.SORT_ORDER,
        PreferenceKeys.FILTER_RECURSIVELY,
        PreferenceKeys.SEARCH_ON_START,
        PreferenceKeys.SHOW_HIDDEN_CONTENTS
      )
    }
    Screen.PasswordSettings -> {
      listOf(
        PreferenceKeys.PREF_KEY_PWGEN_TYPE,
        PreferenceKeys.GENERAL_SHOW_TIME,
        PreferenceKeys.SHOW_PASSWORD,
        PreferenceKeys.COPY_ON_DECRYPT
      )
    }
    Screen.RepositorySettings -> {
      listOf(
        PreferenceKeys.REBASE_ON_PULL
      )
    }
    else -> throw Exception("")
  }
}