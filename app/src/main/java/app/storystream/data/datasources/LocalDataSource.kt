package app.storystream.data.datasources

import androidx.lifecycle.LiveData
import app.storystream.domain.enums.Categories
import app.storystream.domain.models.Book

interface LocalDataSource {

    suspend fun insert(book: Book)

    suspend fun insertAll(books: List<Book>)

    suspend fun getAllBooks(): LiveData<List<Book>>

    suspend fun getBooksByCategory(categories: Categories) : LiveData<List<Book>>

    suspend fun remove(book: Book)

}