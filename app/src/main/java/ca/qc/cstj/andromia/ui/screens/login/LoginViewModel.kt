package ca.qc.cstj.andromia.ui.screens.login

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.qc.cstj.andromia.core.ApiResult
import ca.qc.cstj.andromia.core.Constants
import ca.qc.cstj.andromia.data.repositories.LoginRepository
import ca.qc.cstj.andromia.data.repositories.ModelPreferencesManager
import ca.qc.cstj.andromia.data.repositories.ProfileRepository
import ca.qc.cstj.andromia.models.Explorer
import ca.qc.cstj.andromia.models.Login
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val _loginUiState = MutableStateFlow<LoginUIState>(LoginUIState.Loading)
    val loginUiState: StateFlow<LoginUIState> = _loginUiState.asStateFlow()

    private val _loginRepository = LoginRepository()

    init {
        viewModelScope.launch {
            _loginUiState.update { LoginUIState.Empty }
        }
    }

    fun login(username: String, password: String) {
        viewModelScope.launch {
            val credentials = Login(username, password)
            _loginRepository.login(Constants.LOGIN_URL, credentials).collect { apiResult ->
                _loginUiState.update {
                    when (apiResult) {
                        is ApiResult.Error -> LoginUIState.Error(IllegalStateException(apiResult.throwable))
                        ApiResult.Loading -> LoginUIState.Loading
                        is ApiResult.Success -> {
                            save(apiResult.data)
                            LoginUIState.Success(apiResult.data)
                        }
                    }
                }
            }
        }
    }

    private fun save(explorer: Explorer){
        viewModelScope.launch {
            ModelPreferencesManager.put(explorer.explorateur, "KEY_ACCOUNT")
            ModelPreferencesManager.put(explorer.tokens, "KEY_TOKENS")
        }
    }
}