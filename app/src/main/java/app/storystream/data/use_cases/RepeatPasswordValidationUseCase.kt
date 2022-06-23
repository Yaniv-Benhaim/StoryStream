package app.storystream.data.use_cases

import app.storystream.domain.repositories.AuthRepository

class RepeatPasswordValidationUseCase(private val authRepository: AuthRepository) {

    operator fun invoke(password: String, repeatPassword: String) = authRepository.validateRepeatPassword(password, repeatPassword)

}