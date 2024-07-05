package ca.qc.cstj.andromia.models

import kotlinx.serialization.Serializable

@Serializable
data class Ally(
    val uuid: String = "",
    val name: String = "",
    val essence: Int = 0,
    val asset: String = "",
    val stats: Stat=Stat(),
    val affinity: String = "",
    val archivedIndex: Int = 0,
    val crypto: Crypto= Crypto(),
    val href: String = "",
    val books: List<String> = listOf(),
    val kernel: List<String> = listOf(),
    val captureMe:String =""
)

@Serializable
data class Crypto(
    val hash: String = "",
    val signature: String = ""
)

@Serializable
data class Stat(
    val life: Int = 1,
    val speed: Int = 1,
    val power: Int = 1,
    val shield: Int = 1
)
