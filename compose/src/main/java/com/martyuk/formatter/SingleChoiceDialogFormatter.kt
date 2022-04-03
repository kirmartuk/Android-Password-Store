package com.martyuk.formatter

import com.martyuk.compose.R
import com.martyuk.compose.event.AutoFillSettingsUiEvent
import com.martyuk.compose.event.GeneralSettingsUiEvent
import com.martyuk.compose.event.PasswordSettingsUiEvent
import com.martyuk.compose.reducer.UiEvent
import com.martyuk.compose.utils.indexOfOrNull
import com.martyuk.compose.vo.SingleChoiceDialogVo
import com.martyuk.compose.widget.TextWithSubtitleWidget
import com.martyuk.compose.widget.WidgetsNames
import com.martyuk.utils.extensions.DataStoreManager
import com.martyuk.utils.extensions.PreferenceKeys
import com.martyuk.utils.extensions.ResourcesManager
import javax.inject.Inject

class SingleChoiceDialogFormatter @Inject constructor(
  private val resourcesManager: ResourcesManager,
  private val dataStoreManager: DataStoreManager
) {

  fun format(key: String): SingleChoiceDialogVo {
    val selectedItem = dataStoreManager.getStringBlocking(key)
    return when (key) {
      PreferenceKeys.SORT_ORDER -> {
        val radioOptions = resourcesManager.getStringArray(R.array.sort_order_entries).asList()
        SingleChoiceDialogVo(
          title = resourcesManager.getString(R.string.pref_sort_order_title),
          radioOptions = radioOptions,
          indexOfSelectedItem = getIndexOfSelectedItemInRadioOptions(radioOptions, selectedItem)
        )
      }
      PreferenceKeys.APP_THEME -> {
        val radioOptions = resourcesManager.getStringArray(R.array.app_theme_options).asList()
        SingleChoiceDialogVo(
          title = resourcesManager.getString(R.string.pref_app_theme_title),
          radioOptions = radioOptions,
          indexOfSelectedItem = getIndexOfSelectedItemInRadioOptions(radioOptions, selectedItem))
      }
      PreferenceKeys.OREO_AUTOFILL_DIRECTORY_STRUCTURE -> {
        val radioOptions = resourcesManager.getStringArray(R.array.sort_order_entries).asList()
        SingleChoiceDialogVo(
          title = resourcesManager.getString(R.string.oreo_autofill_preference_directory_structure),
          radioOptions = radioOptions,
          indexOfSelectedItem = getIndexOfSelectedItemInRadioOptions(radioOptions, selectedItem))
      }
      PreferenceKeys.PREF_KEY_PWGEN_TYPE -> {
        val radioOptions = resourcesManager.getStringArray(R.array.pwgen_provider_labels).asList()
        SingleChoiceDialogVo(
          title = resourcesManager.getString(R.string.pref_password_generator_type_title),
          radioOptions = radioOptions,
          indexOfSelectedItem = getIndexOfSelectedItemInRadioOptions(radioOptions, selectedItem)
        )
      }
      else -> {
        throw Exception("")
      }
    }
  }

  fun formatUiEvent(key: String, title: String, subtitle: String): UiEvent {
    return when (key) {
      PreferenceKeys.OREO_AUTOFILL_DIRECTORY_STRUCTURE -> {
        AutoFillSettingsUiEvent.Update(
          TextWithSubtitleWidget(title, subtitle, widgetName = WidgetsNames.AUTOFILL_SETTINGS_PASSWORD_FILE_ORGANISATION)
        )
      }
      PreferenceKeys.APP_THEME -> {
        GeneralSettingsUiEvent.Update(TextWithSubtitleWidget(title, subtitle, widgetName = WidgetsNames.GENERAL_SETTINGS_APP_THEME))
      }
      PreferenceKeys.SORT_ORDER -> {
        GeneralSettingsUiEvent.Update(TextWithSubtitleWidget(title, subtitle, widgetName = WidgetsNames.GENERAL_SETTINGS_SORT_ORDER))
      }
      PreferenceKeys.PREF_KEY_PWGEN_TYPE -> {
        PasswordSettingsUiEvent.Update(key, TextWithSubtitleWidget(title, subtitle, widgetName = WidgetsNames.PASSWORD_SETTINGS_PASSWORD_GENERATOR_TYPE))
      }
      else -> throw Exception("")
    }
  }

  private fun getIndexOfSelectedItemInRadioOptions(radioOptions: List<String>, selectedItem: String?): Int {
    return selectedItem?.let { radioOptions.indexOfOrNull(selectedItem) } ?: 0
  }
}
