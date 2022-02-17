package com.martyuk.compose.viewmodel

import androidx.lifecycle.ViewModel
import com.martyuk.compose.utils.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(val dataStoreManager: DataStoreManager) : ViewModel()