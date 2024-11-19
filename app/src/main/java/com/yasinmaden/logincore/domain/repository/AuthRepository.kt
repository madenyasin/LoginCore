package com.yasinmaden.logincore.domain.repository

import android.content.Context
import com.yasinmaden.logincore.common.Resource
import com.yasinmaden.logincore.domain.model.User

interface AuthRepository {
    fun isUserLoggedIn(): Boolean

    suspend fun signIn(email: String, password: String): Resource<User>

    suspend fun signUp(
        email: String,
        password: String,
        confirmPassword: String,
        name: String
    ): Resource<User>

    suspend fun logout(): Resource<String>

    suspend fun sendResetPasswordEmail(email: String): Resource<String>

    suspend fun signInWithGoogle(activityContext: Context): Resource<User>
}