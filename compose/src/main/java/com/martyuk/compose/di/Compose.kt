package com.martyuk.compose.di

import com.martyuk.compose.reducer.AutoFillSettingsReducer
import com.martyuk.compose.reducer.GeneralSettingsReducer
import com.martyuk.compose.reducer.PasswordSettingsReducer
import com.martyuk.compose.reducer.ReducerFactory
import com.martyuk.compose.reducer.RepositorySettingsReducer
import com.martyuk.compose.state.AutoFillSettingsState
import com.martyuk.compose.state.GeneralSettingsState
import com.martyuk.compose.state.PasswordSettingsState
import com.martyuk.compose.state.RepositorySettingsState
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class Compose {

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