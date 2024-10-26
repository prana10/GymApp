package com.example.gymapp.app.domain.usecase
import com.example.gymapp.app.domain.repository.UserRepository

class LogoutUseCase(private val userRepository: UserRepository) {
    operator fun invoke() {
        userRepository.logout()
    }
}
