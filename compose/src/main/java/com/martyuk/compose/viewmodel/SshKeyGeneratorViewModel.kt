package com.martyuk.compose.viewmodel

import androidx.lifecycle.ViewModel
import com.martyuk.utils.ssh_keygen.Algorithm
import com.martyuk.utils.ssh_keygen.SshKeyGenerator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SshKeyGeneratorViewModel @Inject constructor(private val sshKeGenerator: SshKeyGenerator) : ViewModel() {

  fun generateSsh(algorithm: Algorithm, requireAuthentication: Boolean) {
    val pairs = sshKeGenerator.generateKeystoreNativeKey(algorithm, requireAuthentication)
    print(sshKeGenerator.toSshPublicKey(pairs.public))
  }
}