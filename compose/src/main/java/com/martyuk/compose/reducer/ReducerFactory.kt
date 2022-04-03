package com.martyuk.compose.reducer

import com.martyuk.utils.extensions.PreferenceKeys
import dagger.Lazy
import javax.inject.Inject
import javax.inject.Singleton

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
}