package ca.qc.cstj.andromia.ui.screens.register

import ca.qc.cstj.andromia.models.Explorer

sealed class RegisterUIState {
    class Success(val explorer: Explorer) : RegisterUIState()
    object Loading: RegisterUIState()
    object Empty: RegisterUIState()
    class Error(val exception: Exception): RegisterUIState()
}