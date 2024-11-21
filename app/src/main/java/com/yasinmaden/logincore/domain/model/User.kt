package com.yasinmaden.logincore.domain.model

data class User(
    val uid: String,
    val name: String,
    val email: String,
    val photoUrl: String?,
)