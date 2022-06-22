package app.storystream.domain.screenstates

data class RegisterScreenState(
    var successful: Boolean = false,
    var emailErrorMessage: String? = null,
    var passwordErrorMessage: String? = null,
    var repeatPasswordErrorMessage: String? = null
)
