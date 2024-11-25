package com.yasinmaden.logincore.di

import com.yasinmaden.logincore.domain.repository.AuthRepository
import com.yasinmaden.logincore.presentation.global.ObserveAuthStateUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideObserveAuthStateUseCase(authRepository: AuthRepository): ObserveAuthStateUseCase {
        return ObserveAuthStateUseCase(authRepository)
    }

}