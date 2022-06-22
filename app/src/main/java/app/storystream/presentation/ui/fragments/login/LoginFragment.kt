package app.storystream.presentation.ui.fragments.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import app.storystream.R
import app.storystream.databinding.FragmentLoginBinding
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
    }

    private fun subscribeToObservers() {

    }

    private fun initClickListeners() {
        binding.btnNoAccountYet.setOnClickListener(this)
        binding.btnLogin.setOnClickListener(this)
        binding.btnSignInWithGoogle.setOnClickListener(this)
        binding.btnSignInWithFacebook.setOnClickListener(this)
    }

    private fun signInWithEmailAndPassword() {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        loginViewModel.signInWithEmailAndPassword(email, password)
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