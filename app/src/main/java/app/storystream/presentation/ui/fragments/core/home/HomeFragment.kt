package app.storystream.presentation.ui.fragments.core.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import app.storystream.R
import app.storystream.databinding.FragmentHomeBinding
import app.storystream.domain.enums.MediaType
import app.storystream.domain.models.Book
import app.storystream.domain.viewmodels.AuthViewModel
import app.storystream.domain.viewmodels.MediaViewModel
import app.storystream.presentation.ui.adapters.BooksAdapter
import app.storystream.presentation.ui.adapters.CategoryAdapter
import app.storystream.presentation.ui.adapters.NewStoriesAdapter
import app.storystream.presentation.ui.fragments.details.reader.ReadingFragmentDirections
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(),
    CategoryAdapter.CategoryClickedListener,
    NewStoriesAdapter.NewStoriesClickedListener,
    BooksAdapter.BookClickedListener
{

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val authViewModel by viewModels<AuthViewModel>()
    private val mediaViewModel by activityViewModels<MediaViewModel>()
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var newStoriesAdapter: NewStoriesAdapter
    private lateinit var booksAdapter: BooksAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSignOut.setOnClickListener {
            authViewModel.signOut()
            findNavController().navigate(R.id.action_global_to_loginFragment)
        }

        subscribeToObservers()
        initCategoryAdapter()
        initNewStoriesAdapter()

        initTopRatedAdapter()

    }

    private fun subscribeToObservers() {
        mediaViewModel.allBooks.observe(viewLifecycleOwner) {
            if(!it.isNullOrEmpty()) {
                booksAdapter = BooksAdapter(this, it)
                initSelectedMediaTypeAdapter(MediaType.BOOKS)
                initOnTabSelectedListener()
            }
        }
    }

    private fun initCategoryAdapter() = binding.rvCategories.apply {
        categoryAdapter = CategoryAdapter(this@HomeFragment)
        adapter = categoryAdapter
        layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    private fun initNewStoriesAdapter() = binding.rvNewStories.apply {
        newStoriesAdapter = NewStoriesAdapter(this@HomeFragment)
        val lineaSnapHelper = PagerSnapHelper()
        lineaSnapHelper.attachToRecyclerView(this)
        adapter = newStoriesAdapter
        layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    private fun initTopRatedAdapter() = binding.rvTopRated.apply {
        newStoriesAdapter = NewStoriesAdapter(this@HomeFragment)
        val lineaSnapHelper = PagerSnapHelper()
        lineaSnapHelper.attachToRecyclerView(this)
        adapter = newStoriesAdapter
        layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    private fun initSelectedMediaTypeAdapter(mediaType: MediaType) = binding.rvSelectedMediaType.apply {
        adapter = when(mediaType) {
            MediaType.BOOKS -> booksAdapter
            MediaType.AUDIO -> NewStoriesAdapter(this@HomeFragment)
            MediaType.VIDEO -> NewStoriesAdapter(this@HomeFragment)
        }

        layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    private fun initOnFlingListener() {
        val lineaSnapHelper = PagerSnapHelper()
        lineaSnapHelper.attachToRecyclerView(binding.rvSelectedMediaType)
    }

    private fun initOnTabSelectedListener() = binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

        override fun onTabSelected(tab: TabLayout.Tab?) {
            tab?.let {
                when(it.text) {
                    getString(R.string.books) -> { initSelectedMediaTypeAdapter(MediaType.BOOKS) }
                    getString(R.string.audio) -> { initSelectedMediaTypeAdapter(MediaType.AUDIO) }
                    getString(R.string.video) -> { initSelectedMediaTypeAdapter(MediaType.VIDEO) }
                    else -> {
                        initSelectedMediaTypeAdapter(MediaType.VIDEO)
                    }
                }
            }
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {/*NO-OP*/}

        override fun onTabReselected(tab: TabLayout.Tab?) {/*NO-OP*/}

    })


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun categoryClicked(position: Int, category: String) {
        categoryAdapter.selectedPosition = position
        categoryAdapter.notifyDataSetChanged()
        binding.rvCategories.smoothScrollToPosition(position)
    }



    override fun storyClicked(book: Book) {
        val action = ReadingFragmentDirections.actionGlobalToReadingFragment(book.title, book.story)
        findNavController().navigate(action)
    }

    override fun storyClicked(position: Int, category: String) {

    }
}