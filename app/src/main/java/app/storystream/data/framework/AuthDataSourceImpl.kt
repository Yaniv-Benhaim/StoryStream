package app.storystream.data.framework

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.storystream.domain.enums.EmailStatus
import app.storystream.domain.enums.PasswordStatus
import app.storystream.domain.utils.ValidationUtil
import app.storystream.data.datasources.AuthDataSource
import app.storystream.data.results.LoginResult
import app.storystream.domain.screenstates.LoginScreenState


class AuthDataSourceImpl : AuthDataSource {




    override fun signInWithEmailAndPassword(email: String, password: String): LiveData<LoginScreenState> {

        val loginScreenState = MutableLiveData(LoginScreenState())


        when(ValidationUtil.validateEmail(email)) {
            EmailStatus.BLANK -> loginScreenState.value?.emailErrorMessage = ""
            EmailStatus.BAD -> loginScreenState.value?.emailErrorMessage = ""
        }

        when(ValidationUtil.validatePassword(password)) {
            PasswordStatus.BAD -> loginScreenState.value?.passwordErrorMessage = ""
            PasswordStatus.BLANK -> loginScreenState.value?.passwordErrorMessage = ""
            PasswordStatus.WRONG_CHARACTERS -> loginScreenState.value?.passwordErrorMessage = ""
            else -> {/*NO-OP*/}
        }


//        auth.createUserWithEmailAndPassword(email, password)
//            .addOnCompleteListener(this) { task ->
//                if (task.isSuccessful) {
//                    // Sign in success, update UI with the signed-in user's information
//                    //Log.d(TAG, "createUserWithEmail:success")
//                   // val user = auth.currentUser
//                    //updateUI(user)
//                } else {
//                    // If sign in fails, display a message to the user.
////                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
////                    Toast.makeText(baseContext, "Authentication failed.",
////                        Toast.LENGTH_SHORT).show()
////                    updateUI(null)
//                }
//            }


        return loginScreenState
    }

    override fun signOut(): Boolean {
        return true
    }
}