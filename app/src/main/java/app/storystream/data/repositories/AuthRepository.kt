package app.storystream.data.repositories

import app.storystream.data.datasources.AuthDataSource

class AuthRepository(private val authDataSource: AuthDataSource) {

    suspend fun signInWithEmailAndPassword(email: String, password: String) = authDataSource.signInWithEmailAndPassword(email, password)

    fun signOut() = authDataSource.signOut()

    suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String,
        repeatPassword: String
    ) = authDataSource.createUserWithEmailAndPassword(email, password, repeatPassword)

    fun validateRepeatPassword(password: String, repeatPassword: String) = authDataSource.validateRepeatPassword(password, repeatPassword)
    suspend fun isSignedIn() = authDataSource.isSignedIn()
}