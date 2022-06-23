package app.storystream.domain.utils

import app.storystream.domain.enums.Categories
import app.storystream.domain.models.Book

object MediaFilterUtil {

    fun filterBooksByCategory(books: List<Book>, category: String) : List<Book> {
        return books.filter { it.categories.contains(category.lowercase()) }
    }
}