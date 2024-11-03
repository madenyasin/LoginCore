package com.yasinmaden.logincore.repository

import android.content.Context
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.yasinmaden.logincore.R
import com.yasinmaden.logincore.common.Resource
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.tasks.await
import java.security.MessageDigest
import java.util.UUID
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val credentialManager: CredentialManager,
) {
    fun isUserLoggedIn(): Boolean = auth.currentUser != null

    suspend fun signIn(email: String, password: String): Resource<String> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            Resource.Success(result.user?.uid.orEmpty())
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    suspend fun signUp(
        email: String,
        password: String,
        confirmPassword: String,
        name: String
    ): Resource<String> {
        if (password != confirmPassword) {
            return Resource.Error(Exception("Passwords do not match"))
        }
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val uid = result.user?.uid.orEmpty()

            val saveResult = saveUserToFirestore(name, email)
            if (saveResult is Resource.Error) return saveResult

            Resource.Success(uid)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    suspend fun logout(): Resource<String> {
        return try {
            auth.signOut()

            credentialManager.clearCredentialState(
                ClearCredentialStateRequest(
                    ClearCredentialStateRequest.TYPE_CLEAR_RESTORE_CREDENTIAL
                )
            )
            Resource.Success("Logged out successfully")
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    suspend fun sendResetPasswordEmail(email: String): Resource<String> {
        return try {
            auth.sendPasswordResetEmail(email).await()
            Resource.Success("Password reset email sent")
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    suspend fun signInWithGoogle(activityContext: Context): Resource<String> {

        val hashedNonce = generateHashedNonce()
        val request = buildGoogleSignInRequest(
            nonce = hashedNonce,
            context = activityContext
        )

        return try {
            val googleIdToken = getGoogleIdToken(
                request = request,
                activityContext = activityContext
            )
            val firebaseCredential = GoogleAuthProvider.getCredential(googleIdToken, null)
            val authResult = auth.signInWithCredential(firebaseCredential).await()

            val saveResult = saveUserToFirestore()
            if (saveResult is Resource.Error) return saveResult

            Resource.Success(authResult.user?.uid.orEmpty())
        } catch (e: CancellationException) {
            Resource.Error(e)
        } catch (e: Exception) {
            Resource.Error(e)
        }

    }

    private suspend fun saveUserToFirestore(
        name: String,
        email: String
    ): Resource<String> {
        return try {
            val userMap = hashMapOf(
                "name" to name,
                "email" to email
            )
            firestore.collection("users").document(auth.currentUser?.uid.orEmpty()).set(userMap)
                .await()
            Resource.Success("Saved to Firestore")
        } catch (e: Exception) {
            Resource.Error(e)
        }


    }

    private suspend fun saveUserToFirestore(): Resource<String> {

        return try {
            val currentUser = auth.currentUser!!
            val userMap = hashMapOf(
                "name" to currentUser.displayName,
                "email" to currentUser.email,
                "photoUrl" to currentUser.photoUrl?.toString(),
            )
            firestore.collection("users").document(auth.currentUser?.uid.orEmpty()).set(userMap)
                .await()
            Resource.Success("Saved to Firestore")
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    private suspend fun getGoogleIdToken(
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

    private fun generateHashedNonce(): String {

        val nonce = UUID.randomUUID().toString()
        val digest = MessageDigest.getInstance("SHA-256").digest(nonce.toByteArray())
        return digest.joinToString("") { "%02x".format(it) }
    }

    private fun buildGoogleSignInRequest(
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

}