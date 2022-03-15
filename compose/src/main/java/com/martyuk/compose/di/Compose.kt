package com.martyuk.compose.di

import android.content.Context
import android.content.res.Resources
import com.martyuk.compose.reducer.AutoFillSettingsReducer
import com.martyuk.compose.reducer.GeneralSettingsReducer
import com.martyuk.compose.reducer.PasswordSettingsReducer
import com.martyuk.compose.reducer.ReducerFactory
import com.martyuk.compose.reducer.RepositorySettingsReducer
import com.martyuk.compose.state.AutoFillSettingsState
import com.martyuk.compose.state.GeneralSettingsState
import com.martyuk.compose.state.PasswordSettingsState
import com.martyuk.compose.state.RepositorySettingsState
import com.martyuk.compose.utils.DataStoreManager
import com.martyuk.compose.utils.DataStoreManagerImpl
import com.martyuk.compose.utils.ResourcesManager
import com.martyuk.compose.utils.ResourcesManagerImpl
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class Compose {

  @Singleton
  @Provides
  fun providesResourcesManager(resources: Resources): ResourcesManager {
    return ResourcesManagerImpl(resources)
  }

  @Provides
  @Singleton
  fun providesResources(@ApplicationContext context: Context): Resources {
    return context.resources
  }

  @Provides
  @Singleton
  fun providesDataStoreManager(@ApplicationContext context: Context): DataStoreManager {
    return DataStoreManagerImpl(context)
  }

  @Provides
  @Singleton
  fun providesGeneralSettingsReducer(): GeneralSettingsReducer {
    return GeneralSettingsReducer(GeneralSettingsState.initial())
  }

  @Provides
  @Singleton
  fun providesRepositorySettingsReducer(): RepositorySettingsReducer {
    return RepositorySettingsReducer(RepositorySettingsState.initial())
  }

  @Provides
  @Singleton
  fun providesAutoFillSettingsReducer(): AutoFillSettingsReducer {
    return AutoFillSettingsReducer(AutoFillSettingsState.initial())
  }

  @Provides
  @Singleton
  fun providesPasswordSettingsReducer(): PasswordSettingsReducer {
    return PasswordSettingsReducer(PasswordSettingsState.initial())
  }

  @Provides
  @Singleton
  fun providesReducerFactory(
    autoFillSettingsReducer: Lazy<AutoFillSettingsReducer>,
    generalSettingsReducer: Lazy<GeneralSettingsReducer>
  ): ReducerFactory {
    return ReducerFactory(autoFillSettingsReducer, generalSettingsReducer)
  }
}