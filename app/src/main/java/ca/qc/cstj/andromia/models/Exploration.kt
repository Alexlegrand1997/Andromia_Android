package ca.qc.cstj.andromia.models

import ca.qc.cstj.andromia.core.DateSerializer
import kotlinx.serialization.Serializable

import java.util.Date

@Serializable
data class Exploration(
    val exploration: InfoExploration=InfoExploration()
    )

@Serializable
data class InfoExploration(
//    @Serializable(with = DateSerializer::class)
    val explorationDate: String="",
    val destination: String = "",
    val affinity: String = "",
    val ally: Ally= Ally(),
    val vault: Vault= Vault()

)