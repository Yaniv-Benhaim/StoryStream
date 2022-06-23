package app.storystream.presentation.ui.fragments.splash

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import app.storystream.R
import app.storystream.databinding.FragmentLibraryBinding
import app.storystream.databinding.FragmentSplashBinding
import app.storystream.domain.utils.Constants.MINIMUM_DATA_SIZE
import app.storystream.domain.viewmodels.AuthViewModel
import app.storystream.domain.viewmodels.MediaViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : Fragment() {
    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!
    private val loginViewModel by viewModels<AuthViewModel>()
    private val mediaViewModel by activityViewModels<MediaViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeToObservers()
        initializeData()
    }

    private fun initializeData() {
        mediaViewModel.getAllBooksNetworkRequest()
        mediaViewModel.getAllBooksFromLocalStorage()
    }

    private fun subscribeToObservers() {
        loginViewModel.isLoggedIn.observe(viewLifecycleOwner) { signedIn ->
            if(signedIn) {
                loginSuccessful()
            } else {
                loginUnSuccessful()
            }
        }

        mediaViewModel.allBooks.observe(viewLifecycleOwner) {
            if(it != null) {
                if (it.size > MINIMUM_DATA_SIZE) {
                    loginViewModel.isSignedIn()
                }
            }
        }
    }

    private fun loginSuccessful() {
        findNavController().navigate(R.id.action_splashFragment_to_navigation_home)
    }

    private fun loginUnSuccessful() {
        findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}