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
import com.martyuk.compose.event.GeneralSettingsUiEvent
import com.martyuk.compose.reducer.UiEvent
import com.martyuk.compose.state.GeneralSettingsState
import com.martyuk.compose.ui.theme.APSTheme
import com.martyuk.compose.utils.toPreferenceKey
import com.martyuk.compose.viewmodel.GeneralSettingsStateViewModel
import com.martyuk.compose.widget.WidgetItem
import com.martyuk.compose.widget.WidgetsNames
import com.martyuk.utils.extensions.getParcelableOrNull

@Composable
fun GeneralSettings(
  navController: NavController,
  generalSettingsStateViewModel: GeneralSettingsStateViewModel = hiltViewModel(),
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
      val generalSettingsState by generalSettingsStateViewModel.state.collectAsState(initial = GeneralSettingsState.initial())
      generalSettingsStateViewModel.showData()

      navController.addOnDestinationChangedListener { _, destination, arguments ->
        if (destination.route == Screen.GeneralSettings.name) {
          val event = arguments?.getParcelableOrNull<GeneralSettingsUiEvent>(UiEvent.name)
          event?.let {
            generalSettingsStateViewModel.sendEvent(it)
            arguments.remove(UiEvent.name)
          }
        }
      }

      Surface {
        Column(modifier = Modifier
          .fillMaxSize()
        ) {
          generalSettingsState.data.forEach {
            DrawGeneralSettingsScreenWidget(
              widgetItem = it,
              navController = navController)
          }
        }
      }
    }
  }
}

@Composable
private fun DrawGeneralSettingsScreenWidget(
  widgetItem: WidgetItem,
  navController: NavController,
  generalSettingsStateViewModel: GeneralSettingsStateViewModel = hiltViewModel()
) {
  when (widgetItem.widgetName) {
    WidgetsNames.GENERAL_SETTINGS_APP_THEME, WidgetsNames.GENERAL_SETTINGS_SORT_ORDER -> {
      widgetItem.draw(
        modifier = Modifier
          .clickable {
            navController.navigate(Screen.SingleChoiceDialog.name + "/${widgetItem.widgetName.toPreferenceKey()}")
          }
          .fillMaxWidth()
          .padding(start = 40.dp, top = 11.dp, bottom = 11.dp), changeStateInDataStore = {})
    }
    WidgetsNames.GENERAL_SETTINGS_FILTER_RECURSIVELY, WidgetsNames.GENERAL_SETTINGS_SEARCH_ON_START -> {
      widgetItem.draw(modifier = Modifier.padding(start = 40.dp, top = 11.dp, bottom = 11.dp, end = 40.dp)) {
        generalSettingsStateViewModel.setBooleanToDataStore(widgetItem.widgetName.toPreferenceKey(), it as Boolean)
      }
    }
  }
}
