package com.yasinmaden.logincore.di

import androidx.credentials.CredentialManager
import com.yasinmaden.logincore.utils.GoogleSignInHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GoogleSignInHelperModule {

    @Provides
    @Singleton
    fun provideGoogleSignInHelper(
        credentialManager: CredentialManager
    ): GoogleSignInHelper {
        return GoogleSignInHelper(credentialManager)
    }
}