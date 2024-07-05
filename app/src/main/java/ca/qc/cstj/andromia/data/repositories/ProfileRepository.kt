package ca.qc.cstj.andromia.data.repositories

import android.util.Log
import ca.qc.cstj.andromia.core.ApiResult
import ca.qc.cstj.andromia.core.Constants
import ca.qc.cstj.andromia.data.datasources.LoginDataSource
import ca.qc.cstj.andromia.data.datasources.ProfileDataSource
import ca.qc.cstj.andromia.models.Account
import ca.qc.cstj.andromia.models.Explorer
import ca.qc.cstj.andromia.models.Login
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ProfileRepository {

    private val _profileDataSource = ProfileDataSource()

    fun retrieveOne(href: String, accessToken: String) : Flow<ApiResult<Account>> {
        return flow {
            while (true) {
                try {
                    val explorer = _profileDataSource.getOne(href, accessToken)
                    emit(ApiResult.Success(explorer))
                } catch (ex: Exception) {
                    emit(ApiResult.Error(ex))
                }
                delay(Constants.RefreshDelay.PROFILE_REFRESH_DELAY)
            }
        }.flowOn(Dispatchers.IO)
    }

    fun refreshToken(refreshToken: String) : Flow<ApiResult<Account>> {
        return flow {
            while (true) {
                try {
                    val explorer = _profileDataSource.refreshToken(refreshToken)
                    emit(ApiResult.Success(explorer))
                } catch (ex: Exception) {
                    emit(ApiResult.Error(ex))
                }
                delay(Constants.RefreshDelay.TOKEN_REFRESH_DELAY)
            }
        }.flowOn(Dispatchers.IO)
    }

}
