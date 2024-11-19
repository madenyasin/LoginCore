package com.yasinmaden.logincore.utils


import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.yasinmaden.logincore.R
import java.security.MessageDigest
import java.util.UUID
import javax.inject.Inject

class GoogleSignInHelper @Inject constructor(
    private val credentialManager: CredentialManager
){
    fun generateHashedNonce(): String {
        val nonce = UUID.randomUUID().toString()
        val digest = MessageDigest.getInstance("SHA-256").digest(nonce.toByteArray())
        return digest.joinToString("") { "%02x".format(it) }
    }
    fun buildGoogleSignInRequest(
        nonce: String,
        context: Context
    ): GetCredentialRequest {
        val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(context.getString(R.string.WEB_CLIENT_ID))
            .setNonce(nonce)
            .setAutoSelectEnabled(true)
            .build()

        return GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()
    }

    suspend fun getGoogleIdToken(
        request: GetCredentialRequest,
        activityContext: Context
    ): String {
        val result = credentialManager.getCredential(
            context = activityContext,
            request = request
        )
        val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(
            result.credential.data
        )
        return googleIdTokenCredential.idToken
    }
}