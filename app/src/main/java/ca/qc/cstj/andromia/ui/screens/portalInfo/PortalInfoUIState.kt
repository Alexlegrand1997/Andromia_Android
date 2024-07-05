package ca.qc.cstj.andromia.ui.screens.portalInfo

import ca.qc.cstj.andromia.models.Ally
import ca.qc.cstj.andromia.models.Exploration
import ca.qc.cstj.andromia.ui.screens.portals.PortalUIState

sealed class PortalInfoUIState {
    class Success(val exploration: Exploration) : PortalInfoUIState()
    object Loading: PortalInfoUIState()
    class Error(val exception: Exception): PortalInfoUIState()
}

sealed class  PortalInfoAllyUIState{
    class Success(val ally: Ally) : PortalInfoAllyUIState()
    object Loading: PortalInfoAllyUIState()
    class Error(val exception: Exception): PortalInfoAllyUIState()
}