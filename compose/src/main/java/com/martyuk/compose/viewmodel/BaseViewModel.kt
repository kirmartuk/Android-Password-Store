package com.martyuk.compose.viewmodel

import androidx.lifecycle.ViewModel
import com.martyuk.compose.reducer.UiEvent
import com.martyuk.compose.reducer.UiState
import kotlinx.coroutines.flow.Flow

abstract class BaseViewModel<T : UiState, in E : UiEvent> : ViewModel() {

    abstract val state: Flow<T>

}
