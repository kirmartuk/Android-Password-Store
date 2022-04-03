package com.martyuk.compose.viewmodel

import androidx.lifecycle.viewModelScope
import com.martyuk.compose.repository.StateRepository
import com.martyuk.compose.reducer.AutoFillSettingsReducer
import com.martyuk.compose.state.AutoFillSettingsState
import com.martyuk.compose.event.AutoFillSettingsUiEvent
import com.martyuk.utils.extensions.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@HiltViewModel
class AutoFillSettingsViewModel @Inject constructor(
  private val stateRepository: StateRepository,
  private val reducer: AutoFillSettingsReducer,
  val dataStoreManager: DataStoreManager
) : BaseViewModel<AutoFillSettingsState, AutoFillSettingsUiEvent>() {

  override val state: Flow<AutoFillSettingsState> = reducer.state
  fun showData() {
    sendEvent(AutoFillSettingsUiEvent.ShowData(stateRepository.getAutoFillSettingsState().data))
  }

  fun sendEvent(event: AutoFillSettingsUiEvent) {
    reducer.sendEvent(event)
  }

  fun setStringToDataStore(key: String, value: Int) {
    viewModelScope.launch(Dispatchers.IO) {
      dataStoreManager.setInteger(key, value)
    }
  }

  fun setBooleanToDataStore(key: String, value: Boolean) {
    viewModelScope.launch(Dispatchers.IO) {
      dataStoreManager.setBoolean(key, value)
    }
  }
}