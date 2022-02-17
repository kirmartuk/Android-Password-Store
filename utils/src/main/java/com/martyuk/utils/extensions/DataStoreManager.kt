package com.martyuk.utils.extensions

import kotlinx.coroutines.flow.Flow

interface DataStoreManager {

  fun observeString(key: String): Flow<String?>
  suspend fun getString(key: String): String?
  fun getStringBlocking(key: String): String?
  fun getBooleanBlocking(key: String): Boolean?
  suspend fun getStringAsync(key: String): String?
  suspend fun setString(key: String, value: String)
  suspend fun setBoolean(key: String, value: Boolean)
}