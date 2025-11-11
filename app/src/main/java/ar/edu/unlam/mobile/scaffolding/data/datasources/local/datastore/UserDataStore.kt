package ar.edu.unlam.mobile.scaffolding.data.datasources.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val USERS_PREFERENCES_NAME = "users_prefs"
private val Context.userDataStore: DataStore<Preferences> by preferencesDataStore(
    name = USERS_PREFERENCES_NAME,
)

class UserDataStore(
    private val context: Context,
) {
    // private val usersKey = stringPreferencesKey("users_json")
    private val rememberedUserKey = stringPreferencesKey("remembered_user")

    private val usersTokens = stringPreferencesKey("users_tokens")

    suspend fun saveUserToken(token: String) {
        context.userDataStore.edit { prefs ->
            prefs[usersTokens] = token
        }
    }

    suspend fun deleteUserToken() {
        context.userDataStore.edit { prefs ->
            prefs.remove(usersTokens)
        }
    }

    fun getUserToken(): Flow<String?> =
        context.userDataStore.data.map { prefs ->
            prefs[usersTokens]
        }

    suspend fun setRememberedUser(email: String) {
        context.userDataStore.edit { preferences ->
            preferences[rememberedUserKey] = email
        }
    }

    val rememberedUser: Flow<String?> =
        context.userDataStore.data.map { preferences ->
            preferences[rememberedUserKey]
        }

    suspend fun clearRememberedUser() {
        context.userDataStore.edit { preferences ->
            preferences.remove(rememberedUserKey)
        }
    }

    /*private suspend fun getUsers(): JSONArray {
        val usersJson = context.userDataStore.data.first()[usersKey]
        return if (usersJson != null) JSONArray(usersJson) else JSONArray()
    }

    suspend fun saUser(
        email: String,
        password: String,
    ) {
        context.userDataStore.edit { prefs ->
            val usersArray = getUsers()
            var userExists = false
            for (i in 0 until usersArray.length()) {
                val user = usersArray.getJSONObject(i)
                if (user.getString("email") == email) {
                    user.put("password", password) // Update password
                    userExists = true
                    break
                }
            }

            if (!userExists) {
                val newUser =
                    JSONObject().apply {
                        put("email", email)
                        put("password", password)
                    }
                usersArray.put(newUser)
            }
            prefs[usersKey] = usersArray.toString()
        }
    }

    suspend fun userExists(email: String): Boolean {
        val usersArray = getUsers()
        for (i in 0 until usersArray.length()) {
            if (usersArray.getJSONObject(i).getString("email") == email) {
                return true
            }
        }
        return false
    }

    suspend fun validateUser(
        email: String,
        password: String,
    ): Boolean {
        val usersArray = getUsers()
        for (i in 0 until usersArray.length()) {
            val user = usersArray.getJSONObject(i)
            if (user.getString("email") == email && user.getString("password") == password) {
                return true
            }
        }
        return false
    }*/
}
