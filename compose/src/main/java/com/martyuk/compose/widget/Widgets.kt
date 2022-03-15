package com.martyuk.compose.widget

import android.annotation.SuppressLint
import android.os.Parcelable
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LayoutModifier
import androidx.compose.ui.unit.dp
import com.martyuk.compose.R
import com.martyuk.compose.event.AutoFillSettingsUiEvent
import com.martyuk.compose.screen.LabelledCheckbox
import com.martyuk.compose.screen.Screen
import com.martyuk.compose.screen.TextWithSwitch
import com.martyuk.utils.extensions.PreferenceKeys
import com.martyuk.utils.extensions.autofillManager
import com.martyuk.utils.extensions.isAutofillServiceEnabled
import kotlinx.parcelize.Parcelize

@Parcelize
data class TextWithSubtitleWidget(
  val title: String,
  val subtitle: String
) : WidgetItem, Parcelable {
  @SuppressLint("ComposableNaming")
  @Composable
  override fun draw(modifier: Modifier, changeStateInDataStore: (Any) -> Unit) {
    //
  }
}

@Parcelize
data class CheckboxWithSubtitleWidget(
  val title: String,
  val subTitle: String,
  val isSelected: Boolean
) : WidgetItem, Parcelable {

  @SuppressLint("ComposableNaming")
  @Composable
  override fun draw(modifier: Modifier, changeStateInDataStore: (Any) -> Unit) {
    LabelledCheckbox(
      title = title,
      subtitle = subTitle,
      isSelected = isSelected,
      modifier = modifier, changeStateInDataStore = changeStateInDataStore)
  }
}

@Parcelize
data class TextWithSwitchWidget(
  val title: String,
  val isEnabled: Boolean
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

  @SuppressLint("ComposableNaming")
  @Composable
  fun draw(modifier: Modifier, changeStateInDataStore: (Any) -> Unit)
}
