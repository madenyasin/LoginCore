package com.yasinmaden.logincore.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.yasinmaden.logincore.common.Resource
import com.yasinmaden.logincore.data.mapper.toDomain
import com.yasinmaden.logincore.data.mapper.toEntity
import com.yasinmaden.logincore.data.model.UserEntity
import com.yasinmaden.logincore.domain.model.User
import com.yasinmaden.logincore.domain.repository.UserRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth,
) : UserRepository {

    override suspend fun saveUser(user: User): Resource<String> {
        return try {
            val userEntity = user.toEntity()
            firestore.collection("users")
                .document(userEntity.uid)
                .set(userEntity)
                .await()

            Resource.Success("Saved to Firestore")
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    override suspend fun getCurrentUser(): Resource<User> {
        return try {
            val currentUser = auth.currentUser
            if (currentUser != null) {
                val documentSnapshot = firestore.collection("users")
                    .document(currentUser.uid)
                    .get()
                    .await()

                val userEntity = documentSnapshot.toObject(UserEntity::class.java)

                if (userEntity != null) {
                    val user = userEntity.toDomain()
                    Resource.Success(user)
                } else {
                    Resource.Error(Exception("User not found"))
                }
            } else {
                Resource.Error(Exception("User not logged in"))
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
}