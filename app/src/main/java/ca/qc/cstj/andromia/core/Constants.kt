package ca.qc.cstj.andromia.core

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

object Constants {
//    private const val BASE_API = "http://10.0.2.2:3002"
    private const val BASE_API = "http://projectaaz.ddnsfree.com:9000"
    const val LOGIN_URL = "$BASE_API/explorateurs/login"
    const val ALLY_URL ="$BASE_API/allies"
    const val EXPLORATION_URL="$BASE_API/explorations"
    const val REGISTER_URL="$BASE_API/explorateurs"
    const val REFRESH_TOKEN_URL="$BASE_API/explorateurs/refresh"

    val Context.dataStore : DataStore<Preferences> by preferencesDataStore(name = "andromia-datastore")

    object RefreshDelay {
        const val PROFILE_REFRESH_DELAY = (60000L * 5)
        const val TOKEN_REFRESH_DELAY = (60000L * 60)
    }

}