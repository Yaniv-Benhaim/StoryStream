package app.storystream.data.use_cases

import app.storystream.domain.repositories.AuthRepository

class IsLoggedInUseCase(private val authRepository: AuthRepository) {

    suspend operator fun invoke() = authRepository.isSignedIn()

}