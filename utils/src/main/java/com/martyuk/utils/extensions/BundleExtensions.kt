package com.martyuk.utils.extensions

import android.os.Bundle
import android.os.Parcelable

inline fun <reified T : Parcelable> Bundle?.getParcelableOrNull(key: String?): T? {
  return try {
    this?.getParcelable(key) as T?
  } catch (classCasException: ClassCastException) {
    null
  }
}