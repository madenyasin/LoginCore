package com.yasinmaden.logincore.presentation.global

import com.yasinmaden.logincore.domain.repository.AuthRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class ObserveAuthStateUseCase @Inject constructor(
    authRepository: AuthRepository
) {
    val authStateFlow: StateFlow<Boolean> = authRepository.authStateFlow
}