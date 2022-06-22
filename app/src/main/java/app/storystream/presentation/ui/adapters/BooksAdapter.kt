package app.storystream.presentation.ui.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.storystream.R
import app.storystream.databinding.ItemAudioStoryBinding
import app.storystream.databinding.ItemBookBinding
import app.storystream.databinding.ItemCategoryBinding

class BooksAdapter(private val listener: BookClickedListener) : RecyclerView.Adapter<BooksAdapter.BooksViewHolder>() {


    inner class BooksViewHolder(val binding: ItemBookBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooksViewHolder {
        val binding = ItemBookBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return BooksViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BooksViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 10
    }

    interface BookClickedListener {
        fun storyClicked(position: Int, category: String)
    }
}