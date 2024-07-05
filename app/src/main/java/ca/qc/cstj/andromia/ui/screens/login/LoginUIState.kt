package ca.qc.cstj.andromia.ui.screens.login

import ca.qc.cstj.andromia.models.Explorer

sealed class LoginUIState {
    class Success(val explorer: Explorer) : LoginUIState()
    object Loading: LoginUIState()
    object Empty: LoginUIState()
    class Error(val exception: Exception): LoginUIState()
}