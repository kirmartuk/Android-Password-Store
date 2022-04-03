/*
 * Copyright © 2014-2021 The Android Password Store Authors. All Rights Reserved.
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.martyuk.utils.extensions

import android.app.KeyguardManager
import android.content.ClipboardManager
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.util.Base64
import android.util.TypedValue
import android.view.autofill.AutofillManager
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.FragmentActivity
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

/** Get an instance of [AutofillManager]. Only available on Android Oreo and above */
val Context.autofillManager: AutofillManager?
  get() = getSystemService()
val Context.isAutofillServiceEnabled: Boolean
  get() {
    return this.autofillManager?.hasEnabledAutofillServices() == true
  }

/** Get an instance of [ClipboardManager] */
val Context.clipboard
  get() = getSystemService<ClipboardManager>()

/** Wrapper for [getEncryptedPrefs] to avoid open-coding the file name at each call site */
fun Context.getEncryptedGitPrefs() = getEncryptedPrefs("git_operation")

/** Get an instance of [EncryptedSharedPreferences] with the given [fileName] */
private fun Context.getEncryptedPrefs(fileName: String): SharedPreferences {
  val masterKeyAlias =
    MasterKey.Builder(applicationContext).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()
  return EncryptedSharedPreferences.create(
    applicationContext,
    fileName,
    masterKeyAlias,
    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
  )
}

/** Get an instance of [KeyguardManager] */
val Context.keyguardManager: KeyguardManager
  get() = getSystemService()!!

/** Get the default [SharedPreferences] instance */
val Context.sharedPrefs: SharedPreferences
  get() = getSharedPreferences("${this.packageName}_preferences", 0)
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
val Context.sshKeyDataStore: DataStore<Preferences> by preferencesDataStore(name = "androidx_sshkey_keyset_prefs")

/** Resolve [attr] from the [Context]'s theme */
fun Context.resolveAttribute(attr: Int): Int {
  val typedValue = TypedValue()
  this.theme.resolveAttribute(attr, typedValue, true)
  return typedValue.data
}
/*
*/
/**
 * Commit changes to the store from a [FragmentActivity] using a custom implementation of
 * [GitOperation]
 *//*
suspend fun FragmentActivity.commitChange(
  message: String,
): Result<Unit, Throwable> {
  if (!PasswordRepository.isGitRepo()) {
    return Ok(Unit)
  }
  return object : GitOperation(this@commitChange) {
      override val commands =
        arrayOf(
          // Stage all files
          git.add().addFilepattern("."),
          // Populate the changed files count
          git.status(),
          // Commit everything! If anything changed, that is.
          git.commit().setAll(true).setMessage(message),
        )

      override fun preExecute(): Boolean {
        logcat { "Committing with message: '$message'" }
        return true
      }
    }
    .execute()
}*/

/** Check if [permission] has been granted to the app. */
fun FragmentActivity.isPermissionGranted(permission: String): Boolean {
  return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
}
/*
*/
/**
 * Show a [Snackbar] in a [FragmentActivity] and correctly anchor it to a
 * [com.google.android.material.floatingactionbutton.FloatingActionButton] if one exists in the
 * [view]
 *//*
fun FragmentActivity.snackbar(
  view: View = findViewById(android.R.id.content),
  message: String,
  length: Int = Snackbar.LENGTH_SHORT,
): Snackbar {
  val snackbar = Snackbar.make(view, message, length)
  snackbar.anchorView = findViewById(R.id.fab)
  snackbar.show()
  return snackbar
}*/

/** Simplifies the common `getString(key, null) ?: defaultValue` case slightly */
fun SharedPreferences.getString(key: String): String? = getString(key, null)

/** Convert this [String] to its [Base64] representation */
fun String.base64(): String {
  return Base64.encodeToString(encodeToByteArray(), Base64.NO_WRAP)
}