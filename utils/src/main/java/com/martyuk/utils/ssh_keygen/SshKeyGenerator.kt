package com.martyuk.utils.ssh_keygen

import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.security.PrivateKey
import java.security.PublicKey
import java.security.Security
import javax.inject.Inject
import net.schmizz.sshj.common.Buffer
import net.schmizz.sshj.common.KeyType
import org.bouncycastle.jce.provider.BouncyCastleProvider

class SshKeyGenerator @Inject constructor(private val keyGenParameterGenerator: KeyGenParameterGenerator) {

  fun generateKeystoreNativeKey(algorithm: Algorithm, requireAuthentication: Boolean): KeyPair {
    Security.addProvider(BouncyCastleProvider())
    val keyPairGenerator = KeyPairGenerator.getInstance(algorithm.algorithmName, ANDROID_KEY_STORE_PROVIDER)
    val specs: KeyGenParameterSpec.Builder = keyGenParameterGenerator.getKeyGenParametersByAlgorithm(algorithm)

    specs.setUserAuthenticationRequired(requireAuthentication)
    specs.setUserAuthenticationTimeout(DEFAULT_TIMING)

    keyPairGenerator.initialize(specs.build())
    return keyPairGenerator.generateKeyPair()
  }

  fun parseSshPublicKey(sshPublicKey: String): PublicKey? {
    val sshKeyParts = sshPublicKey.split("""\s+""".toRegex())
    if (sshKeyParts.size < 2) return null
    return Buffer.PlainBuffer(Base64.decode(sshKeyParts[1], Base64.NO_WRAP)).readPublicKey()
  }

  fun toSshPublicKey(publicKey: PublicKey): String {
    val rawPublicKey = Buffer.PlainBuffer().putPublicKey(publicKey).compactData
    val keyType = KeyType.fromKey(publicKey)
    return "$keyType ${Base64.encodeToString(rawPublicKey, Base64.NO_WRAP)}"
  }

  private fun KeyGenParameterSpec.Builder.setUserAuthenticationTimeout(timeout: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
      this.setUserAuthenticationParameters(timeout, KeyProperties.AUTH_DEVICE_CREDENTIAL)
    } else {
      @Suppress("DEPRECATION") this.setUserAuthenticationValidityDurationSeconds(timeout)
    }
  }

  companion object {

    const val KEYSTORE_ALIAS = "sshkey"
    private val KeyStore.sshPrivateKey
      get() = getKey(KEYSTORE_ALIAS, null) as? PrivateKey
    private val KeyStore.sshPublicKey
      get() = getCertificate(KEYSTORE_ALIAS)?.publicKey
    private const val DEFAULT_TIMING = 30
    private const val ANDROID_KEY_STORE_PROVIDER = "AndroidKeyStore"
  }
}