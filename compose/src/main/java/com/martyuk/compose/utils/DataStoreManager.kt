package com.martyuk.compose.utils

import kotlinx.coroutines.flow.Flow

interface DataStoreManager {

  fun observeString(key: String): Flow<String?>
  fun observeInteger(key: String): Flow<Int?>

  fun getIntegerBlocking(key: String): Int?
  fun getStringBlocking(key: String): String?
  fun getBooleanBlocking(key: String): Boolean?

  suspend fun getIntegerAsync(key: String): Int?
  suspend fun getStringAsync(key: String): String?

  suspend fun setString(key: String, value: String)
  suspend fun setBoolean(key: String, value: Boolean)
  suspend fun setInteger(key: String, value: Int)
}