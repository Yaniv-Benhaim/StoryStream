package app.storystream.presentation.ui.fragments.details.reader

import android.os.Bundle
import android.text.Html
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import app.storystream.R
import app.storystream.databinding.FragmentReadingBinding

class ReadingFragment : Fragment() {

    private var _binding: FragmentReadingBinding? = null
    private val binding get() = _binding!!
    private var textSize = 16F
    val args: ReadingFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReadingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initReadingView()
    }

    private fun initReadingView() {
        val htmlTitle = Html.fromHtml(args.title)
        val htmlStory = Html.fromHtml(args.story)
        binding.title.text = htmlTitle
        binding.story.text = htmlStory
        binding.btnIncreaseFontSize.setOnClickListener { increaseTextSize() }
        binding.btnDecreaseFontSize.setOnClickListener { decreaseTextSize() }
    }

    private fun increaseTextSize() {
        if (textSize < 22) {
            textSize += 1
            binding.story.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize)
        }
    }

    private fun decreaseTextSize() {
        if (textSize > 14) {
            textSize -= 1
            binding.story.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}