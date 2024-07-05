package ca.qc.cstj.andromia.data.datasources

import ca.qc.cstj.andromia.models.Explorer
import ca.qc.cstj.andromia.models.Login
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.json.responseJson
import com.github.kittinunf.result.Result
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class LoginDataSource {

    private val json = Json { ignoreUnknownKeys = true }

    fun login(href: String, credentials: Login): Explorer {
        val (_, _, result) = href.httpPost().jsonBody(Json.encodeToString(credentials))
            .responseJson()

        when (result) {
            is Result.Success -> return json.decodeFromString(result.value.content)
            is Result.Failure -> throw result.error.exception
        }
    }
}
