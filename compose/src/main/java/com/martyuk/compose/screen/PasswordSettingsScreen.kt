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
import com.martyuk.compose.utils.TextWithSubtitle
import com.martyuk.compose.viewmodel.PasswordSettingsStateViewModel
import com.martyuk.compose.widget.CheckboxWithSubtitleWidget
import com.martyuk.compose.widget.TextWithSubtitleWidget
import com.martyuk.utils.extensions.PreferenceKeys
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
            Text(text = stringResource(id = R.string.pref_category_general_title))
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
          getScreenItems(Screen.PasswordSettings).forEach {
            DrawPasswordSettingsScreenWidget(
              preferenceKey = it,
              passwordSettingsState = passwordSettingsState,
              navController = navController)
          }
        }
      }
    }
  }
}

@Composable
fun DrawPasswordSettingsScreenWidget(
  preferenceKey: String,
  passwordSettingsState: PasswordSettingsState,
  navController: NavController,
  passwordSettingsStateViewModel: PasswordSettingsStateViewModel = hiltViewModel()
) {
  when (preferenceKey) {
    PreferenceKeys.PREF_KEY_PWGEN_TYPE -> {
      (passwordSettingsState.data[preferenceKey] as TextWithSubtitleWidget?)?.let { state ->
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
    PreferenceKeys.GENERAL_SHOW_TIME -> {
      (passwordSettingsState.data[preferenceKey] as TextWithSubtitleWidget?)?.let { state ->
        TextWithSubtitle(
          title = state.title,
          subtitle = state.subtitle,
          modifier = Modifier
            .clickable {
              navController.navigate(Screen.UserInputDialog.name + "/${preferenceKey}")
            }
            .fillMaxWidth()
            .padding(start = 40.dp, top = 11.dp, bottom = 11.dp)
        )
      }
    }
    PreferenceKeys.SHOW_PASSWORD, PreferenceKeys.COPY_ON_DECRYPT -> {
      (passwordSettingsState.data[preferenceKey] as CheckboxWithSubtitleWidget?)?.let { state ->
        state.draw(modifier = Modifier.padding(start = 40.dp, top = 11.dp, bottom = 11.dp, end = 40.dp)) {
          passwordSettingsStateViewModel.setBooleanToDataStore(preferenceKey, it as Boolean)
        }
      }
    }
  }
}