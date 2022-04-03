package com.martyuk.compose.widget

import android.annotation.SuppressLint
import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.martyuk.compose.utils.CheckboxWithTitleAndSubtitle
import com.martyuk.compose.utils.TextWithSubtitle
import com.martyuk.compose.utils.TextWithSwitch
import kotlinx.parcelize.Parcelize

@Parcelize
data class TextWithSubtitleWidget(
  val title: String,
  val subtitle: String, override val widgetName: String
) : WidgetItem, Parcelable {

  @SuppressLint("ComposableNaming")
  @Composable
  override fun draw(modifier: Modifier, changeStateInDataStore: (Any) -> Unit) {
    TextWithSubtitle(
      title = title,
      subtitle = subtitle,
      modifier = modifier
    )
  }
}

@Parcelize
data class CheckboxWithSubtitleWidget(
  val title: String,
  val subTitle: String,
  val isSelected: Boolean, override val widgetName: String
) : WidgetItem, Parcelable {

  @SuppressLint("ComposableNaming")
  @Composable
  override fun draw(modifier: Modifier, changeStateInDataStore: (Any) -> Unit) {
    CheckboxWithTitleAndSubtitle(
      title = title,
      subtitle = subTitle,
      isSelected = isSelected,
      modifier = modifier, stateClickListener = changeStateInDataStore)
  }
}

@Parcelize
data class TextWithSwitchWidget(
  val title: String,
  val isEnabled: Boolean, override val widgetName: String
) : WidgetItem, Parcelable {

  @Composable
  @SuppressLint("ComposableNaming")
  override fun draw(modifier: Modifier, changeStateInDataStore: (Any) -> Unit) {
    TextWithSwitch(title = title,
      isChecked = isEnabled,
      modifier = modifier)
  }
}

interface WidgetItem {

  val widgetName: String

  @SuppressLint("ComposableNaming")
  @Composable
  fun draw(modifier: Modifier, changeStateInDataStore: (Any) -> Unit)
}

object WidgetsNames {

  const val AUTOFILL_SETTINGS_ENABLE_AUTOFILL = "autoFillSettingsEnableAutoFill"
  const val AUTOFILL_SETTINGS_PASSWORD_FILE_ORGANISATION = "autoFillSettingsPasswordFileOrganisation"
  const val AUTOFILL_SETTINGS_DEFAULT_USERNAME = "autoFillSettingsUsername"
  const val AUTOFILL_SETTINGS_CUSTOM_DOMAINS = "autoFillSettingsCustomDomains"
  const val PASSWORD_SETTINGS_PASSWORD_GENERATOR_TYPE = "passwordSettingsPasswordGeneratorType"
  const val PASSWORD_SETTINGS_COPY_TIMEOUT = "passwordSettingsCopyTimeout"
  const val PASSWORD_SETTINGS_COPY_ON_DECRYPT = "passwordSettingsCopyOnDecrypt"
  const val GENERAL_SETTINGS_APP_THEME = "generalSettingsAppTheme"
  const val GENERAL_SETTINGS_SORT_ORDER = "generalSettingsSortOrder"
  const val GENERAL_SETTINGS_FILTER_RECURSIVELY = "generalSettingsFilterRecursively"
  const val GENERAL_SETTINGS_SEARCH_ON_START = "generalSettingsSearchOnStart"
  const val GENERAL_SETTINGS_SHOW_HIDDEN_CONTENTS = "generalSettingsShowHiddenContents"
  const val REPOSITORY_SETTINGS_REBASE_ON_PULL = "RepositorySettingsRebaseOnPull"
  const val REPOSITORY_SETTINGS_SSH_KEYGEN = "RepositorySettingsSshKeygen"
}
