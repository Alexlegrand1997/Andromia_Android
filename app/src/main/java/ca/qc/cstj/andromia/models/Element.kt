package ca.qc.cstj.andromia.models

import kotlinx.serialization.Serializable

@Serializable
data class Element(
    val element: String = "",
    val quantity: Int = 0
)
