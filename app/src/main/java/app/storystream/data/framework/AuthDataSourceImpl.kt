package app.storystream.data.framework

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.storystream.domain.enums.EmailStatus
import app.storystream.domain.enums.PasswordStatus
import app.storystream.domain.utils.ValidationUtil
import app.storystream.data.datasources.AuthDataSource
import app.storystream.data.results.LoginResult
import app.storystream.domain.screenstates.LoginScreenState
import app.storystream.domain.screenstates.RegisterScreenState
import app.storystream.domain.utils.Constants.AUTH_TAG
import app.storystream.presentation.ui.activities.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import kotlin.math.log


class AuthDataSourceImpl() : AuthDataSource {

    private val auth = FirebaseAuth.getInstance()


    override suspend fun signInWithEmailAndPassword(email: String, password: String): LoginScreenState {

        val loginScreenState = LoginScreenState()

        when(ValidationUtil.validateEmail(email)) {
            EmailStatus.BLANK -> loginScreenState.emailErrorMessage = "Email field can't be empty"
            EmailStatus.BAD -> loginScreenState.emailErrorMessage = "Please enter a correct email address"
            else -> {/*NO-OP*/}
        }

        when(ValidationUtil.validatePassword(password)) {
            PasswordStatus.TO_SHORT -> loginScreenState.passwordErrorMessage = "Password incorrect"
            PasswordStatus.BLANK -> loginScreenState.passwordErrorMessage = "Password field can't be empty"
            else -> {/*NO-OP*/}
        }


        if (loginScreenState.emailErrorMessage == null && loginScreenState.passwordErrorMessage == null) {
            return try {
                val result = auth.signInWithEmailAndPassword(email, password).await()
                loginScreenState.successful = result.user != null
                loginScreenState
            } catch (e: FirebaseAuthInvalidUserException) {
                loginScreenState.invalidUserError = "No user has been registered using this email, please register an account"
                loginScreenState
            } catch (e: Exception) {
                loginScreenState.successful = false
                loginScreenState
            }
        }
        Log.d(AUTH_TAG, "return end: emailError: ${loginScreenState.emailErrorMessage} \n passwordError: ${loginScreenState.passwordErrorMessage}")
        return loginScreenState
    }

    override fun signOut() {
        auth.signOut()
    }

    override suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String,
        repeatPassword: String
    ): RegisterScreenState {
        val registerScreenState = RegisterScreenState()

        when(ValidationUtil.validateEmail(email)) {
            EmailStatus.BLANK -> registerScreenState.emailErrorMessage = "Email field can't be empty"
            EmailStatus.BAD -> registerScreenState.emailErrorMessage = "Please enter a correct email address"
            else -> {/*NO-OP*/}
        }

        when(ValidationUtil.validatePassword(password)) {
            PasswordStatus.TO_SHORT -> registerScreenState.passwordErrorMessage = "Password incorrect"
            PasswordStatus.BLANK -> registerScreenState.passwordErrorMessage = "Password field can't be empty"
            else -> {/*NO-OP*/}
        }

        when(ValidationUtil.validateRepeatPassword(password, repeatPassword)) {
            PasswordStatus.NO_MATCH -> registerScreenState.repeatPasswordErrorMessage = "Your passwords must match."
            PasswordStatus.BLANK -> registerScreenState.repeatPasswordErrorMessage = "Your password must match."
            else -> {/*NO-OP*/}
        }

        if (registerScreenState.emailErrorMessage == null && registerScreenState.passwordErrorMessage == null && registerScreenState.repeatPasswordErrorMessage == null) {
            return try {
                val result = auth.createUserWithEmailAndPassword(email, password).await()
                registerScreenState.successful = result.user != null
                registerScreenState
            } catch (e: Exception) {
                registerScreenState.successful = false
                registerScreenState
            }
        }

        return registerScreenState
    }

    override fun validateRepeatPassword(password: String, repeatPassword: String): RegisterScreenState {
        val registerScreenState = RegisterScreenState()
        when(ValidationUtil.validateRepeatPassword(password, repeatPassword)) {
            PasswordStatus.NO_MATCH -> registerScreenState.repeatPasswordErrorMessage = "Your passwords do not match."
            PasswordStatus.BLANK -> registerScreenState.repeatPasswordErrorMessage = "Make sure to fill out all the fields"
            else -> {/*NO-OP*/}
        }
        return registerScreenState
    }

    override suspend fun isSignedIn(): Boolean {
        return auth.currentUser != null
    }
}