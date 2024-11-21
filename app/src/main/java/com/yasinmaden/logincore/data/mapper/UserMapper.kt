package com.yasinmaden.logincore.data.mapper

import com.yasinmaden.logincore.data.model.UserEntity
import com.yasinmaden.logincore.domain.model.User

fun UserEntity.toDomain(): User {
    return User(
        uid = this.uid,
        name = this.name ?: "",
        email = this.email ?: "",
        photoUrl = this.photoUrl
    )
}

fun User.toEntity(): UserEntity{
    return UserEntity(
        uid = this.uid,
        name = this.name,
        email = this.email,
        photoUrl = this.photoUrl,
    )
}