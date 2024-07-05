package ca.qc.cstj.andromia.data.datasources

import ca.qc.cstj.andromia.core.Constants
import ca.qc.cstj.andromia.dto.BoosterDTO
import ca.qc.cstj.andromia.models.Booster
import ca.qc.cstj.andromia.models.Explorer
import ca.qc.cstj.andromia.models.Login
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.json.responseJson
import com.github.kittinunf.result.Result
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class BoosterDataSource {
    private val json = Json { ignoreUnknownKeys = true }

    fun buyOne(href: String, accessToken: String): BoosterDTO {
        val (_, _, result) = href.httpGet().appendHeader("Authorization", "Bearer $accessToken").responseJson()

        when (result) {
            is Result.Success -> return json.decodeFromString(result.value.content)
            is Result.Failure -> throw result.error.exception
        }
    }
}