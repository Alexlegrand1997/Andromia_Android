package ca.qc.cstj.andromia.ui.screens.Ally

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import ca.qc.cstj.andromia.core.ApiResult
import ca.qc.cstj.andromia.data.repositories.AllyRepository
import ca.qc.cstj.andromia.data.repositories.ModelPreferencesManager
import ca.qc.cstj.andromia.models.Account
import ca.qc.cstj.andromia.models.Explorer
import ca.qc.cstj.andromia.models.Tokens
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AllyViewModel(application: Application) : AndroidViewModel(application) {
    private val _allyUIState = MutableStateFlow<AllyUIState>(AllyUIState.Loading)
    val allyUIState: StateFlow<AllyUIState> = _allyUIState.asStateFlow()

    private val _allyRepository = AllyRepository()
    private var account = ModelPreferencesManager.get<Account>("KEY_ACCOUNT")
    private var tokens = ModelPreferencesManager.get<Tokens>("KEY_TOKENS")

    init {
        viewModelScope.launch {
            tokens?.let { tokens ->
                _allyRepository.retrieveAll(tokens.accessToken).collect { apiResult ->
                    _allyUIState.update {
                        when (apiResult) {
                            is ApiResult.Error -> AllyUIState.Error(
                                IllegalStateException(
                                    apiResult.throwable
                                )
                            )

                            ApiResult.Loading -> AllyUIState.Loading
                            is ApiResult.Success -> AllyUIState.Success(apiResult.data)
                        }
                    }
                }
            }
        }
    }


}
