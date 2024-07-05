package ca.qc.cstj.andromia.data.repositories

import android.util.Log
import ca.qc.cstj.andromia.core.ApiResult
import ca.qc.cstj.andromia.data.datasources.LoginDataSource
import ca.qc.cstj.andromia.models.Explorer
import ca.qc.cstj.andromia.models.Login
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class LoginRepository {
    private val loginDataSource = LoginDataSource()

    fun login(href:String, rawValue: Login): Flow<ApiResult<Explorer>> {
        return flow {
            try{
                emit(ApiResult.Loading)
                val explorer = loginDataSource.login(href, rawValue)
                emit(ApiResult.Success(explorer))
            } catch (ex: Exception) {
                emit(ApiResult.Error(ex))
            }
        }.flowOn(Dispatchers.IO)
    }
}