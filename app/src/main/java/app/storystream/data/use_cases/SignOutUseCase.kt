package app.storystream.data.use_cases

import app.storystream.domain.repositories.AuthRepository

class SignOutUseCase(private val authRepository: AuthRepository) {

    operator fun invoke() = authRepository.signOut()

}