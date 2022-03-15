package com.martyuk.compose.repository

import com.martyuk.compose.R
import com.martyuk.compose.state.AutoFillSettingsState
import com.martyuk.compose.state.GeneralSettingsState
import com.martyuk.compose.state.PasswordSettingsState
import com.martyuk.compose.state.RepositorySettingsState
import com.martyuk.compose.utils.DataStoreManager
import com.martyuk.compose.utils.ResourcesManager
import com.martyuk.compose.widget.CheckboxWithSubtitleWidget
import com.martyuk.compose.widget.TextWithSubtitleWidget
import com.martyuk.compose.widget.TextWithSwitchWidget
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
          TextWithSubtitleWidget(
            title = resourcesManager.getString(R.string.pref_app_theme_title),
            subtitle = appThemeSubTitle ?: resourcesManager.getString(R.string.pref_app_theme_value_light)),
        PreferenceKeys.SORT_ORDER to
          TextWithSubtitleWidget(
            title = resourcesManager.getString(R.string.pref_sort_order_title),
            subtitle = prefSortOrderSubTitle ?: resourcesManager.getString(R.string.pref_file_first_sort_order)
          ),
        PreferenceKeys.FILTER_RECURSIVELY to
          CheckboxWithSubtitleWidget(
            title = resourcesManager.getString(R.string.pref_recursive_filter_title),
            subTitle = resourcesManager.getString(R.string.pref_recursive_filter_summary),
            dataStoreManager.getBooleanBlocking(PreferenceKeys.FILTER_RECURSIVELY) ?: true
          ),
        PreferenceKeys.SEARCH_ON_START to
          CheckboxWithSubtitleWidget(
            title = resourcesManager.getString(R.string.pref_search_on_start_title),
            subTitle = resourcesManager.getString(R.string.pref_search_on_start_summary),
            dataStoreManager.getBooleanBlocking(PreferenceKeys.SEARCH_ON_START) ?: false
          ),
        PreferenceKeys.SHOW_HIDDEN_CONTENTS to
          CheckboxWithSubtitleWidget(
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
      mapOf(
        PreferenceKeys.AUTOFILL_ENABLE to
          TextWithSwitchWidget(
            title = resourcesManager.getString(R.string.pref_autofill_enable_title),
            isEnabled = isAutoFillEnabled ?: false
          ),
        PreferenceKeys.OREO_AUTOFILL_DIRECTORY_STRUCTURE to
          TextWithSubtitleWidget(
            title = resourcesManager.getString(R.string.oreo_autofill_preference_directory_structure),
            subtitle = dataStoreManager.getStringBlocking(PreferenceKeys.OREO_AUTOFILL_DIRECTORY_STRUCTURE)
              ?: resourcesManager.getString(R.string.pref_folder_first_sort_order)
          ),
        PreferenceKeys.OREO_AUTOFILL_DEFAULT_USERNAME to
          TextWithSubtitleWidget(
            title = resourcesManager.getString(R.string.preference_default_username_title),
            subtitle = dataStoreManager.getStringBlocking(PreferenceKeys.OREO_AUTOFILL_DEFAULT_USERNAME) ?: ""
          ),
        PreferenceKeys.OREO_AUTOFILL_CUSTOM_PUBLIC_SUFFIXES to
          TextWithSubtitleWidget(
            title = resourcesManager.getString(R.string.preference_custom_public_suffixes_title),
            subtitle = dataStoreManager.getStringBlocking(PreferenceKeys.OREO_AUTOFILL_CUSTOM_PUBLIC_SUFFIXES) ?: ""
          )
      )
    )
  }

  fun getPasswordSettingsState(): PasswordSettingsState {
    return PasswordSettingsState(
      mapOf(
        PreferenceKeys.PREF_KEY_PWGEN_TYPE to
          TextWithSubtitleWidget(
            title = resourcesManager.getString(R.string.pref_password_generator_type_title),
            subtitle = dataStoreManager.getStringBlocking(PreferenceKeys.PREF_KEY_PWGEN_TYPE)
              ?: resourcesManager.getStringArray(R.array.pwgen_provider_labels)[0]
          ),
        PreferenceKeys.GENERAL_SHOW_TIME to
          TextWithSubtitleWidget(
            title = resourcesManager.getString(R.string.pref_clipboard_timeout_title),
            subtitle = (dataStoreManager.getStringBlocking(PreferenceKeys.GENERAL_SHOW_TIME)?.toIntOrNull()
              ?: 45).toString()
          ),
        PreferenceKeys.SHOW_PASSWORD to
          CheckboxWithSubtitleWidget(
            title = resourcesManager.getString(R.string.show_password_pref_title),
            subTitle = resourcesManager.getString(R.string.show_password_pref_summary),
            isSelected = dataStoreManager.getBooleanBlocking(PreferenceKeys.SHOW_PASSWORD) ?: true
          ),
        PreferenceKeys.COPY_ON_DECRYPT to
          CheckboxWithSubtitleWidget(
            title = resourcesManager.getString(R.string.pref_copy_title),
            subTitle = resourcesManager.getString(R.string.pref_copy_summary),
            isSelected = dataStoreManager.getBooleanBlocking(PreferenceKeys.COPY_ON_DECRYPT) ?: false
          )
      )
    )
  }

  fun getRepositorySettingsState(): RepositorySettingsState {
    return RepositorySettingsState(
      mapOf(
        PreferenceKeys.REBASE_ON_PULL to
          CheckboxWithSubtitleWidget(
            title = resourcesManager.getString(R.string.pref_rebase_on_pull_title),
            subTitle = resourcesManager.getString(R.string.pref_rebase_on_pull_summary),
            isSelected = dataStoreManager.getBooleanBlocking(PreferenceKeys.REBASE_ON_PULL) ?: true
          )
      )
    )
  }

  companion object {

    const val GENERAL_SHOW_TIME_DEFAULT_VALUE = 45
  }
}