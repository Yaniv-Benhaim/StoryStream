package app.storystream.data.datasources

import androidx.lifecycle.LiveData
import app.storystream.data.results.LoginResult
import app.storystream.domain.screenstates.LoginScreenState

interface AuthDataSource {

    fun signInWithEmailAndPassword(email: String, password: String) : LiveData<LoginScreenState>

    fun signOut() : Boolean
}