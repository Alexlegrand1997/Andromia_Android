package ca.qc.cstj.andromia.data.repositories

import ca.qc.cstj.andromia.core.ApiResult
import ca.qc.cstj.andromia.data.datasources.BoosterDataSource
import ca.qc.cstj.andromia.dto.BoosterDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class BoosterRepository {
    private val _boosterDataSource = BoosterDataSource()

    fun buyOne(href: String, accessToken: String) : Flow<ApiResult<BoosterDTO>> {
        return flow {
            emit(ApiResult.Loading)
            try {
                emit(ApiResult.Success(_boosterDataSource.buyOne(href, accessToken)))
            } catch (ex: Exception) {
                emit(ApiResult.Error(ex))
            }
        }.flowOn(Dispatchers.IO)
    }
}