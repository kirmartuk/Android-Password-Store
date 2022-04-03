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
    Screen.RepositorySettings -> {
      listOf(
        PreferenceKeys.REBASE_ON_PULL
      )
    }
    else -> throw Exception("")
  }
}