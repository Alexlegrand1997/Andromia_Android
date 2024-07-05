package ca.qc.cstj.andromia.data.datasources

import ca.qc.cstj.andromia.core.Constants
import ca.qc.cstj.andromia.models.Account
import ca.qc.cstj.andromia.models.Explorer
import ca.qc.cstj.andromia.models.Login
import ca.qc.cstj.andromia.models.Register
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.json.responseJson
import com.github.kittinunf.result.Result
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class RegisterDataSource {
    private val json = Json { ignoreUnknownKeys = true }

    fun register(credentials: Register): Explorer {
        val (_, _, result) = Constants.REGISTER_URL.httpPost().jsonBody(Json.encodeToString(credentials))
            .responseJson()

        when (result) {
            is Result.Success -> return json.decodeFromString(result.value.content)
            is Result.Failure -> throw result.error.exception
        }
    }
}