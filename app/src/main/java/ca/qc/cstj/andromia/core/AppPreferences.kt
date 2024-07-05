package ca.qc.cstj.andromia.core

import android.content.Context
import android.content.SharedPreferences

class AppPreferences(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREFS_NAME = "MyAppPreferences"
        private const val FUNCTION_EXECUTED_KEY = "function_executed"
    }

    fun isFunctionExecuted(): Boolean {
        return sharedPreferences.getBoolean(FUNCTION_EXECUTED_KEY, false)
    }

    fun setFunctionExecutedFlag(value: Boolean) {
        sharedPreferences.edit().putBoolean(FUNCTION_EXECUTED_KEY, value).apply()
    }
}
