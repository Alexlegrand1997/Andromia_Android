package ca.qc.cstj.andromia.dto

import ca.qc.cstj.andromia.models.Account
import ca.qc.cstj.andromia.models.Element
import ca.qc.cstj.andromia.models.Exploration
import ca.qc.cstj.andromia.models.Explorer

@kotlinx.serialization.Serializable
class ExplorationDTO (
    val exploration: Exploration,
    val explorer: Account
)