package ru.kvebekshaev.civettapizza.data.repositories

import android.content.Context
import ru.kvebekshaev.civettapizza.data.db.local.SessionManager
import ru.kvebekshaev.civettapizza.domain.repositories.SessionRepository

class SessionRepositoryImpl(private val context: Context): SessionRepository{
    private val sessionManager = SessionManager(context)
    override fun saveUserId(id: Int) {
        sessionManager.editor.putInt(SessionManager.KEY_USER_ID,id)
        sessionManager.editor.apply()
    }

    override fun getUserId(): Int {
        val result: Int = sessionManager.sharedPreferences.getInt(SessionManager.KEY_USER_ID,-1)
        if (result != -1){
            return result
        } else{
            return 0
        }
    }

    override fun saveUsername(username: String) {
        sessionManager.editor.putString(SessionManager.KEY_USERNAME,username)
        sessionManager.editor.apply()
    }

    override fun getUsername(): String {
        val result: String? = sessionManager.sharedPreferences.getString(SessionManager.KEY_USERNAME,null)
        if (result != null){
            return result
        } else{
            return "Error"
        }
    }

    override fun saveEmail(email: String) {
        sessionManager.editor.putString(SessionManager.KEY_EMAIL,email)
        sessionManager.editor.apply()
    }

    override fun getEmail(): String {
        val result: String? = sessionManager.sharedPreferences.getString(SessionManager.KEY_EMAIL,null)
        if (result != null){
            return result
        } else{
            return "Error"
        }
    }

    override fun savePhone(phone: String) {
        sessionManager.editor.putString(SessionManager.KEY_PHONE,phone)
        sessionManager.editor.apply()
    }

    override fun getPhone(): String {
        val result: String? = sessionManager.sharedPreferences.getString(SessionManager.KEY_PHONE,null)
        if (result != null){
            return result
        } else{
            return "Error"
        }
    }


    override fun saveLogIn(isLoggedIn: Boolean) {
        sessionManager.editor.putBoolean(SessionManager.KEY_IS_LOGGED_IN,isLoggedIn)
        sessionManager.editor.apply()
    }

    override fun getLogIn(): Boolean {
        val result: Boolean = sessionManager.sharedPreferences.getBoolean(SessionManager.KEY_IS_LOGGED_IN,false)
        return result
    }

}