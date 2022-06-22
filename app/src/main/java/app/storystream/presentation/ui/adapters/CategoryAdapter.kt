package app.storystream.presentation.ui.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.storystream.R
import app.storystream.databinding.ItemCategoryBinding

class CategoryAdapter(private val listener: CategoryClickedListener) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private val categories = listOf("Top rated", "Fantasy", "Adventure", "Romantic", "Sci-fi", "Documentary", "Reddit", "True")
    var selectedPosition = 0

    inner class CategoryViewHolder(val binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemCategoryBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        holder.binding.btnCategory.text = category
        holder.binding.btnCategory.setOnClickListener { listener.categoryClicked(position, category) }

        if (selectedPosition == position) {
            setSelectedItem(holder.binding)
        } else {
            setUnselectedItem(holder.binding)
        }
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    private fun setSelectedItem(binding: ItemCategoryBinding) {
        binding.btnCategory.setBackgroundResource(R.drawable.category_bg_selected)
    }

    private fun setUnselectedItem(binding: ItemCategoryBinding) {
        binding.btnCategory.setBackgroundResource(R.drawable.category_bg_default)
    }

    interface CategoryClickedListener {
        fun categoryClicked(position: Int, category: String)
    }
}