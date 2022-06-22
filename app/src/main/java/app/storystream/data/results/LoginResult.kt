package app.storystream.data.results

import app.storystream.domain.models.User

data class LoginResult(
    var success: Boolean = false,
    var emailError: Boolean = false,
    var passwordError: Boolean = false,
    var connectionError: Boolean = false,
    var user: User? = null
) {
}