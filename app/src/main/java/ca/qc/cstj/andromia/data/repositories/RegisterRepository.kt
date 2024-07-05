package ca.qc.cstj.andromia.data.repositories

import ca.qc.cstj.andromia.core.ApiResult
import ca.qc.cstj.andromia.data.datasources.ProfileDataSource
import ca.qc.cstj.andromia.data.datasources.RegisterDataSource
import ca.qc.cstj.andromia.models.Account
import ca.qc.cstj.andromia.models.Explorer
import ca.qc.cstj.andromia.models.Register
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RegisterRepository {
    private val _registerDataSource = RegisterDataSource()

    fun register(credentials: Register): Flow<ApiResult<Explorer>> {
        return flow {
            emit(ApiResult.Loading)
            try {
                val explorer = _registerDataSource.register(credentials = credentials)
                emit(ApiResult.Success(explorer))
            } catch (ex: Exception) {
                emit(ApiResult.Error(ex))
            }
        }.flowOn(Dispatchers.IO)
    }
}