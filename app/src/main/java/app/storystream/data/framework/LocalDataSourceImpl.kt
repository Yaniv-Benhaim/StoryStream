package app.storystream.data.framework

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.storystream.data.datasources.LocalDataSource
import app.storystream.data.db.BookDao
import app.storystream.domain.enums.Categories
import app.storystream.domain.models.Book
import app.storystream.domain.utils.MediaFilterUtil
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(private val bookDao: BookDao) : LocalDataSource {

    override suspend fun insert(book: Book) {
        bookDao.insert(book)
    }

    override suspend fun insertAll(books: List<Book>) {
        books.forEach { bookDao.insert(it) }
    }

    override suspend fun getAllBooks(): LiveData<List<Book>> {
        return bookDao.getAll()
    }

    override suspend fun getBooksByCategory(categories: String): LiveData<List<Book>> {
        val allBooks = bookDao.getAll().value
        val mutableBooks = MutableLiveData<List<Book>>()
        val filteredBooks: LiveData<List<Book>> = mutableBooks
        allBooks?.let {
            mutableBooks.value = MediaFilterUtil.filterBooksByCategory(allBooks, categories)
        }
        return filteredBooks
    }

    override suspend fun remove(book: Book) {

    }
}