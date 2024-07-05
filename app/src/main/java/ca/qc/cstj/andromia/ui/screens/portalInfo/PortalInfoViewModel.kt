package ca.qc.cstj.andromia.ui.screens.portalInfo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import ca.qc.cstj.andromia.core.ApiResult
import ca.qc.cstj.andromia.data.repositories.ExplorationRepository
import ca.qc.cstj.andromia.data.repositories.ModelPreferencesManager
import ca.qc.cstj.andromia.models.Account
import ca.qc.cstj.andromia.models.Explorer
import ca.qc.cstj.andromia.models.Tokens
import ca.qc.cstj.andromia.ui.screens.portals.PortalUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PortalInfoViewModel(application: Application, savedStateHandle: SavedStateHandle) :
    AndroidViewModel(application) {
    private val _portalInfoUIState = MutableStateFlow<PortalInfoUIState>(PortalInfoUIState.Loading)
    val portalUIState: StateFlow<PortalInfoUIState> = _portalInfoUIState.asStateFlow()

    private val _portalInfoAllyUIState =
        MutableStateFlow<PortalInfoAllyUIState>(PortalInfoAllyUIState.Loading)
    val portalInfoAllyUIState: StateFlow<PortalInfoAllyUIState> =
        _portalInfoAllyUIState.asStateFlow()

    private val _explorationRepository = ExplorationRepository()
    private var tokens = ModelPreferencesManager.get<Tokens>("KEY_TOKENS")
    private val qrCode: String = checkNotNull(savedStateHandle["qrCode"])

    init {
        viewModelScope.launch {
            tokens?.let { tokens ->
                _explorationRepository.retrieveOne(tokens.accessToken, qrCode)
                    .collect { apiResult ->
                        _portalInfoUIState.update {
                            when (apiResult) {
                                is ApiResult.Error -> PortalInfoUIState.Error(
                                    IllegalStateException(
                                        apiResult.throwable
                                    )
                                )

                                ApiResult.Loading -> PortalInfoUIState.Loading
                                is ApiResult.Success -> PortalInfoUIState.Success(apiResult.data)
                            }
                        }
                    }
            }
        }
    }

    fun capture(captureMeHref: String) {
        viewModelScope.launch {
            tokens?.let { tokens ->
                _explorationRepository.captureOne(tokens.accessToken, captureMeHref)
                    .collect { apiResult ->
                        _portalInfoAllyUIState.update {
                            when (apiResult) {
                                is ApiResult.Error -> PortalInfoAllyUIState.Error(
                                    IllegalStateException(
                                        apiResult.throwable
                                    )
                                )

                                ApiResult.Loading -> PortalInfoAllyUIState.Loading
                                is ApiResult.Success -> PortalInfoAllyUIState.Success(apiResult.data)
                            }
                        }
                    }

            }
        }
    }

}
