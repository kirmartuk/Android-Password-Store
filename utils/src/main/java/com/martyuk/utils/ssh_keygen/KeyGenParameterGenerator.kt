package com.martyuk.utils.ssh_keygen

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import com.martyuk.utils.extensions.unsafeLazy
import javax.inject.Inject

class KeyGenParameterGenerator @Inject constructor(context: Context) {

  private val isStrongBoxSupported by unsafeLazy {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
      context.packageManager.hasSystemFeature(PackageManager.FEATURE_STRONGBOX_KEYSTORE)
    } else {
      false
    }
  }

  fun getKeyGenParametersByAlgorithm(algorithm: Algorithm): KeyGenParameterSpec.Builder {
    val builder = KeyGenParameterSpec.Builder(SshKeyGenerator.KEYSTORE_ALIAS, KeyProperties.PURPOSE_SIGN)
    return when (algorithm) {
      Algorithm.Rsa -> {
        builder.setKeySize(3072)
        builder.setSignaturePaddings(KeyProperties.SIGNATURE_PADDING_RSA_PKCS1)
        builder.setDigests(
          KeyProperties.DIGEST_SHA1,
          KeyProperties.DIGEST_SHA256,
          KeyProperties.DIGEST_SHA512
        )
        builder
      }
      Algorithm.Ecdsa -> {
        builder.setKeySize(256)
        builder.setAlgorithmParameterSpec(java.security.spec.ECGenParameterSpec("secp256r1"))
        builder.setDigests(KeyProperties.DIGEST_SHA256)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
          builder.setIsStrongBoxBacked(isStrongBoxSupported)
        }
        builder
      }
    }
  }
}