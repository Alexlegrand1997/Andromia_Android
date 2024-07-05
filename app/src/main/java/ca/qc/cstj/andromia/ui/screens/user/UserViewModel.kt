package ca.qc.cstj.andromia.ui.screens.user

import android.app.Application
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.AndroidViewModel

import androidx.lifecycle.viewModelScope
import ca.qc.cstj.andromia.core.ApiResult
import ca.qc.cstj.andromia.core.AppPreferences
import ca.qc.cstj.andromia.data.repositories.ExplorationRepository
import ca.qc.cstj.andromia.data.repositories.ModelPreferencesManager
import ca.qc.cstj.andromia.data.repositories.ProfileRepository
import ca.qc.cstj.andromia.models.Account
import ca.qc.cstj.andromia.models.Explorer
import ca.qc.cstj.andromia.models.Tokens
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserViewModel(application: Application) :
    AndroidViewModel(application) {

    private val _userUIState = MutableStateFlow<UserUIState>(UserUIState.Loading)
    val userUIState: StateFlow<UserUIState> = _userUIState.asStateFlow()
    private val _explorationsUIState =
        MutableStateFlow<ExplorationsUIState>(ExplorationsUIState.Loading)
    val explorationsUIState: StateFlow<ExplorationsUIState> = _explorationsUIState.asStateFlow()
    private val appPreferences: AppPreferences = AppPreferences(application)

    private val _profileRepository = ProfileRepository()
    private val _profilRepository = ProfileRepository()
    private val _explorationRepository = ExplorationRepository()
    private var account = ModelPreferencesManager.get<Account>("KEY_ACCOUNT")
    private var tokens = ModelPreferencesManager.get<Tokens>("KEY_TOKENS")

    //TODO()
    init {
        getExplorer()
        getExploration()
    }

    private fun getExplorer() {
        viewModelScope.launch {
            delay(1000)
            account?.let { account ->
                tokens?.let { tokens ->
                    _profileRepository.retrieveOne(account.href, tokens.accessToken)
                        .collect { apiResult ->
                            _userUIState.update {
                                when (apiResult) {
                                    is ApiResult.Error -> UserUIState.Error(
                                        IllegalStateException(
                                            apiResult.throwable
                                        )
                                    )

                                    ApiResult.Loading -> UserUIState.Loading
                                    is ApiResult.Success -> {
                                        checkAndExecuteFunction(tokens.refreshToken)
                                        UserUIState.Success(apiResult.data)
                                    }
                                }
                            }
                        }
                }
            }
        }
    }

    private fun getExploration() {
        viewModelScope.launch {
            ExplorationsUIState.Loading
            tokens?.let { tokens ->
                _explorationRepository.retrieveAll(tokens.accessToken)
                    .collect { apiResult ->
                        _explorationsUIState.update {
                            when (apiResult) {
                                is ApiResult.Error -> ExplorationsUIState.Error(
                                    IllegalStateException(
                                        apiResult.throwable
                                    )
                                )

                                ApiResult.Loading -> ExplorationsUIState.Loading
                                is ApiResult.Success -> {
                                    ExplorationsUIState.Success(apiResult.data)
                                }
                            }
                        }
                    }
            }
        }
    }

    fun delete(explorer: Explorer) {
        viewModelScope.launch {
            ModelPreferencesManager.put(explorer.explorateur, "KEY_ACCOUNT")
            ModelPreferencesManager.put(explorer.tokens, "KEY_TOKENS")
            checkAndTurnOffFlag()
        }
    }

    private fun checkAndExecuteFunction(refreshToken: String) {
        viewModelScope.launch {
            if (!appPreferences.isFunctionExecuted()) {
                _profilRepository.refreshToken(refreshToken)
                appPreferences.setFunctionExecutedFlag(true)
            }
        }
    }

    private fun checkAndTurnOffFlag() {
        viewModelScope.launch {
            if (appPreferences.isFunctionExecuted()) {
                appPreferences.setFunctionExecutedFlag(false)
            }
        }
    }
}

