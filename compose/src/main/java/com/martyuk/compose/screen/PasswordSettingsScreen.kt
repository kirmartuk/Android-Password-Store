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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.martyuk.compose.R
import com.martyuk.compose.event.PasswordSettingsUiEvent
import com.martyuk.compose.reducer.UiEvent
import com.martyuk.compose.state.PasswordSettingsState
import com.martyuk.compose.ui.theme.APSTheme
import com.martyuk.compose.utils.toPreferenceKey
import com.martyuk.compose.viewmodel.PasswordSettingsStateViewModel
import com.martyuk.compose.widget.WidgetItem
import com.martyuk.compose.widget.WidgetsNames
import com.martyuk.utils.extensions.getParcelableOrNull

@Composable
fun PasswordSettings(
  navController: NavController,
  passwordSettingsStateViewModel: PasswordSettingsStateViewModel = hiltViewModel(),
) {
  APSTheme {
    Scaffold(
      topBar = {
        SmallTopAppBar(
          title = {
            Text(text = stringResource(id = R.string.pref_category_passwords_title))
          },
          navigationIcon = {
            Icon(imageVector = Icons.Filled.ArrowBack,
              contentDescription = "",
              modifier = Modifier.clickable { navController.popBackStack() })
          }
        )
      }
    ) {
      val passwordSettingsState by passwordSettingsStateViewModel.state.collectAsState(initial = PasswordSettingsState.initial())
      passwordSettingsStateViewModel.showData()

      navController.addOnDestinationChangedListener { _, destination, arguments ->
        if (destination.route == Screen.PasswordSettings.name) {
          val event = arguments?.getParcelableOrNull<PasswordSettingsUiEvent>(UiEvent.name)
          event?.let {
            passwordSettingsStateViewModel.sendEvent(it)
            arguments.remove(UiEvent.name)
          }
        }
      }

      Surface {
        Column(modifier = Modifier
          .fillMaxSize()
        ) {
          passwordSettingsState.data.forEach {
            DrawPasswordSettingsScreenWidget(
              widgetItem = it,
              navController = navController)
          }
        }
      }
    }
  }
}

@Composable
fun DrawPasswordSettingsScreenWidget(
  widgetItem: WidgetItem,
  navController: NavController,
  passwordSettingsStateViewModel: PasswordSettingsStateViewModel = hiltViewModel()
) {
  when (widgetItem.widgetName) {
    WidgetsNames.PASSWORD_SETTINGS_PASSWORD_GENERATOR_TYPE -> {
      widgetItem.draw(modifier = Modifier
        .clickable {
          navController.navigate(Screen.SingleChoiceDialog.name + "/${widgetItem.widgetName.toPreferenceKey()}")
        }
        .fillMaxWidth()
        .padding(start = 40.dp, top = 11.dp, bottom = 11.dp), changeStateInDataStore = {})
    }
    WidgetsNames.PASSWORD_SETTINGS_COPY_TIMEOUT -> {
      widgetItem.draw(modifier = Modifier
        .clickable {
          navController.navigate(Screen.UserInputDialog.name + "/${widgetItem.widgetName.toPreferenceKey()}")
        }
        .fillMaxWidth()
        .padding(start = 40.dp, top = 11.dp, bottom = 11.dp), changeStateInDataStore = {})
    }
    WidgetsNames.PASSWORD_SETTINGS_COPY_ON_DECRYPT, WidgetsNames.PASSWORD_SETTINGS_SHOW_PASSWORD -> {
      widgetItem.draw(modifier = Modifier.padding(start = 40.dp, top = 11.dp, bottom = 11.dp, end = 40.dp)) {
        passwordSettingsStateViewModel.setBooleanToDataStore(widgetItem.widgetName.toPreferenceKey(), it as Boolean)
      }
    }
  }
}