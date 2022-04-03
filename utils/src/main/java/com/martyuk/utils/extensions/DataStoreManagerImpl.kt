package com.martyuk.utils.extensions

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

class DataStoreManagerImpl @Inject constructor(private val context: Context
) : DataStoreManager {

  override fun observeString(key: String): Flow<String?> {
    val dataStoreKey: Preferences.Key<String> = stringPreferencesKey(key)
    return context.dataStore.data
      .map { preferences ->
        // No type safety.
        preferences[dataStoreKey]
      }
  }

  override fun observeInteger(key: String): Flow<Int?> {
    val dataStoreKey: Preferences.Key<Int> = intPreferencesKey(key)
    return context.dataStore.data
      .map { preferences ->
        // No type safety.
        preferences[dataStoreKey]
      }
  }

  override fun getIntegerBlocking(key: String): Int? = runBlocking {
    val dataStoreKey: Preferences.Key<Int> = intPreferencesKey(key)
    return@runBlocking context.dataStore.data.map { it[dataStoreKey] }.first()
  }

  override fun getStringBlocking(key: String): String? = runBlocking {
    val dataStoreKey: Preferences.Key<String> = stringPreferencesKey(key)
    return@runBlocking context.dataStore.data.map { it[dataStoreKey] }.first()
  }

  override fun getBooleanBlocking(key: String): Boolean? = runBlocking {
    val dataStoreKey: Preferences.Key<Boolean> = booleanPreferencesKey(key)
    return@runBlocking context.dataStore.data.map { it[dataStoreKey] }.first()
  }

  override suspend fun getIntegerAsync(key: String): Int? {
    val dataStoreKey: Preferences.Key<Int> = intPreferencesKey(key)
    return context.dataStore.data.map { it[dataStoreKey] }.first()
  }

  override suspend fun getStringAsync(key: String): String? {
    val dataStoreKey: Preferences.Key<String> = stringPreferencesKey(key)
    return context.dataStore.data.map { it[dataStoreKey] }.first()
  }

  override fun setStringBlocking(key: String, value: String): Unit = runBlocking {
    val dataStoreKey: Preferences.Key<String> = stringPreferencesKey(key)
    context.dataStore.edit { settings ->
      settings[dataStoreKey] = value
    }
  }

  override fun setBooleanBlocking(key: String, value: Boolean): Unit = runBlocking {
    val dataStoreKey: Preferences.Key<Boolean> = booleanPreferencesKey(key)
    context.dataStore.edit { settings ->
      settings[dataStoreKey] = value
    }
  }

  override fun setIntegerBlocking(key: String, value: Int): Unit = runBlocking {
    val dataStoreKey: Preferences.Key<Int> = intPreferencesKey(key)
    context.dataStore.edit { settings ->
      settings[dataStoreKey] = value
    }
  }

  override suspend fun setString(key: String, value: String) {
    val dataStoreKey: Preferences.Key<String> = stringPreferencesKey(key)
    context.dataStore.edit { settings ->
      settings[dataStoreKey] = value
    }
  }

  override suspend fun setBoolean(key: String, value: Boolean) {
    val dataStoreKey: Preferences.Key<Boolean> = booleanPreferencesKey(key)
    context.dataStore.edit { settings ->
      settings[dataStoreKey] = value
    }
  }

  override suspend fun setInteger(key: String, value: Int) {
    val dataStoreKey: Preferences.Key<Int> = intPreferencesKey(key)
    context.dataStore.edit { settings ->
      settings[dataStoreKey] = value
    }
  }

  override fun clear(): Unit = runBlocking {
    context.dataStore.edit {
      it.clear()
    }
  }
}