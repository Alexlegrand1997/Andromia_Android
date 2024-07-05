package ca.qc.cstj.andromia.data.repositories

import android.util.Log
import ca.qc.cstj.andromia.core.ApiResult
import ca.qc.cstj.andromia.data.datasources.AllyDataSource
import ca.qc.cstj.andromia.data.datasources.ExplorationDataSource
import ca.qc.cstj.andromia.models.Ally
import ca.qc.cstj.andromia.models.Exploration
import ca.qc.cstj.andromia.models.InfoExploration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ExplorationRepository {

    private val _explorationDataSource = ExplorationDataSource()

    fun retrieveOne(accessToken:String,qrCode: String) : Flow<ApiResult<Exploration>> {
        return flow {
            emit(ApiResult.Loading)
            try {
                emit(ApiResult.Success(_explorationDataSource.getOne(accessToken,qrCode)))
            } catch (ex: Exception) {
                emit(ApiResult.Error(ex))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun retrieveAll(accessToken:String) : Flow<ApiResult<List<InfoExploration>>> {
        return flow {
            emit(ApiResult.Loading)
            try {

                emit(ApiResult.Success(_explorationDataSource.getAll(accessToken)))
            } catch (ex: Exception) {
                emit(ApiResult.Error(ex))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun captureOne(accessToken: String,captureMe:String ): Flow<ApiResult<Ally>> {
        return flow {
            emit(ApiResult.Loading)
            try {
                emit(ApiResult.Success(_explorationDataSource.captureOne(accessToken,captureMe)))
            } catch (ex: Exception) {
                emit(ApiResult.Error(ex))
            }
        }.flowOn(Dispatchers.IO)
    }

}