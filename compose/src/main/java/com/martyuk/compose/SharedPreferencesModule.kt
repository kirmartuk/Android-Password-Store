package com.martyuk.compose

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SharedPreferencesModule {

  @Singleton
  @Provides
  fun providesSharedPreference(@ApplicationContext context: Context): SharedPreferences {
    val preferenceFileName: String = (context.packageName ?: "package") + "_preferences"
    return context.getSharedPreferences(preferenceFileName, Context.MODE_PRIVATE)
  }
}