package com.martyuk.compose.viewmodel

import androidx.lifecycle.viewModelScope
import com.martyuk.compose.event.RepositorySettingsUiEvent
import com.martyuk.compose.reducer.RepositorySettingsReducer
import com.martyuk.compose.repository.StateRepository
import com.martyuk.compose.state.RepositorySettingsState
import com.martyuk.compose.utils.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@HiltViewModel
class RepositorySettingsViewModel @Inject constructor(
  private val stateRepository: StateRepository,
  private val reducer: RepositorySettingsReducer,
  val dataStoreManager: DataStoreManager
) : BaseViewModel<RepositorySettingsState, RepositorySettingsUiEvent>() {

  override val state: Flow<RepositorySettingsState> = reducer.state
  fun showData() {
    sendEvent(RepositorySettingsUiEvent.ShowData(stateRepository.getRepositorySettingsState().data))
  }

  fun sendEvent(event: RepositorySettingsUiEvent) {
    reducer.sendEvent(event)
  }

  fun setBooleanToDataStore(key: String, value: Boolean) {
    viewModelScope.launch(Dispatchers.IO) {
      dataStoreManager.setBoolean(key, value)
    }
  }
}