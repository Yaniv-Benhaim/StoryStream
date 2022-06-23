package app.storystream.presentation.ui.adapters

import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.storystream.databinding.ItemBookBinding
import app.storystream.domain.models.Book


class BooksAdapter(private val listener: BookClickedListener, private val books: List<Book>) : RecyclerView.Adapter<BooksAdapter.BooksViewHolder>() {


    inner class BooksViewHolder(val binding: ItemBookBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooksViewHolder {
        val binding = ItemBookBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return BooksViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BooksViewHolder, position: Int) {
        val book = books[position]
        val htmlTitle = Html.fromHtml(book.title)
        val htmlCategory = Html.fromHtml(book.categories)
        holder.binding.tvTitle.text = htmlTitle
        holder.binding.tvSubTitle.text = htmlTitle
        holder.binding.tvCategory.text = htmlCategory.split(",")[0]
        holder.binding.root.setOnClickListener {
            listener.storyClicked(book)
        }
    }

    override fun getItemCount(): Int {
        return books.size
    }

    interface BookClickedListener {
        fun storyClicked(book: Book)
    }
}