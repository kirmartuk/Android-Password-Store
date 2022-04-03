package com.martyuk.compose.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.martyuk.compose.R
import com.martyuk.compose.event.AutoFillSettingsUiEvent
import com.martyuk.compose.reducer.UiEvent
import com.martyuk.compose.state.AutoFillSettingsState
import com.martyuk.compose.ui.theme.APSTheme
import com.martyuk.compose.utils.toPreferenceKey
import com.martyuk.compose.viewmodel.AutoFillSettingsViewModel
import com.martyuk.compose.widget.TextWithSwitchWidget
import com.martyuk.compose.widget.WidgetItem
import com.martyuk.compose.widget.WidgetsNames
import com.martyuk.utils.extensions.autofillManager
import com.martyuk.utils.extensions.getParcelableOrNull
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
      val autoFillSettingsState by autoFillSettingsViewModel.state.collectAsState(AutoFillSettingsState.initial())
      autoFillSettingsViewModel.showData()

      navController.addOnDestinationChangedListener { _, destination, arguments ->
        if (destination.route == Screen.AutoFillSettings.name) {
          val event = arguments?.getParcelableOrNull<AutoFillSettingsUiEvent>(UiEvent.name)
          event?.let {
            autoFillSettingsViewModel.sendEvent(it)
            arguments.remove(UiEvent.name)
          }
        }
      }

      Surface {
        Column(modifier = Modifier
          .fillMaxSize()
        ) {
          autoFillSettingsState.data.forEach {
            DrawAutoFillSettingsWidget(
              it,
              navController
            )
          }
        }
      }
    }
  }
}

@Composable
fun DrawAutoFillSettingsWidget(
  widgetItem: WidgetItem,
  navController: NavController,
  autoFillSettingsViewModel: AutoFillSettingsViewModel = hiltViewModel()) {
  val context = LocalContext.current
  when (widgetItem.widgetName) {
    WidgetsNames.AUTOFILL_SETTINGS_ENABLE_AUTOFILL -> {
      widgetItem as TextWithSwitchWidget
      widgetItem.draw(
        modifier = Modifier.clickable {
          if (widgetItem.isEnabled) {
            context.autofillManager?.disableAutofillServices()
            autoFillSettingsViewModel.setBooleanToDataStore(
              widgetItem.widgetName.toPreferenceKey(),
              context.isAutofillServiceEnabled)
            autoFillSettingsViewModel.sendEvent(
              AutoFillSettingsUiEvent.Update(
                widgetItem.copy(isEnabled = context.isAutofillServiceEnabled)
              ))
          } else {
            navController.navigate(Screen.EnableAutoFillDialog.name)
          }
        }) {}
    }
    WidgetsNames.AUTOFILL_SETTINGS_PASSWORD_FILE_ORGANISATION -> {
      widgetItem.draw(modifier = Modifier
        .clickable {
          navController.navigate(Screen.SingleChoiceDialog.name + "/${widgetItem.widgetName.toPreferenceKey()}")
        }
        .fillMaxWidth()
        .padding(start = 40.dp, top = 11.dp, bottom = 11.dp), changeStateInDataStore = {})
    }
    WidgetsNames.AUTOFILL_SETTINGS_CUSTOM_DOMAINS, WidgetsNames.AUTOFILL_SETTINGS_DEFAULT_USERNAME -> {
      widgetItem.draw(modifier = Modifier
        .clickable {
          navController.navigate(Screen.UserInputDialog.name + "/${widgetItem.widgetName.toPreferenceKey()}")
        }
        .fillMaxWidth()
        .padding(start = 40.dp, top = 11.dp, bottom = 11.dp), changeStateInDataStore = {})
    }
  }
}