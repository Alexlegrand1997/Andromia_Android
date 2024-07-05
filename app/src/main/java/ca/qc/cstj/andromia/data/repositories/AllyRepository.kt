package ca.qc.cstj.andromia.data.repositories

import ca.qc.cstj.andromia.core.ApiResult
import ca.qc.cstj.andromia.data.datasources.AllyDataSource
import ca.qc.cstj.andromia.data.datasources.ProfileDataSource
import ca.qc.cstj.andromia.models.Ally
import ca.qc.cstj.andromia.models.Explorer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class AllyRepository {

    private val _allyDataSource = AllyDataSource()

    fun retrieveOne(accessToken: String) : Flow<ApiResult<Ally>> {
        return flow {
            emit(ApiResult.Loading)
            try {
                emit(ApiResult.Success(_allyDataSource.getOne(accessToken)))
            } catch (ex: Exception) {
                emit(ApiResult.Error(ex))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun retrieveAll(href: String) : Flow<ApiResult<List<Ally>>> {
        return flow {
            emit(ApiResult.Loading)
            try {
                emit(ApiResult.Success(_allyDataSource.getAll(href)))
            } catch (ex: Exception) {
                emit(ApiResult.Error(ex))
            }
        }.flowOn(Dispatchers.IO)
    }
}