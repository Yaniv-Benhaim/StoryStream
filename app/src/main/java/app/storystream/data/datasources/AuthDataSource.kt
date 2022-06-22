package app.storystream.data.datasources

import app.storystream.domain.screenstates.LoginScreenState
import app.storystream.domain.screenstates.RegisterScreenState

interface AuthDataSource {

    //Sign in
    suspend fun signInWithEmailAndPassword(email: String, password: String) : LoginScreenState

    fun signOut()

    //Register
    suspend fun createUserWithEmailAndPassword(email: String, password: String, repeatPassword: String) : RegisterScreenState

    fun validateRepeatPassword(password: String, repeatPassword: String) : RegisterScreenState

    suspend fun isSignedIn() : Boolean
}