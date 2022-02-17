package com.martyuk.compose

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavArgument
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.github.androidpasswordstore.autofillparser.BrowserAutofillSupportLevel
import com.github.androidpasswordstore.autofillparser.getInstalledBrowsersWithAutofillSupportLevel
import com.martyuk.compose.event.AutoFillSettingsUiEvent
import com.martyuk.compose.event.GeneralSettingsUiEvent
import com.martyuk.compose.reducer.AutoFillSettingsReducer
import com.martyuk.compose.reducer.ReducerFactory
import com.martyuk.compose.reducer.UpdateAllState
import com.martyuk.compose.screen.AutoFillSettings
import com.martyuk.compose.screen.GeneralSettings
import com.martyuk.compose.screen.Screen
import com.martyuk.compose.state.AutoFillSettingsSwitchWidget
import com.martyuk.compose.state.GeneralSettingsItem
import com.martyuk.compose.state.GeneralSettingsSingleChoiceWidget
import com.martyuk.compose.ui.theme.APSTheme
import com.martyuk.compose.utils.DataStoreManager
import com.martyuk.compose.utils.ResourcesManager
import com.martyuk.compose.utils.SingleChoiceDialog
import com.martyuk.compose.utils.UserInputDialog
import com.martyuk.compose.viewmodel.Reducer
import com.martyuk.compose.viewmodel.UiEvent
import com.martyuk.compose.viewmodel.UiState
import com.martyuk.compose.vo.UserInputDialogVo
import com.martyuk.utils.extensions.PreferenceKeys
import com.martyuk.utils.extensions.isAutofillServiceEnabled
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  @Inject
  lateinit var dataStoreManager: DataStoreManager

  @Inject
  lateinit var resourcesManager: ResourcesManager

  @Inject
  lateinit var reducerFactory: ReducerFactory

  @Inject
  lateinit var singleChoiceDialogFormatter: SingleChoiceDialogFormatter
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      val coroutineScope = rememberCoroutineScope()
      APSTheme {
        val navController = rememberNavController()
        Surface(color = MaterialTheme.colors.background) {
          NavHost(navController, startDestination = Screen.Welcome.name) {
            composable(Screen.Settings.name) { Settings(navController) }
            composable(Screen.Welcome.name) { Welcome(navController) }
            composable(Screen.GeneralSettings.name) {
              GeneralSettings(navController)
            }
            composable(Screen.AutoFillSettings.name) {
              AutoFillSettings(navController)
            }
            dialog(Screen.EnableAutoFillDialog.name) {
              AlertDialog(
                title = { Text(text = stringResource(id = R.string.pref_autofill_enable_title)) },
                text = {
                  Column {
                    Text(text = stringResource(id = R.string.oreo_autofill_enable_dialog_description))
                    Text(modifier = Modifier.padding(top = 16.dp), text = stringResource(id = R.string.oreo_autofill_enable_dialog_instructions))
                    Text(modifier = Modifier.padding(top = 16.dp), text = stringResource(id = R.string.oreo_autofill_enable_dialog_installed_browsers), fontWeight = FontWeight.Bold)
                    Text(text = getInstalledBrowsersWithAutofillSupportLevel(applicationContext).joinToString(separator = System.lineSeparator()) {
                      val appLabel = it.first
                      val supportDescription =
                        when (it.second) {
                          BrowserAutofillSupportLevel.None ->
                            resourcesManager.getString(R.string.oreo_autofill_no_support)
                          BrowserAutofillSupportLevel.FlakyFill ->
                            resourcesManager.getString(R.string.oreo_autofill_flaky_fill_support)
                          BrowserAutofillSupportLevel.PasswordFill ->
                            resourcesManager.getString(R.string.oreo_autofill_password_fill_support)
                          BrowserAutofillSupportLevel.PasswordFillAndSaveIfNoAccessibility ->
                            resourcesManager.getString(
                              R.string.oreo_autofill_password_fill_and_conditional_save_support
                            )
                          BrowserAutofillSupportLevel.GeneralFill ->
                            resourcesManager.getString(R.string.oreo_autofill_general_fill_support)
                          BrowserAutofillSupportLevel.GeneralFillAndSave ->
                            resourcesManager.getString(R.string.oreo_autofill_general_fill_and_save_support)
                        }
                      "$appLabel: $supportDescription"
                    })
                  }
                },
                dismissButton = {
                  TextButton(onClick = {
                    navController.popBackStack()
                  }) {
                    Text(text = stringResource(id = R.string.dialog_cancel))
                  }
                },
                confirmButton = {
                  TextButton(onClick = {
                    val intent =
                      Intent(android.provider.Settings.ACTION_REQUEST_SET_AUTOFILL_SERVICE).apply {
                        data = Uri.parse("package:${applicationContext.packageName}")
                      }
                    startActivity(intent)
                    navController.popBackStack()
                    val observer = LifecycleEventObserver { _, event ->
                      when (event) {
                        Lifecycle.Event.ON_RESUME -> {
                          coroutineScope.launch(Dispatchers.IO) {
                            val autoFillSettingsReducer: AutoFillSettingsReducer =
                              reducerFactory.getReducerByKey(PreferenceKeys.AUTOFILL_ENABLE) as AutoFillSettingsReducer
                            val item: AutoFillSettingsSwitchWidget =
                              (autoFillSettingsReducer.state.value.data[PreferenceKeys.AUTOFILL_ENABLE] as AutoFillSettingsSwitchWidget).copy(isEnabled = applicationContext.isAutofillServiceEnabled)
                            dataStoreManager.setBoolean(PreferenceKeys.AUTOFILL_ENABLE, applicationContext.isAutofillServiceEnabled)
                            autoFillSettingsReducer.sendEvent(
                              AutoFillSettingsUiEvent.Update(
                                PreferenceKeys.AUTOFILL_ENABLE,
                                item
                              )
                            )
                          }
                        }
                        else -> {
                        }
                      }
                    }
                    lifecycle.addObserver(observer)
                  }) {
                    Text(text = stringResource(id = R.string.dialog_ok))
                  }
                },
                onDismissRequest = {
                  navController.popBackStack()
                }
              )
            }
            dialog(Screen.SingleChoiceDialog.name + "/{id}") { backStackEntry ->
              val dialogType = backStackEntry.arguments?.getString("id")!!
              val vo = singleChoiceDialogFormatter.format(dialogType)
              SingleChoiceDialog(
                navController,
                vo
              ) {
                coroutineScope.launch(Dispatchers.IO) {
                  dataStoreManager.setString(dialogType, it)
                  runOnUiThread {
                    navArgument("ads")
                    navController.currentDestination.addArgument("", NavArgument())
                    reducerFactory.updateReducerState(dialogType, vo.title, it)
                  }
                }
              }
            }
            dialog(Screen.UserInputDialog.name){
              UserInputDialog(
                navController,
                UserInputDialogVo("add")
              ){

              }
            }
          }
        }
      }
    }
  }
}
