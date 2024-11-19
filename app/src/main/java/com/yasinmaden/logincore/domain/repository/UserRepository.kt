package com.yasinmaden.logincore.domain.repository

import com.yasinmaden.logincore.common.Resource
import com.yasinmaden.logincore.domain.model.User

interface UserRepository {
    suspend fun saveUser(user: User): Resource<String>
    suspend fun getCurrentUser(): Resource<User>

}