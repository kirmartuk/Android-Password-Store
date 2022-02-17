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