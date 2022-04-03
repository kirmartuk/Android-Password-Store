package com.martyuk.utils.ssh_keygen

import android.security.keystore.KeyProperties

enum class Algorithm(val algorithmName: String) {
    Rsa(
      KeyProperties.KEY_ALGORITHM_RSA
    ),
    Ecdsa(
      KeyProperties.KEY_ALGORITHM_EC
    )
  }