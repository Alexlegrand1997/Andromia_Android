package ca.qc.cstj.andromia.models

import kotlinx.serialization.Serializable

@Serializable
data class Vault (
    val elements: List<Element> = listOf(),
    val inox: Int = 0
)
