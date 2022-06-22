package app.storystream.domain.screenstates

data class LoginScreenState(
    var successful: Boolean = false,
    var emailErrorMessage: String? = null,
    var passwordErrorMessage: String? = null
)