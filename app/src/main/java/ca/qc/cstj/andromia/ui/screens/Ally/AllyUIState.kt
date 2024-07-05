package ca.qc.cstj.andromia.ui.screens.Ally

import ca.qc.cstj.andromia.models.Ally



sealed class AllyUIState {
    class Success(val ally: List<Ally>) : AllyUIState()
    object Loading : AllyUIState()
    class Error(val exception: Exception) : AllyUIState()
}