package com.martyuk.compose.repository

import com.martyuk.compose.R
import com.martyuk.compose.state.AutoFillSettingsState
import com.martyuk.compose.state.GeneralSettingsState
import com.martyuk.compose.state.PasswordSettingsState
import com.martyuk.compose.state.RepositorySettingsState
import com.martyuk.compose.widget.CheckboxWithSubtitleWidget
import com.martyuk.compose.widget.TextWithSubtitleWidget
import com.martyuk.compose.widget.TextWithSwitchWidget
import com.martyuk.compose.widget.WidgetsNames.AUTOFILL_SETTINGS_CUSTOM_DOMAINS
import com.martyuk.compose.widget.WidgetsNames.AUTOFILL_SETTINGS_DEFAULT_USERNAME
import com.martyuk.compose.widget.WidgetsNames.AUTOFILL_SETTINGS_ENABLE_AUTOFILL
import com.martyuk.compose.widget.WidgetsNames.AUTOFILL_SETTINGS_PASSWORD_FILE_ORGANISATION
import com.martyuk.compose.widget.WidgetsNames.GENERAL_SETTINGS_APP_THEME
import com.martyuk.compose.widget.WidgetsNames.GENERAL_SETTINGS_FILTER_RECURSIVELY
import com.martyuk.compose.widget.WidgetsNames.GENERAL_SETTINGS_SEARCH_ON_START
import com.martyuk.compose.widget.WidgetsNames.GENERAL_SETTINGS_SHOW_HIDDEN_CONTENTS
import com.martyuk.compose.widget.WidgetsNames.GENERAL_SETTINGS_SORT_ORDER
import com.martyuk.compose.widget.WidgetsNames.PASSWORD_SETTINGS_COPY_ON_DECRYPT
import com.martyuk.compose.widget.WidgetsNames.PASSWORD_SETTINGS_COPY_TIMEOUT
import com.martyuk.compose.widget.WidgetsNames.PASSWORD_SETTINGS_PASSWORD_GENERATOR_TYPE
import com.martyuk.compose.widget.WidgetsNames.REPOSITORY_SETTINGS_REBASE_ON_PULL
import com.martyuk.compose.widget.WidgetsNames.REPOSITORY_SETTINGS_SSH_KEYGEN
import com.martyuk.utils.extensions.DataStoreManager
import com.martyuk.utils.extensions.PreferenceKeys
import com.martyuk.utils.extensions.ResourcesManager
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
      data = listOf(
        TextWithSubtitleWidget(
          widgetName = GENERAL_SETTINGS_APP_THEME,
          title = resourcesManager.getString(R.string.pref_app_theme_title),
          subtitle = appThemeSubTitle ?: resourcesManager.getString(R.string.pref_app_theme_value_light)),
        TextWithSubtitleWidget(
          widgetName = GENERAL_SETTINGS_SORT_ORDER,
          title = resourcesManager.getString(R.string.pref_sort_order_title),
          subtitle = prefSortOrderSubTitle ?: resourcesManager.getString(R.string.pref_file_first_sort_order)
        ),
        CheckboxWithSubtitleWidget(
          widgetName = GENERAL_SETTINGS_FILTER_RECURSIVELY,
          title = resourcesManager.getString(R.string.pref_recursive_filter_title),
          subTitle = resourcesManager.getString(R.string.pref_recursive_filter_summary),
          isSelected = dataStoreManager.getBooleanBlocking(PreferenceKeys.FILTER_RECURSIVELY) ?: true
        ),
        CheckboxWithSubtitleWidget(
          widgetName = GENERAL_SETTINGS_SEARCH_ON_START,
          title = resourcesManager.getString(R.string.pref_search_on_start_title),
          subTitle = resourcesManager.getString(R.string.pref_search_on_start_summary),
          isSelected = dataStoreManager.getBooleanBlocking(PreferenceKeys.SEARCH_ON_START) ?: false
        ),
        CheckboxWithSubtitleWidget(
          widgetName = GENERAL_SETTINGS_SHOW_HIDDEN_CONTENTS,
          title = resourcesManager.getString(R.string.pref_show_hidden_title),
          subTitle = resourcesManager.getString(R.string.pref_show_hidden_summary),
          isSelected = dataStoreManager.getBooleanBlocking(PreferenceKeys.SHOW_HIDDEN_CONTENTS) ?: false
        ),
      )
    )
  }

  fun getAutoFillSettingsState(): AutoFillSettingsState {
    val isAutoFillEnabled = dataStoreManager.getBooleanBlocking(PreferenceKeys.AUTOFILL_ENABLE)

    return AutoFillSettingsState(
      listOf(
        TextWithSwitchWidget(
          widgetName = AUTOFILL_SETTINGS_ENABLE_AUTOFILL,
          title = resourcesManager.getString(R.string.pref_autofill_enable_title),
          isEnabled = isAutoFillEnabled ?: false
        ),
        TextWithSubtitleWidget(
          widgetName = AUTOFILL_SETTINGS_PASSWORD_FILE_ORGANISATION,
          title = resourcesManager.getString(R.string.oreo_autofill_preference_directory_structure),
          subtitle = dataStoreManager.getStringBlocking(PreferenceKeys.OREO_AUTOFILL_DIRECTORY_STRUCTURE)
            ?: resourcesManager.getString(R.string.pref_folder_first_sort_order)
        ),
        TextWithSubtitleWidget(
          widgetName = AUTOFILL_SETTINGS_DEFAULT_USERNAME,
          title = resourcesManager.getString(R.string.preference_default_username_title),
          subtitle = dataStoreManager.getStringBlocking(PreferenceKeys.OREO_AUTOFILL_DEFAULT_USERNAME) ?: ""
        ),
        TextWithSubtitleWidget(
          widgetName = AUTOFILL_SETTINGS_CUSTOM_DOMAINS,
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
            widgetName = PASSWORD_SETTINGS_PASSWORD_GENERATOR_TYPE,
            title = resourcesManager.getString(R.string.pref_password_generator_type_title),
            subtitle = dataStoreManager.getStringBlocking(PreferenceKeys.PREF_KEY_PWGEN_TYPE)
              ?: resourcesManager.getStringArray(R.array.pwgen_provider_labels)[0]
          ),
        PreferenceKeys.GENERAL_SHOW_TIME to
          TextWithSubtitleWidget(
            widgetName = PASSWORD_SETTINGS_COPY_TIMEOUT,
            title = resourcesManager.getString(R.string.pref_clipboard_timeout_title),
            subtitle = (dataStoreManager.getStringBlocking(PreferenceKeys.GENERAL_SHOW_TIME)?.toIntOrNull()
              ?: 45).toString()
          ),
        PreferenceKeys.SHOW_PASSWORD to
          CheckboxWithSubtitleWidget(
            widgetName = PASSWORD_SETTINGS_COPY_TIMEOUT,
            title = resourcesManager.getString(R.string.show_password_pref_title),
            subTitle = resourcesManager.getString(R.string.show_password_pref_summary),
            isSelected = dataStoreManager.getBooleanBlocking(PreferenceKeys.SHOW_PASSWORD) ?: true
          ),
        PreferenceKeys.COPY_ON_DECRYPT to
          CheckboxWithSubtitleWidget(
            widgetName = PASSWORD_SETTINGS_COPY_ON_DECRYPT,
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
            widgetName = REPOSITORY_SETTINGS_REBASE_ON_PULL,
            title = resourcesManager.getString(R.string.pref_rebase_on_pull_title),
            subTitle = resourcesManager.getString(R.string.pref_rebase_on_pull_summary),
            isSelected = dataStoreManager.getBooleanBlocking(PreferenceKeys.REBASE_ON_PULL) ?: true
          ),
        PreferenceKeys.SSH_KEYGEN to
          TextWithSubtitleWidget(
            widgetName = REPOSITORY_SETTINGS_SSH_KEYGEN,
            title = resourcesManager.getString(R.string.pref_ssh_keygen_title),
            subtitle = ""
          )
      )
    )
  }

  companion object {

    const val GENERAL_SHOW_TIME_DEFAULT_VALUE = 45
  }
}