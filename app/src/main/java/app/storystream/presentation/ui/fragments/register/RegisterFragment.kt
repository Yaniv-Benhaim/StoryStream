package app.storystream.presentation.ui.fragments.register

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import app.storystream.R
import app.storystream.databinding.FragmentRegisterBinding
import app.storystream.domain.screenstates.LoginScreenState
import app.storystream.domain.screenstates.RegisterScreenState
import app.storystream.domain.viewmodels.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment(), View.OnClickListener{

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val authViewModel by viewModels<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClickListeners()
        initRepeatPasswordListener()
        subscribeToObservers()
        setTextStyling()
    }

    private fun setTextStyling() {
        val mBold = StyleSpan(Typeface.BOLD)
        val spannableString = SpannableString(binding.btnHaveAccount.text.toString())
        spannableString.setSpan(mBold, 24, 32, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(ForegroundColorSpan(Color.WHITE), 24, 32, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.btnHaveAccount.text = spannableString
    }

    private fun subscribeToObservers() {
        authViewModel.registerScreenState.observe(viewLifecycleOwner) {
            stopLoading()
            if (it.successful) {
                registerSuccessful()
            } else {
                showErrorMessages(it)
            }
        }
    }

    private fun registerSuccessful() {
        findNavController().navigate(R.id.action_registerFragment_to_navigation_home)
    }

    private fun showErrorMessages(screenState: RegisterScreenState) {

        if (screenState.repeatPasswordErrorMessage != null) {
            binding.tvRepeatPasswordError.text = screenState.repeatPasswordErrorMessage
            binding.passwordRepeatCheck.isVisible = false
            binding.etRepeatPassword.requestFocus()
        } else {
            binding.tvRepeatPasswordError.text = ""
            binding.passwordRepeatCheck.isVisible = true
        }

        if(screenState.emailErrorMessage != null) {
            binding.tvEmailError.text = screenState.emailErrorMessage
            binding.emailCheck.isVisible = false
            binding.etEmail.requestFocus()
        } else {
            binding.tvEmailError.text = ""
            binding.emailCheck.isVisible = false
        }

        if(screenState.passwordErrorMessage != null) {
            binding.tvPasswordError.text = screenState.passwordErrorMessage
            binding.passwordCheck.isVisible = true
            binding.etPassword.requestFocus()
        } else {
            binding.tvPasswordError.text = ""
            binding.passwordCheck.isVisible = true
        }
    }

    private fun initClickListeners() {
        binding.btnHaveAccount.setOnClickListener(this)
        binding.btnBack.setOnClickListener(this)
        binding.btnRegister.setOnClickListener(this)
    }

    private fun initRepeatPasswordListener() = binding.etRepeatPassword.addTextChangedListener {
        val password = binding.etPassword.text.toString()
        val repeatPassword = it.toString()
        authViewModel.validateRepeatPassword(password, repeatPassword)
    }

    private fun startLoading() {
        binding.loadingView.isVisible = true
    }

    private fun stopLoading() {
        binding.loadingView.isVisible = false
    }

    private fun registerWithEmailAndPassword() {
        startLoading()
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        val repeatPassword = binding.etRepeatPassword.text.toString()
        authViewModel.createUserWithEmailAndPassword(email, password, repeatPassword)
    }

    override fun onClick(view: View?) {
        view?.let { button ->
            when(button.id) {
                binding.btnHaveAccount.id -> findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                binding.btnRegister.id -> registerWithEmailAndPassword()
                binding.btnBack.id -> requireActivity().onBackPressed()
            }
        }
    }

}