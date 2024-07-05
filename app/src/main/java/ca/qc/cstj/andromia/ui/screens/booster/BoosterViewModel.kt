package ca.qc.cstj.andromia.ui.screens.booster

import android.app.Application
import androidx.lifecycle.AndroidViewModel

import androidx.lifecycle.viewModelScope
import ca.qc.cstj.andromia.core.ApiResult
import ca.qc.cstj.andromia.data.repositories.BoosterRepository
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

class BoosterViewModel(application: Application) : AndroidViewModel(application) {

    private val _boosterUIState = MutableStateFlow<BoosterUIState>(BoosterUIState.Loading)
    val boosterUIState: StateFlow<BoosterUIState> = _boosterUIState.asStateFlow()

    private val _profileRepository = ProfileRepository()
    private val _boosterRepository = BoosterRepository()

    private var account = ModelPreferencesManager.get<Account>("KEY_ACCOUNT")
    private var tokens = ModelPreferencesManager.get<Tokens>("KEY_TOKENS")

    //TODO()
    init {
        getExplorer()
    }

    private fun getExplorer() {
        viewModelScope.launch {
            delay(1000)
            account?.let { account ->
                tokens?.let { tokens ->
                    _profileRepository.retrieveOne(account.href, tokens.accessToken)
                        .collect { apiResult ->
                            _boosterUIState.update {
                                when (apiResult) {
                                    is ApiResult.Error -> BoosterUIState.Error(
                                        IllegalStateException(
                                            apiResult.throwable
                                        )
                                    )

                                    ApiResult.Loading -> BoosterUIState.Loading
                                    is ApiResult.Success -> {
                                        save(apiResult.data)
                                        BoosterUIState.Success(apiResult.data)
                                    }
                                }
                            }
                        }
                }
            }
        }
    }

    fun buyBooster(buyMe: String, price: Number) {
        viewModelScope.launch {
            tokens?.let { tokens ->
                _boosterRepository.buyOne(buyMe, tokens.accessToken).collect { apiResult ->
                    when (apiResult) {
                        is ApiResult.Error -> {
                            _boosterUIState.update {
                                BoosterUIState.Error(
                                    IllegalStateException(
                                        apiResult.throwable
                                    )
                                )
                            }
                        }

                        ApiResult.Loading -> BoosterUIState.Loading
                        is ApiResult.Success -> {
                            _boosterUIState.update {
                                BoosterUIState.BoosterBuyed(apiResult.data)
                            }
                        }

                    }
                }
            }
        }
    }

    fun refreshBoosters(account: Account) {
        _boosterUIState.update {
            BoosterUIState.Success(account)
        }
    }

    private fun save(account: Account) {
        viewModelScope.launch {
            ModelPreferencesManager.put(account, "KEY_ACCOUNT")
        }
    }

}

