package app.storystream.data.use_cases

import app.storystream.domain.repositories.AuthRepository

class SignInUseCase(private val authRepository: AuthRepository) {

    suspend operator fun invoke(email: String, password: String) = authRepository.signInWithEmailAndPassword(email, password)

}