package ca.qc.cstj.andromia.models

import kotlinx.serialization.Serializable

@Serializable
data class Booster (
//    val id: Int = 0,
    val price: Int = 0,
    val rarity: String = "",
    val openMe: String = ""
)
