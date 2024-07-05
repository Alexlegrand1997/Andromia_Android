package ca.qc.cstj.andromia.data.datasources

import ca.qc.cstj.andromia.core.Constants
import ca.qc.cstj.andromia.models.Ally
import ca.qc.cstj.andromia.models.Exploration
import ca.qc.cstj.andromia.models.InfoExploration
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPatch
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.httpPut
import com.github.kittinunf.fuel.json.responseJson
import com.github.kittinunf.result.Result
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class ExplorationDataSource {

    private val json = Json { ignoreUnknownKeys = true }

    fun getOne(accessToken:String,qrCode: String): Exploration {
        val href = "${Constants.EXPLORATION_URL}/${qrCode} "
        val (_, _, result) = href.httpPost().appendHeader("Authorization", "Bearer $accessToken").responseJson()

        when (result) {
            is Result.Success -> return json.decodeFromString(result.value.content)
            is Result.Failure -> throw result.error.exception
        }
    }

    fun getAll(accessToken:String): List<InfoExploration> {
        val href = "${Constants.EXPLORATION_URL}"
        val (_, _, result) = href.httpGet().appendHeader("Authorization", "Bearer $accessToken").responseJson()

        when (result) {
            is Result.Success -> return json.decodeFromString(result.value.content)
            is Result.Failure -> throw result.error.exception
        }
    }
    fun captureOne(accessToken:String,captureMe: String): Ally {
//        val (_, _, result) = captureMe.httpPatch().appendHeader("Authorization", "Bearer $accessToken").responseJson()
        val (_, _, result) = captureMe.httpPost().header("X-HTTP-Method-Override", "PATCH").appendHeader("Authorization", "Bearer $accessToken").responseJson()

        when (result) {
            is Result.Success -> return json.decodeFromString(result.value.content)
            is Result.Failure -> throw result.error.exception
        }
    }
}