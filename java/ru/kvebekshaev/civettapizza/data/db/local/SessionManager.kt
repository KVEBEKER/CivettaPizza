package ru.kvebekshaev.civettapizza.data.db.local

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey



class SessionManager(context: Context) {
    var masterKey: MasterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    var sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
        context,
        "secret_shared_prefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
    companion object{
        const val KEY_USER_ID: String = "user_id"
        const val KEY_USERNAME: String = "username"
        const val KEY_EMAIL: String = "email"
        const val KEY_PHONE: String = "phonenumber"
        const val KEY_PASSWORD: String = "password"
        const val KEY_IS_LOGGED_IN: String = "is_logged_in"
    }
    // use the shared preferences and editor as you normally would
    var editor: SharedPreferences.Editor = sharedPreferences.edit()
}