package br.com.amparocuidado.data.utils

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import br.com.amparocuidado.domain.model.User
import com.google.gson.Gson
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserManager @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    private val gson = Gson()

    private object PreferenceKeys {
        val USER_JSON = stringPreferencesKey("user_json")
    }

    val userFlow = dataStore.data.map { preferences ->
        preferences[PreferenceKeys.USER_JSON]?.let { json ->
            gson.fromJson(json, User::class.java)
        }
    }

    suspend fun getUser(): User? {
        return userFlow.first()
    }

    suspend fun saveUser(user: User) {
        val json = gson.toJson(user)
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.USER_JSON] = json
        }
    }

    suspend fun clearUser() {
        dataStore.edit { preferences ->
            preferences.remove(PreferenceKeys.USER_JSON)
        }
    }
}
