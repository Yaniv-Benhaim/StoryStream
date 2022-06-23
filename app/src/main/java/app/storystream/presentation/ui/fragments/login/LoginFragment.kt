package app.storystream.presentation.ui.fragments.login

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import app.storystream.R
import app.storystream.databinding.FragmentLoginBinding
import app.storystream.domain.screenstates.LoginScreenState
import app.storystream.domain.viewmodels.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.sign

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login), View.OnClickListener {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val loginViewModel by viewModels<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClickListeners()
        subscribeToObservers()
        setTextStyling()
    }

    private fun subscribeToObservers() {
        loginViewModel.loginScreenState.observe(viewLifecycleOwner) { screenState ->
            stopLoading()
            if (screenState.successful) {
                loginSuccessful()
            } else {
                showErrorMessages(screenState)
            }
        }

        loginViewModel.isLoggedIn.observe(viewLifecycleOwner) { signedIn ->
            if(signedIn) { loginSuccessful() }
        }
    }

    private fun setTextStyling() {
        val mBold = StyleSpan(Typeface.BOLD)
        val spannableString = SpannableString(binding.btnNoAccountYet.text.toString())
        spannableString.setSpan(mBold, 23, 30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(ForegroundColorSpan(Color.WHITE), 23, 30, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.btnNoAccountYet.text = spannableString
    }

    private fun loginSuccessful() {
        findNavController().navigate(R.id.action_loginFragment_to_navigation_home)
    }

    private fun showErrorMessages(screenState: LoginScreenState) {

        if(screenState.passwordErrorMessage != null) {
            binding.tvPasswordError.text = screenState.passwordErrorMessage
            binding.etPassword.requestFocus()
        } else { binding.tvPasswordError.text = "" }

        if(screenState.emailErrorMessage != null) {
            binding.tvEmailError.text = screenState.emailErrorMessage
            binding.etEmail.requestFocus()
        } else { binding.tvEmailError.text = "" }

        if(screenState.invalidUserError != null) {
            Toast.makeText(requireContext(), "${screenState.invalidUserError}", Toast.LENGTH_SHORT).show()
            binding.etEmail.requestFocus()
        }
    }

    private fun initClickListeners() {
        binding.btnNoAccountYet.setOnClickListener(this)
        binding.btnLogin.setOnClickListener(this)
        binding.btnSignInWithGoogle.setOnClickListener(this)
        binding.btnSignInWithFacebook.setOnClickListener(this)
    }

    private fun signInWithEmailAndPassword() {
        startLoading()
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        loginViewModel.signInWithEmailAndPassword(email, password)
    }

    private fun startLoading() {
        binding.loadingView.isVisible = true
    }

    private fun stopLoading() {
        binding.loadingView.isVisible = false
    }

    override fun onClick(view: View?) {
        view?.let { button ->
            when(button.id) {
                binding.btnNoAccountYet.id -> findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
                binding.btnLogin.id -> signInWithEmailAndPassword()
                else -> {}
            }
        }
    }

}