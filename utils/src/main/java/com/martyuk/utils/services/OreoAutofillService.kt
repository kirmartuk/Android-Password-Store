/*
 * Copyright © 2014-2021 The Android Password Store Authors. All Rights Reserved.
 * SPDX-License-Identifier: GPL-3.0-only
 */
package com.martyuk.utils.services

import android.os.Build
import android.os.CancellationSignal
import android.service.autofill.AutofillService
import android.service.autofill.FillCallback
import android.service.autofill.FillRequest
import android.service.autofill.FillResponse
import android.service.autofill.SaveCallback
import android.service.autofill.SaveRequest
import androidx.annotation.RequiresApi
import com.github.androidpasswordstore.autofillparser.AutofillScenario
import com.github.androidpasswordstore.autofillparser.FillableForm
import com.github.androidpasswordstore.autofillparser.FixedSaveCallback
import com.github.androidpasswordstore.autofillparser.FormOrigin
import com.github.androidpasswordstore.autofillparser.cachePublicSuffixList
import com.github.androidpasswordstore.autofillparser.passwordValue
import com.github.androidpasswordstore.autofillparser.recoverNodes
import com.github.androidpasswordstore.autofillparser.usernameValue
import com.martyuk.resources.R
import com.martyuk.utils.extensions.DataStoreManager
import com.martyuk.utils.extensions.PreferenceKeys
import com.martyuk.utils.extensions.hasFlag
import javax.inject.Inject
import logcat.LogPriority.ERROR
import logcat.logcat

@RequiresApi(Build.VERSION_CODES.O)
class OreoAutofillService : AutofillService() {

  @Inject
  lateinit var dataStoreManager: DataStoreManager

  companion object {

    // TODO: Provide a user-configurable denylist
    private val DENYLISTED_PACKAGES =
      listOf(
        "com.martyuk.compose",
        "android",
        "com.android.settings",
        "com.android.settings.intelligence",
        "com.android.systemui",
        "com.oneplus.applocker",
        "org.sufficientlysecure.keychain",
      )
    private const val DISABLE_AUTOFILL_DURATION_MS = 1000 * 60 * 60 * 24L
  }

  override fun onCreate() {
    super.onCreate()
    cachePublicSuffixList(applicationContext)
  }

  override fun onFillRequest(
    request: FillRequest,
    cancellationSignal: CancellationSignal,
    callback: FillCallback
  ) {
    val structure =
      request.fillContexts.lastOrNull()?.structure
        ?: run {
          callback.onSuccess(null)
          return
        }
    if (structure.activityComponent.packageName in DENYLISTED_PACKAGES) {
      if (Build.VERSION.SDK_INT >= 28) {
        callback.onSuccess(
          FillResponse.Builder().run {
            disableAutofill(DISABLE_AUTOFILL_DURATION_MS)
            build()
          }
        )
      } else {
        callback.onSuccess(null)
      }
      return
    }
    val formToFill =
      FillableForm.parseAssistStructure(
        this,
        structure,
        isManualRequest = request.flags hasFlag FillRequest.FLAG_MANUAL_REQUEST,
        getCustomSuffixes(),
      )
        ?: run {
          logcat { "Form cannot be filled" }
          callback.onSuccess(null)
          return
        }
  }

  override fun onSaveRequest(request: SaveRequest, callback: SaveCallback) {
    // SaveCallback's behavior and feature set differs based on both target and device SDK, so
    // we replace it with a wrapper that works the same in all situations.
    @Suppress("NAME_SHADOWING") val callback = FixedSaveCallback(this, callback)
    val structure =
      request.fillContexts.lastOrNull()?.structure
        ?: run {
          callback.onFailure(getString(R.string.oreo_autofill_save_app_not_supported))
          return
        }
    val clientState =
      request.clientState
        ?: run {
          logcat(ERROR) { "Received save request without client state" }
          callback.onFailure(getString(R.string.oreo_autofill_save_internal_error))
          return
        }
    val scenario =
      AutofillScenario.fromClientState(clientState)?.recoverNodes(structure)
        ?: run {
          logcat(ERROR) { "Failed to recover client state or nodes from client state" }
          callback.onFailure(getString(R.string.oreo_autofill_save_internal_error))
          return
        }
    val formOrigin =
      FormOrigin.fromBundle(clientState)
        ?: run {
          logcat(ERROR) { "Failed to recover form origin from client state" }
          callback.onFailure(getString(R.string.oreo_autofill_save_internal_error))
          return
        }
    val username = scenario.usernameValue
    val password =
      scenario.passwordValue
        ?: run {
          callback.onFailure(getString(R.string.oreo_autofill_save_passwords_dont_match))
          return
        }
    //TODO
    /*callback.onSuccess(
      AutofillSaveActivity.makeSaveIntentSender(
        this,
        credentials = Credentials(username, password, null),
        formOrigin = formOrigin
      )
    )*/
  }

  private fun getCustomSuffixes(): Sequence<String> {
    return dataStoreManager
      .getStringBlocking(PreferenceKeys.OREO_AUTOFILL_CUSTOM_PUBLIC_SUFFIXES)
      ?.splitToSequence('\n')
      ?.filter { it.isNotBlank() && it.first() != '.' && it.last() != '.' }
      ?: emptySequence()
  }
}
/*
fun Context.getDefaultUsername() =
  sharedPrefs.getString(PreferenceKeys.OREO_AUTOFILL_DEFAULT_USERNAME)
*/

