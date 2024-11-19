package com.yasinmaden.logincore.data.repository

import android.content.Context
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.yasinmaden.logincore.common.Resource
import com.yasinmaden.logincore.domain.model.User
import com.yasinmaden.logincore.domain.repository.AuthRepository
import com.yasinmaden.logincore.domain.repository.UserRepository
import com.yasinmaden.logincore.utils.GoogleSignInHelper
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val credentialManager: CredentialManager,
    private val userRepository: UserRepository,
    private val googleSignInHelper: GoogleSignInHelper
) : AuthRepository {
    override fun isUserLoggedIn(): Boolean = auth.currentUser != null

    override suspend fun signIn(email: String, password: String): Resource<User> {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()

            val userResource = userRepository.getCurrentUser()
            if (userResource is Resource.Success) {
                Resource.Success(userResource.data)
            } else {
                Resource.Error(Exception("Failed to get user data"))
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    override suspend fun signUp(
        email: String,
        password: String,
        confirmPassword: String,
        name: String
    ): Resource<User> {
        if (password != confirmPassword) {
            return Resource.Error(Exception("Passwords do not match"))
        }
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()

            val user = User(
                uid = result.user?.uid.orEmpty(),
                name = name,
                email = email,
                photoUrl = result.user?.photoUrl.toString()
            )

            val saveResult = userRepository.saveUser(user)
            if (saveResult is Resource.Error) return Resource.Error(saveResult.exception)

            Resource.Success(user)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    override suspend fun logout(): Resource<String> {
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

    override suspend fun sendResetPasswordEmail(email: String): Resource<String> {
        return try {
            auth.sendPasswordResetEmail(email).await()
            Resource.Success("Password reset email sent")
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    override suspend fun signInWithGoogle(activityContext: Context): Resource<User> {
        val hashedNonce = googleSignInHelper.generateHashedNonce()
        val request = googleSignInHelper.buildGoogleSignInRequest(
            nonce = hashedNonce,
            context = activityContext
        )

        return try {
            val googleIdToken = googleSignInHelper.getGoogleIdToken(
                request = request,
                activityContext = activityContext
            )
            val firebaseCredential = GoogleAuthProvider.getCredential(googleIdToken, null)
            val authResult = auth.signInWithCredential(firebaseCredential).await()

            val currentUser = authResult.user

            if (currentUser != null) {
                val user = User(
                    uid = currentUser.uid,
                    name = currentUser.displayName ?: "",
                    email = currentUser.email ?: "",
                    photoUrl = currentUser.photoUrl.toString()
                )

                val saveResult = userRepository.saveUser(user)
                if (saveResult is Resource.Error) return Resource.Error(saveResult.exception)
                return Resource.Success(user)
            } else {
                return Resource.Error(Exception("Authentication failed"))
            }
        } catch (e: CancellationException) {
            Resource.Error(e)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
}