package ca.qc.cstj.andromia.data.datasources

import ca.qc.cstj.andromia.core.Constants
import ca.qc.cstj.andromia.models.Account
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.json.responseJson
import com.github.kittinunf.result.Result
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class ProfileDataSource {

    private val json = Json { ignoreUnknownKeys = true }

    fun getOne(href: String, accessToken:String): Account {
        val (_, _, result) = href.httpGet().appendHeader("Authorization", "Bearer $accessToken").responseJson()

        when (result) {
            is Result.Success -> {
                return json.decodeFromString(result.value.content)
            }
            is Result.Failure -> throw result.error.exception
        }
    }

    fun refreshToken(refreshToken:String): Account {
        val (_, _, result) = Constants.REFRESH_TOKEN_URL.httpGet().appendHeader("Authorization", "Bearer $refreshToken").responseJson()

        when (result) {
            is Result.Success -> {
                return json.decodeFromString(result.value.content)
            }
            is Result.Failure -> throw result.error.exception
        }
    }
}