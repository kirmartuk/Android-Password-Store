package com.martyuk.utils.di

import android.content.Context
import android.content.res.Resources
import com.martyuk.utils.extensions.DataStoreManager
import com.martyuk.utils.extensions.DataStoreManagerImpl
import com.martyuk.utils.extensions.ResourcesManager
import com.martyuk.utils.extensions.ResourcesManagerImpl
import com.martyuk.utils.extensions.SshKeyDataStoreManager
import com.martyuk.utils.extensions.SshKeyDataStoreManagerImpl
import com.martyuk.utils.ssh_keygen.KeyGenParameterGenerator
import com.martyuk.utils.ssh_keygen.SshKeyGenerator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UtilsModule {

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
  fun providesSshKeyDataStoreManager(@ApplicationContext context: Context): SshKeyDataStoreManager {
    return SshKeyDataStoreManagerImpl(context)
  }

  @Provides
  fun providesSshKeyGenerator(keyGenParameterGenerator: KeyGenParameterGenerator): SshKeyGenerator {
    return SshKeyGenerator(keyGenParameterGenerator)
  }

  @Provides
  fun providesKeyGenParameterGenerator(@ApplicationContext context: Context): KeyGenParameterGenerator {
    return KeyGenParameterGenerator(context)
  }
}