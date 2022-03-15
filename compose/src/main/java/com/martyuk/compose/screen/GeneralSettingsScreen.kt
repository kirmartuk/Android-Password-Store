package com.martyuk.compose.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material.Checkbox
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
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
import com.martyuk.compose.ui.theme.SubTitle
import com.martyuk.compose.ui.theme.Title
import com.martyuk.compose.utils.TextWithSubtitle
import com.martyuk.compose.viewmodel.GeneralSettingsStateViewModel
import com.martyuk.compose.widget.CheckboxWithSubtitleWidget
import com.martyuk.compose.widget.TextWithSubtitleWidget
import com.martyuk.utils.extensions.PreferenceKeys
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
          getScreenItems(Screen.GeneralSettings).forEach {
            DrawGeneralSettingsScreenWidget(
              preferenceKey = it,
              generalSettingsState = generalSettingsState,
              navController = navController)
          }
        }
      }
    }
  }
}

@Composable
private fun DrawGeneralSettingsScreenWidget(
  preferenceKey: String,
  generalSettingsState: GeneralSettingsState,
  navController: NavController,
  generalSettingsStateViewModel: GeneralSettingsStateViewModel = hiltViewModel()
) {
  when (preferenceKey) {
    PreferenceKeys.APP_THEME, PreferenceKeys.SORT_ORDER -> {
      (generalSettingsState.data[preferenceKey] as TextWithSubtitleWidget?)?.let { state ->
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
    PreferenceKeys.FILTER_RECURSIVELY, PreferenceKeys.SEARCH_ON_START, PreferenceKeys.SHOW_HIDDEN_CONTENTS -> {
      (generalSettingsState.data[preferenceKey] as CheckboxWithSubtitleWidget?)?.let { state ->
        LabelledCheckbox(
          title = state.title,
          subtitle = state.subTitle,
          isSelected = state.isSelected,
          modifier = Modifier.padding(start = 40.dp, top = 11.dp, bottom = 11.dp, end = 40.dp)) {
          generalSettingsStateViewModel.setBooleanToDataStore(preferenceKey, it)
        }
      }
    }
  }
}

@Composable
fun LabelledCheckbox(
  title: String,
  subtitle: String,
  isSelected: Boolean,
  modifier: Modifier,
  changeStateInDataStore: (Boolean) -> Unit
) {
  val (isChecked, setCheckboxState) = remember { mutableStateOf(isSelected) }
  Column(Modifier
    .clickable {
      changeStateInDataStore(!isChecked)
      setCheckboxState(!isChecked)
    }
    .fillMaxWidth()) {
    Box(
      modifier = modifier
        .fillMaxWidth()
    ) {
      Column(Modifier.align(Alignment.CenterStart)) {
        Text(text = title, style = Title)
        Text(modifier = Modifier.requiredWidth(280.dp), text = subtitle, style = SubTitle)
      }
      Checkbox(
        modifier = Modifier
          .align(Alignment.CenterEnd),
        checked = isChecked,
        onCheckedChange = {
          changeStateInDataStore(it)
          setCheckboxState(it)
        }
      )
    }
  }
}
