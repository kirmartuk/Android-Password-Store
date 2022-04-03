package com.martyuk.formatter

import com.martyuk.compose.R
import com.martyuk.compose.event.AutoFillSettingsUiEvent
import com.martyuk.compose.event.PasswordSettingsUiEvent
import com.martyuk.compose.reducer.UiEvent
import com.martyuk.compose.repository.StateRepository.Companion.GENERAL_SHOW_TIME_DEFAULT_VALUE
import com.martyuk.compose.vo.UserInputDialogVo
import com.martyuk.compose.widget.TextWithSubtitleWidget
import com.martyuk.compose.widget.WidgetsNames
import com.martyuk.utils.extensions.DataStoreManager
import com.martyuk.utils.extensions.PreferenceKeys
import com.martyuk.utils.extensions.ResourcesManager
import javax.inject.Inject

class UserInputDialogFormatter @Inject constructor(
  private val resourcesManager: ResourcesManager,
  private val dataStoreManager: DataStoreManager
) {

  fun format(key: String): UserInputDialogVo {
    return when (key) {
      PreferenceKeys.OREO_AUTOFILL_DEFAULT_USERNAME -> {
        UserInputDialogVo(
          title = resourcesManager.getString(R.string.preference_default_username_title),
          subtitle = dataStoreManager.getStringBlocking(PreferenceKeys.OREO_AUTOFILL_DEFAULT_USERNAME) ?: ""
        )
      }
      PreferenceKeys.OREO_AUTOFILL_CUSTOM_PUBLIC_SUFFIXES -> {
        UserInputDialogVo(
          title = resourcesManager.getString(R.string.preference_custom_public_suffixes_title),
          subtitle = dataStoreManager.getStringBlocking(PreferenceKeys.OREO_AUTOFILL_CUSTOM_PUBLIC_SUFFIXES) ?: ""
        )
      }
      PreferenceKeys.GENERAL_SHOW_TIME -> {
        UserInputDialogVo(
          title = resourcesManager.getString(R.string.pref_clipboard_timeout_title),
          subtitle = dataStoreManager.getStringBlocking(PreferenceKeys.GENERAL_SHOW_TIME)
            ?: GENERAL_SHOW_TIME_DEFAULT_VALUE.toString()
        )
      }
      else -> throw Exception("")
    }
  }

  fun formatUiEvent(key: String, title: String, subtitle: String): UiEvent {
    return when (key) {
      PreferenceKeys.OREO_AUTOFILL_DEFAULT_USERNAME -> {
        AutoFillSettingsUiEvent.Update(
          TextWithSubtitleWidget(title, subtitle, widgetName = WidgetsNames.AUTOFILL_SETTINGS_DEFAULT_USERNAME)
        )
      }
      PreferenceKeys.OREO_AUTOFILL_CUSTOM_PUBLIC_SUFFIXES -> {
        AutoFillSettingsUiEvent.Update(
          TextWithSubtitleWidget(title, subtitle, widgetName = WidgetsNames.AUTOFILL_SETTINGS_CUSTOM_DOMAINS)
        )
      }
      PreferenceKeys.GENERAL_SHOW_TIME -> {
        PasswordSettingsUiEvent.Update(
          key,
          TextWithSubtitleWidget(title, subtitle, widgetName = WidgetsNames.PASSWORD_SETTINGS_COPY_TIMEOUT)
        )
      }
      else -> throw Exception("")
    }
  }
}