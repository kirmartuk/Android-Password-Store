package com.martyuk.compose.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Switch
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.martyuk.compose.R
import com.martyuk.compose.event.AutoFillSettingsUiEvent
import com.martyuk.compose.state.AutoFillSettingsState
import com.martyuk.compose.state.AutoFillSettingsSwitchWidget
import com.martyuk.compose.state.AutoFillSettingsTextWithSubtitle
import com.martyuk.compose.ui.theme.APSTheme
import com.martyuk.compose.ui.theme.Title
import com.martyuk.compose.utils.TextWithSubtitle
import com.martyuk.compose.viewmodel.AutoFillSettingsViewModel
import com.martyuk.utils.extensions.PreferenceKeys
import com.martyuk.utils.extensions.autofillManager
import com.martyuk.utils.extensions.isAutofillServiceEnabled

@Composable
fun AutoFillSettings(
  navController: NavController,
  autoFillSettingsViewModel: AutoFillSettingsViewModel = hiltViewModel(),
) {
  APSTheme {
    Scaffold(
      topBar = {
        SmallTopAppBar(
          title = {
            Text(text = stringResource(id = R.string.pref_category_autofill_title))
          },
          navigationIcon = {
            Icon(imageVector = Icons.Filled.ArrowBack,
              contentDescription = "",
              modifier = Modifier.clickable { navController.popBackStack() })
          }
        )
      }
    ) {
      val autoFillSettingsState by autoFillSettingsViewModel.state.collectAsState(initial = AutoFillSettingsState.initial())
      autoFillSettingsViewModel.showData()
      navController.addOnDestinationChangedListener { controller, destination, arguments ->
        if (destination.route == Screen.AutoFillSettings.name) {
          autoFillSettingsViewModel.
          arguments.get
        }
      }
      Surface {
        Column(modifier = Modifier
          .fillMaxSize()
        ) {
          DrawAutoFillSettingsWidget(
            autoFillSettingsState,
            PreferenceKeys.AUTOFILL_ENABLE,
            navController
          )
          DrawAutoFillSettingsWidget(
            autoFillSettingsState = autoFillSettingsState,
            preferenceKey = PreferenceKeys.OREO_AUTOFILL_DIRECTORY_STRUCTURE,
            navController = navController)
        }
      }
    }
  }
}

@Composable
fun DrawAutoFillSettingsWidget(autoFillSettingsState: AutoFillSettingsState, preferenceKey: String, navController: NavController, autoFillSettingsViewModel: AutoFillSettingsViewModel = hiltViewModel()) {
  val context = LocalContext.current
  when (preferenceKey) {
    PreferenceKeys.AUTOFILL_ENABLE -> {
      (autoFillSettingsState.data[preferenceKey] as AutoFillSettingsSwitchWidget?)?.let { state ->
        TextWithSwitch(title = state.title,
          isChecked = state.isEnabled,
          Modifier.clickable {
            if (state.isEnabled) {
              context.autofillManager?.disableAutofillServices()
              autoFillSettingsViewModel.setBooleanToDataStore(preferenceKey, context.isAutofillServiceEnabled)
              autoFillSettingsViewModel.update(
                AutoFillSettingsUiEvent.Update(
                  PreferenceKeys.AUTOFILL_ENABLE,
                  AutoFillSettingsSwitchWidget(
                    context.getString(R.string.pref_autofill_enable_title),
                    context.isAutofillServiceEnabled)
                ))
            } else {
              navController.navigate(Screen.EnableAutoFillDialog.name)
            }
          })
      }
    }
    PreferenceKeys.OREO_AUTOFILL_DIRECTORY_STRUCTURE -> {
      (autoFillSettingsState.data[preferenceKey] as AutoFillSettingsTextWithSubtitle?)?.let { state ->
        TextWithSubtitle(
          title = state.title,
          subtitle = state.subtitle,
          modifier = Modifier
            .clickable {
              navController.navigate(Screen.SingleChoiceDialog.name + "/${preferenceKey}")
            }
            .fillMaxWidth()
            .padding(start = 40.dp, top = 11.dp, bottom = 11.dp)
        )
      }
    }
  }
}

@Composable
fun TextWithSwitch(title: String, isChecked: Boolean, modifier: Modifier) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .padding(start = 40.dp, top = 11.dp, bottom = 11.dp, end = 40.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
    Text(text = title, style = Title)
    Switch(checked = isChecked, onCheckedChange = {})
  }
}