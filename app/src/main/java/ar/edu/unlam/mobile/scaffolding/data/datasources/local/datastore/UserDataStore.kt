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
}
