package com.martyuk.utils.extensions

import kotlinx.coroutines.flow.Flow

interface DataStoreManager {

  fun observeString(key: String): Flow<String?>
  fun observeInteger(key: String): Flow<Int?>

  fun getIntegerBlocking(key: String): Int?
  fun getStringBlocking(key: String): String?
  fun getBooleanBlocking(key: String): Boolean?

  suspend fun getIntegerAsync(key: String): Int?
  suspend fun getStringAsync(key: String): String?

  fun setStringBlocking(key: String, value: String)
  fun setBooleanBlocking(key: String, value: Boolean)
  fun setIntegerBlocking(key: String, value: Int)

  suspend fun setString(key: String, value: String)
  suspend fun setBoolean(key: String, value: Boolean)
  suspend fun setInteger(key: String, value: Int)

  fun clear()
}