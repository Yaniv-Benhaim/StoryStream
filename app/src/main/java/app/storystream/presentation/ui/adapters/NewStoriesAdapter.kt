package app.storystream.presentation.ui.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.storystream.R
import app.storystream.databinding.ItemAudioStoryBinding
import app.storystream.databinding.ItemCategoryBinding

class NewStoriesAdapter(private val listener: NewStoriesClickedListener) : RecyclerView.Adapter<NewStoriesAdapter.CategoryViewHolder>() {


    inner class CategoryViewHolder(val binding: ItemAudioStoryBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemAudioStoryBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 10
    }

    interface NewStoriesClickedListener {
        fun storyClicked(position: Int, category: String)
    }
}