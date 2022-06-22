package app.storystream.domain.utils

import android.os.PatternMatcher
import app.storystream.domain.enums.EmailStatus
import app.storystream.domain.enums.PasswordStatus
import app.storystream.domain.utils.Constants.EMAIL_REGEX

object ValidationUtil {

    fun validateEmail(email: String) : EmailStatus {

        if(email.isBlank()) {
            return EmailStatus.BLANK
        }

        if (!EMAIL_REGEX.toRegex().matches(email)) {
            return EmailStatus.BAD
        }

        return EmailStatus.GOOD
    }

    fun validatePassword(password: String) : PasswordStatus {
        if (password.isBlank()) {
            return PasswordStatus.BLANK
        }
        if(password.length < 6) {
            return PasswordStatus.TO_SHORT
        }
        return PasswordStatus.GOOD
    }

    fun validateRepeatPassword(password: String, repeatPassword: String) : PasswordStatus {
        if (password != repeatPassword) {
            return PasswordStatus.NO_MATCH
        }
        if (password.isBlank() || repeatPassword.isBlank()) {
            return PasswordStatus.BLANK
        }
        return PasswordStatus.GOOD
    }
}