package ca.qc.cstj.andromia.models

import android.media.session.MediaSession.Token
import kotlinx.serialization.Serializable

@Serializable
data class Explorer(
    val explorateur: Account = Account(),
    val tokens: Tokens = Tokens(),
)

@Serializable
data class Account(
    val email: String = "",
    val username: String = "",
    val uuid: String = "",
    val inox: Int = 0,
//    val allies: String = "",
    val href: String = "",
    val location: String ="",
    val elements: List<Element> = listOf(),
    val boosters: List<Booster> = listOf()
)

@Serializable
data class Tokens(
    val accessToken: String = "",
    val refreshToken: String = ""
)