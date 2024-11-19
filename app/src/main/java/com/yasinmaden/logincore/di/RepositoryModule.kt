package com.yasinmaden.logincore.di

import com.yasinmaden.logincore.data.repository.AuthRepositoryImpl
import com.yasinmaden.logincore.data.repository.UserRepositoryImpl
import com.yasinmaden.logincore.domain.repository.AuthRepository
import com.yasinmaden.logincore.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {


    @Binds
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    abstract fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository
}