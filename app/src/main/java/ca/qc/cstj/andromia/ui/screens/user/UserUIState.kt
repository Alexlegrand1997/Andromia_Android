package ca.qc.cstj.andromia.ui.screens.user

import ca.qc.cstj.andromia.models.Account
import ca.qc.cstj.andromia.models.Exploration
import ca.qc.cstj.andromia.models.Explorer
import ca.qc.cstj.andromia.models.InfoExploration

sealed class UserUIState {
    class Success(val account: Account) : UserUIState()
    object Loading : UserUIState()
    class Error(val exception: Exception) : UserUIState()
}

sealed class ExplorationsUIState {
    class Success(val explorations: List<InfoExploration>) : ExplorationsUIState()
    object Loading : ExplorationsUIState()
    class Error(val exception: Exception) : ExplorationsUIState()
}