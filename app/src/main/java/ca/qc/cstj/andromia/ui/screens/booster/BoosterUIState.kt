package ca.qc.cstj.andromia.ui.screens.booster

import ca.qc.cstj.andromia.dto.BoosterDTO
import ca.qc.cstj.andromia.models.Account
import ca.qc.cstj.andromia.models.Booster
import ca.qc.cstj.andromia.models.Explorer
import ca.qc.cstj.andromia.ui.screens.login.LoginUIState

sealed class BoosterUIState {
    class Success(val account: Account) : BoosterUIState()
    class BoosterBuyed(val boosterDTO: BoosterDTO) : BoosterUIState()
    object Loading : BoosterUIState()
    class Error(val exception: Exception) : BoosterUIState()
}