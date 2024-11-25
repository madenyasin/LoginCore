package com.yasinmaden.logincore.domain.repository

import android.content.Context
import com.yasinmaden.logincore.common.Resource
import com.yasinmaden.logincore.domain.model.User
import kotlinx.coroutines.flow.StateFlow

interface AuthRepository {
    val authStateFlow: StateFlow<Boolean>
    fun addAuthStateListener()
    fun removeAuthStateListener()

    fun isUserLoggedIn(): Boolean

    suspend fun signInWithEmailAndPassword(email: String, password: String): Resource<User>

    suspend fun signUpWithEmailAndPassword(
        email: String,
        password: String,
        confirmPassword: String,
        name: String
    ): Resource<User>

    suspend fun signOut(): Resource<String>

    suspend fun sendResetPasswordEmail(email: String): Resource<String>

    suspend fun signInWithGoogle(activityContext: Context): Resource<User>
}