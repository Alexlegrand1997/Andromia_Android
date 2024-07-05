package ca.qc.cstj.andromia.models

import kotlinx.serialization.Serializable

@Serializable
data class Login (val username: String = "", val password: String = "")