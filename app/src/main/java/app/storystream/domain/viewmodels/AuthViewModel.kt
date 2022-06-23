package app.storystream.domain.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.storystream.data.use_cases.*
import app.storystream.domain.screenstates.LoginScreenState
import app.storystream.domain.screenstates.RegisterScreenState
import app.storystream.domain.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val signOutUseCase: SignOutUseCase,
    private val registerUseCase: RegisterUseCase,
    private val validationUseCase: RepeatPasswordValidationUseCase,
    private val isLoggedInUseCase: IsLoggedInUseCase
) : ViewModel() {

    private val _loginScreenState = MutableLiveData<LoginScreenState>()
    val loginScreenState: LiveData<LoginScreenState> = _loginScreenState

    private val _registerScreenState = MutableLiveData<RegisterScreenState>()
    val registerScreenState: LiveData<RegisterScreenState> = _registerScreenState

    private val _isLoggedIn = MutableLiveData<Boolean>()
    val isLoggedIn: LiveData<Boolean> = _isLoggedIn

    fun signInWithEmailAndPassword(email: String, password: String) = viewModelScope.launch {
        val screenState = signInUseCase.invoke(email, password)
        _loginScreenState.postValue(screenState)
    }

    fun createUserWithEmailAndPassword(email: String, password: String, repeatPassword: String) = viewModelScope.launch {
        val screenState = registerUseCase.invoke(email, password, repeatPassword)
        _registerScreenState.postValue(screenState)
    }

    fun validateRepeatPassword(password: String, repeatPassword: String) = viewModelScope.launch {
        val screenState = validationUseCase.invoke(password, repeatPassword)
        _registerScreenState.postValue(screenState)
    }

    fun signOut() = signOutUseCase.invoke()

    fun isSignedIn() = viewModelScope.launch {
        _isLoggedIn.postValue(isLoggedInUseCase.invoke())
    }
}