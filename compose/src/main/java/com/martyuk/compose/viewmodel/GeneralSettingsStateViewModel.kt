package com.martyuk.compose.viewmodel

import androidx.lifecycle.viewModelScope
import com.martyuk.compose.repository.StateRepository
import com.martyuk.compose.reducer.GeneralSettingsReducer
import com.martyuk.compose.state.GeneralSettingsState
import com.martyuk.compose.event.GeneralSettingsUiEvent
import com.martyuk.compose.utils.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@HiltViewModel
class GeneralSettingsStateViewModel @Inject constructor(
  private val stateRepository: StateRepository,
  private val reducer: GeneralSettingsReducer,
  val dataStoreManager: DataStoreManager
) : BaseViewModel<GeneralSettingsState, GeneralSettingsUiEvent>() {

  override val state: Flow<GeneralSettingsState> = reducer.state
  fun showData() {
    sendEvent(GeneralSettingsUiEvent.ShowData(stateRepository.getGeneralSettingsState().data))
  }

  private fun sendEvent(event: GeneralSettingsUiEvent) {
    reducer.sendEvent(event)
  }

  fun setBooleanToDataStore(key: String, value: Boolean) {
    viewModelScope.launch(Dispatchers.IO) {
      dataStoreManager.setBoolean(key, value)
    }
  }
}