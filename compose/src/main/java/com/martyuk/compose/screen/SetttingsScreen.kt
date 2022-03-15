package com.martyuk.compose.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.martyuk.compose.R
import com.martyuk.compose.ui.theme.APSTheme

@Composable
fun Settings(navController: NavController) {
  APSTheme {
    Scaffold(
      topBar = {
        SmallTopAppBar(
          title = {
            Text(text = stringResource(id = R.string.action_settings))
          },
          navigationIcon = {
            Icon(imageVector = Icons.Filled.ArrowBack,
              contentDescription = "",
              modifier = Modifier.clickable { navController.popBackStack() })
          }
        )
      }
    ) {
      Surface {
        Column(modifier = Modifier.fillMaxSize()) {
          SubScreen(title = stringResource(id = R.string.pref_category_general_title),
            icon = painterResource(id = R.drawable.app_settings_alt_24px),
            navController,
            Screen.GeneralSettings)
          SubScreen(title = stringResource(id = R.string.pref_category_autofill_title),
            icon = painterResource(id = R.drawable.ic_wysiwyg_24px),
            navController,
            Screen.AutoFillSettings)
          SubScreen(title = stringResource(id = R.string.pref_category_passwords_title),
            icon = painterResource(id = R.drawable.ic_lock_open_24px),
            navController,
            Screen.PasswordSettings)
          SubScreen(title = stringResource(id = R.string.pref_category_repository_title),
            icon = painterResource(id = R.drawable.ic_call_merge_24px),
            navController,
            Screen.RepositorySettings)
          SubScreen(
            title = stringResource(id = R.string.pref_category_misc_title),
            icon = painterResource(id = R.drawable.ic_miscellaneous_services_24px),
            navController,
            Screen.GeneralSettings)
        }
      }
    }
  }
}

@Composable
fun SubScreen(title: String, icon: Painter, navController: NavController, targetNavigation: Screen) {
  Row(modifier = Modifier
    .clickable { navController.navigate(targetNavigation.name) }
    .fillMaxWidth()
    .padding(top = 15.dp, bottom = 15.dp, start = 15.dp)) {
    Icon(painter = icon, contentDescription = null, modifier = Modifier.padding(end = 20.dp))
    Text(text = title)
  }
}