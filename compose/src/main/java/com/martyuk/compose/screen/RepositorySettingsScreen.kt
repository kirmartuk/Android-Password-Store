package com.martyuk.compose.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import com.martyuk.compose.event.RepositorySettingsUiEvent
import com.martyuk.compose.reducer.UiEvent
import com.martyuk.compose.reducer.UiState
import com.martyuk.compose.state.PasswordSettingsState
import com.martyuk.compose.state.RepositorySettingsState
import com.martyuk.compose.ui.theme.APSTheme
import com.martyuk.compose.viewmodel.PasswordSettingsStateViewModel
import com.martyuk.compose.viewmodel.RepositorySettingsViewModel
import com.martyuk.compose.widget.CheckboxWithSubtitleWidget
import com.martyuk.utils.extensions.PreferenceKeys
import com.martyuk.utils.extensions.getParcelableOrNull

@Composable
fun RepositorySettingsScreen(
  navController: NavController,
  repositorySettingsViewModel: RepositorySettingsViewModel = hiltViewModel(),
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
      val repositorySettingsState by repositorySettingsViewModel.state.collectAsState(initial = RepositorySettingsState.initial())
      repositorySettingsViewModel.showData()

      navController.addOnDestinationChangedListener { _, destination, arguments ->
        if (destination.route == Screen.PasswordSettings.name) {
          val event = arguments?.getParcelableOrNull<RepositorySettingsUiEvent>(UiEvent.name)
          event?.let {
            repositorySettingsViewModel.sendEvent(it)
            arguments.remove(UiEvent.name)
          }
        }
      }

      Surface {
        Column(modifier = Modifier
          .fillMaxSize()
        ) {
          getScreenItems(Screen.RepositorySettings).forEach {
            DrawRepositorySettingsScreenWidget(
              preferenceKey = it,
              repositorySettingsState = repositorySettingsState,
              navController = navController)
          }
        }
      }
    }
  }
}

@Composable fun DrawRepositorySettingsScreenWidget(
  preferenceKey: String,
  repositorySettingsState: RepositorySettingsState,
  navController: NavController) {
  when (preferenceKey) {
    PreferenceKeys.REBASE_ON_PULL -> {
      (repositorySettingsState.data[preferenceKey] as CheckboxWithSubtitleWidget?)?.let {
        LabelledCheckbox(
          title = it.title,
          subtitle = it.subTitle,
          isSelected = it.isSelected,
          modifier = Modifier.padding(start = 40.dp, top = 11.dp, bottom = 11.dp, end = 40.dp)
        ) {
        }
      }
    }
  }
}
