package ca.qc.cstj.andromia.dto

import ca.qc.cstj.andromia.models.Account
import ca.qc.cstj.andromia.models.Element
import ca.qc.cstj.andromia.models.Explorer

@kotlinx.serialization.Serializable
class BoosterDTO (
    val explorateur: Account,
    val loot: Loot
) {
    @kotlinx.serialization.Serializable
    data class Loot(
        val inox: Int = 0,
        val elements: List<Element> = listOf()
    )
}