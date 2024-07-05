package ca.qc.cstj.andromia.ui.screens.portals

import ca.qc.cstj.andromia.models.Exploration
import ca.qc.cstj.andromia.models.Explorer
import ca.qc.cstj.andromia.ui.screens.login.LoginUIState

sealed class PortalUIState {
    class Success(val exploration: Exploration) : PortalUIState()
    object Loading: PortalUIState()
    class Error(val exception: Exception): PortalUIState()
}