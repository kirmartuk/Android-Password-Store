package com.martyuk.compose.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.martyuk.compose.widget.WidgetsNames
import com.martyuk.compose.widget.WidgetsNames.GENERAL_SETTINGS_SHOW_HIDDEN_CONTENTS
import com.martyuk.utils.extensions.PreferenceKeys

fun Drawable.asImageBitmap(): ImageBitmap {
  val bitmap = Bitmap.createBitmap(
    this.intrinsicWidth, this.intrinsicHeight, Bitmap.Config.ARGB_8888
  )
  val canvas = Canvas(bitmap)
  this.setBounds(0, 0, canvas.width, canvas.height)
  this.draw(canvas)
  return bitmap.asImageBitmap()
}

fun <T> List<T>.indexOfOrNull(element: T): Int? {
  if (element == null) {
    return null
  }
  val index = this.indexOf(element)
  return if (index == -1) {
    null
  } else {
    index
  }
}

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
fun String.toPreferenceKey(): String {
  return when (this) {
    GENERAL_SETTINGS_SHOW_HIDDEN_CONTENTS -> {
      PreferenceKeys.SHOW_HIDDEN_CONTENTS
    }
    WidgetsNames.GENERAL_SETTINGS_APP_THEME -> {
      PreferenceKeys.APP_THEME
    }
    WidgetsNames.GENERAL_SETTINGS_SORT_ORDER -> {
      PreferenceKeys.SORT_ORDER
    }
    WidgetsNames.GENERAL_SETTINGS_FILTER_RECURSIVELY -> {
      PreferenceKeys.FILTER_RECURSIVELY
    }
    WidgetsNames.GENERAL_SETTINGS_SEARCH_ON_START -> {
      PreferenceKeys.SEARCH_ON_START
    }
    WidgetsNames.AUTOFILL_SETTINGS_PASSWORD_FILE_ORGANISATION -> {
      PreferenceKeys.OREO_AUTOFILL_DIRECTORY_STRUCTURE
    }
    WidgetsNames.AUTOFILL_SETTINGS_DEFAULT_USERNAME -> {
      PreferenceKeys.OREO_AUTOFILL_DEFAULT_USERNAME
    }
    WidgetsNames.AUTOFILL_SETTINGS_CUSTOM_DOMAINS -> {
      PreferenceKeys.OREO_AUTOFILL_CUSTOM_PUBLIC_SUFFIXES
    }
    WidgetsNames.PASSWORD_SETTINGS_PASSWORD_GENERATOR_TYPE -> {
      PreferenceKeys.PREF_KEY_PWGEN_TYPE
    }
    else -> {
      throw Exception("No preference key for $this widget")
    }
  }
}