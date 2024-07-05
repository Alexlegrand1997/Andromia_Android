package ca.qc.cstj.andromia.ui.screens.register

import android.app.Application
import androidx.lifecycle.AndroidViewModel

import androidx.lifecycle.viewModelScope
import ca.qc.cstj.andromia.core.ApiResult
import ca.qc.cstj.andromia.core.Constants
import ca.qc.cstj.andromia.data.repositories.BoosterRepository
import ca.qc.cstj.andromia.data.repositories.ModelPreferencesManager
import ca.qc.cstj.andromia.data.repositories.ProfileRepository
import ca.qc.cstj.andromia.data.repositories.RegisterRepository
import ca.qc.cstj.andromia.models.Explorer
import ca.qc.cstj.andromia.models.Login
import ca.qc.cstj.andromia.models.Register
import ca.qc.cstj.andromia.ui.screens.booster.BoosterUIState
import ca.qc.cstj.andromia.ui.screens.login.LoginUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel(application: Application) : AndroidViewModel(application) {

    private val _registerUIState = MutableStateFlow<RegisterUIState>(RegisterUIState.Empty)
    val registerUIState: StateFlow<RegisterUIState> = _registerUIState.asStateFlow()

    private val _registerRepository = RegisterRepository()

    fun register(username: String, email: String, password: String) {
        viewModelScope.launch {
            val credentials = Register(username, email, password)
            _registerRepository.register(credentials).collect { apiResult ->
                _registerUIState.update {
                    when (apiResult) {
                        is ApiResult.Error -> RegisterUIState.Error(IllegalStateException(apiResult.throwable))
                        ApiResult.Loading -> RegisterUIState.Loading
                        is ApiResult.Success -> {
                            save(apiResult.data)
                            RegisterUIState.Success(apiResult.data)
                        }
                    }
                }
            }
        }
    }

    private fun save(explorer: Explorer) {
        viewModelScope.launch {
            ModelPreferencesManager.put(explorer.explorateur, "KEY_ACCOUNT")
            ModelPreferencesManager.put(explorer.tokens, "KEY_TOKENS")
        }
    }

}