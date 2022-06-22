package app.storystream.data.use_cases

import app.storystream.data.repositories.AuthRepository

class RegisterUseCase(private val authRepository: AuthRepository) {

    suspend operator fun invoke(email: String, password: String, repeatPassword: String) = authRepository.createUserWithEmailAndPassword(email, password, repeatPassword)

}