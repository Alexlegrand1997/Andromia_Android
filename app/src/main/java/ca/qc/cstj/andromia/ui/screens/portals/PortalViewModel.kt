package ca.qc.cstj.andromia.ui.screens.portals

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import ca.qc.cstj.andromia.core.ApiResult
import ca.qc.cstj.andromia.data.repositories.AllyRepository
import ca.qc.cstj.andromia.data.repositories.ExplorationRepository
import ca.qc.cstj.andromia.data.repositories.ModelPreferencesManager
import ca.qc.cstj.andromia.models.Account
import ca.qc.cstj.andromia.models.Explorer
import ca.qc.cstj.andromia.ui.screens.portals.PortalUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PortalViewModel(application: Application): AndroidViewModel(application) {
    private val _portalUIState = MutableStateFlow<PortalUIState>(PortalUIState.Loading)
    val portalUIState: StateFlow<PortalUIState> =_portalUIState.asStateFlow()

    private val _explorationRepository = ExplorationRepository()
    private var account = ModelPreferencesManager.get<Account>("KEY_ACCOUNT")


}