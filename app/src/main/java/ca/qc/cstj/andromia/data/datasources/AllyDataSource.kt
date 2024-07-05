package ca.qc.cstj.andromia.data.datasources

import ca.qc.cstj.andromia.core.Constants
import ca.qc.cstj.andromia.models.Ally
import ca.qc.cstj.andromia.models.Explorer
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.json.responseJson
import com.github.kittinunf.result.Result
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class AllyDataSource {

    private val json = Json { ignoreUnknownKeys = true }

    fun getAll(accessToken: String): List<Ally> {
        val (_, _, result) = Constants.ALLY_URL.httpGet().appendHeader("Authorization", "Bearer $accessToken").responseJson()

        when (result) {
            is Result.Success -> return json.decodeFromString(result.value.content)
            is Result.Failure -> throw result.error.exception
        }
    }

    fun getOne(href: String): Ally {
        val (_, _, result) = href.httpGet().responseJson()

        when (result) {
            is Result.Success -> return json.decodeFromString(result.value.content)
            is Result.Failure -> throw result.error.exception
        }
    }
}