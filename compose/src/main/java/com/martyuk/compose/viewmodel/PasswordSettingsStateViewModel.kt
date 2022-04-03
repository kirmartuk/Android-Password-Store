package com.martyuk.compose.viewmodel

import androidx.lifecycle.viewModelScope
import com.martyuk.compose.event.PasswordSettingsUiEvent
import com.martyuk.compose.reducer.PasswordSettingsReducer
import com.martyuk.compose.repository.StateRepository
import com.martyuk.compose.state.PasswordSettingsState
import com.martyuk.utils.extensions.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@HiltViewModel
class PasswordSettingsStateViewModel @Inject constructor(
  private val stateRepository: StateRepository,
  private val reducer: PasswordSettingsReducer,
  val dataStoreManager: DataStoreManager
) : BaseViewModel<PasswordSettingsState, PasswordSettingsUiEvent>() {

  override val state: Flow<PasswordSettingsState> = reducer.state
  fun showData() {
    sendEvent(PasswordSettingsUiEvent.ShowData(stateRepository.getPasswordSettingsState().data))
  }

  fun sendEvent(event: PasswordSettingsUiEvent) {
    reducer.sendEvent(event)
  }

  fun setBooleanToDataStore(key: String, value: Boolean) {
    viewModelScope.launch(Dispatchers.IO) {
      dataStoreManager.setBoolean(key, value)
    }
  }
}