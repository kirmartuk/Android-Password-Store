package com.martyuk.compose.reducer

import com.martyuk.compose.event.AutoFillSettingsUiEvent
import com.martyuk.compose.event.GeneralSettingsUiEvent
import com.martyuk.compose.state.AutoFillSettingsTextWithSubtitle
import com.martyuk.compose.state.GeneralSettingsSingleChoiceWidget
import com.martyuk.compose.viewmodel.Reducer
import com.martyuk.utils.extensions.PreferenceKeys
import javax.inject.Inject
import javax.inject.Singleton
import dagger.Lazy
import kotlin.reflect.KClass

@Singleton
class ReducerFactory @Inject constructor(
  private val autoFillSettingsReducer: Lazy<AutoFillSettingsReducer>,
  private val generalSettingsReducer: Lazy<GeneralSettingsReducer>
) {

  fun getReducerByKey(key: String): Reducer<*, *> {
    return when (key) {
      PreferenceKeys.OREO_AUTOFILL_DIRECTORY_STRUCTURE,
      PreferenceKeys.SORT_ORDER, PreferenceKeys.AUTOFILL_ENABLE -> autoFillSettingsReducer.get()
      PreferenceKeys.APP_THEME -> generalSettingsReducer.get()
      else -> throw Exception("")
    }
  }

  fun updateReducerState(key: String, title: String, subtitle: String) {
    when (key) {
      PreferenceKeys.OREO_AUTOFILL_DIRECTORY_STRUCTURE -> {
        autoFillSettingsReducer.get().sendEvent(
          AutoFillSettingsUiEvent.Update(key,
            AutoFillSettingsTextWithSubtitle(title, subtitle)
          )
        )
      }
      PreferenceKeys.APP_THEME, PreferenceKeys.SORT_ORDER -> {
        generalSettingsReducer.get().sendEvent(
          GeneralSettingsUiEvent.Update(key, GeneralSettingsSingleChoiceWidget(title, subtitle))
        )
      }
    }
  }
}