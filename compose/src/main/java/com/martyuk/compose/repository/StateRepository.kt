package com.martyuk.compose.repository

import com.martyuk.compose.R
import com.martyuk.compose.state.AutoFillSettingsState
import com.martyuk.compose.state.AutoFillSettingsSwitchWidget
import com.martyuk.compose.state.AutoFillSettingsTextWithSubtitle
import com.martyuk.compose.state.GeneralSettingsCheckboxWidget
import com.martyuk.compose.state.GeneralSettingsSingleChoiceWidget
import com.martyuk.compose.state.GeneralSettingsState
import com.martyuk.compose.utils.DataStoreManager
import com.martyuk.compose.utils.ResourcesManager
import com.martyuk.utils.extensions.PreferenceKeys
import javax.inject.Inject

class StateRepository @Inject constructor(
  private val resourcesManager: ResourcesManager,
  private val dataStoreManager: DataStoreManager
) {

  fun getGeneralSettingsState(): GeneralSettingsState {
    val appThemeSubTitle =
      dataStoreManager.getStringBlocking(PreferenceKeys.APP_THEME)
    val prefSortOrderSubTitle =
      dataStoreManager.getStringBlocking(PreferenceKeys.SORT_ORDER)
    return GeneralSettingsState(
      data = mapOf(
        PreferenceKeys.APP_THEME to
          GeneralSettingsSingleChoiceWidget(
            title = resourcesManager.getString(R.string.pref_app_theme_title),
            subTitle = appThemeSubTitle ?: resourcesManager.getString(R.string.pref_app_theme_value_light)),
        PreferenceKeys.SORT_ORDER to GeneralSettingsSingleChoiceWidget(
          title = resourcesManager.getString(R.string.pref_sort_order_title),
          subTitle = prefSortOrderSubTitle ?: resourcesManager.getString(R.string.pref_file_first_sort_order)
        ),
        PreferenceKeys.FILTER_RECURSIVELY to
          GeneralSettingsCheckboxWidget(
            title = resourcesManager.getString(R.string.pref_recursive_filter_title),
            subTitle = resourcesManager.getString(R.string.pref_recursive_filter_summary),
            dataStoreManager.getBooleanBlocking(PreferenceKeys.FILTER_RECURSIVELY) ?: true
          ),
        PreferenceKeys.SEARCH_ON_START to
          GeneralSettingsCheckboxWidget(
            title = resourcesManager.getString(R.string.pref_search_on_start_title),
            subTitle = resourcesManager.getString(R.string.pref_search_on_start_summary),
            dataStoreManager.getBooleanBlocking(PreferenceKeys.SEARCH_ON_START) ?: false
          ),
        PreferenceKeys.SHOW_HIDDEN_CONTENTS to
          GeneralSettingsCheckboxWidget(
            title = resourcesManager.getString(R.string.pref_show_hidden_title),
            subTitle = resourcesManager.getString(R.string.pref_show_hidden_summary),
            dataStoreManager.getBooleanBlocking(PreferenceKeys.SHOW_HIDDEN_CONTENTS) ?: false
          ),
      )
    )
  }

  fun getAutoFillSettingsState(): AutoFillSettingsState {
    val isAutoFillEnabled = dataStoreManager.getBooleanBlocking(PreferenceKeys.AUTOFILL_ENABLE)

    return AutoFillSettingsState(
      mapOf(PreferenceKeys.AUTOFILL_ENABLE to
        AutoFillSettingsSwitchWidget(
          title = resourcesManager.getString(R.string.pref_autofill_enable_title),
          isEnabled = isAutoFillEnabled ?: false
        ),
        PreferenceKeys.OREO_AUTOFILL_DIRECTORY_STRUCTURE to
          AutoFillSettingsTextWithSubtitle(
            title = resourcesManager.getString(R.string.oreo_autofill_preference_directory_structure),
            subtitle = dataStoreManager.getStringBlocking(PreferenceKeys.OREO_AUTOFILL_DIRECTORY_STRUCTURE) ?: ""
          )
      )
    )
  }
}