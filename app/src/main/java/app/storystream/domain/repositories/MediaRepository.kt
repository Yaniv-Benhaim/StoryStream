package app.storystream.domain.repositories

import app.storystream.data.datasources.LocalDataSource
import app.storystream.data.datasources.MediaDataSource
import app.storystream.domain.enums.Categories
import app.storystream.domain.models.Book
import javax.inject.Inject

class MediaRepository @Inject constructor(
    private val mediaDataSourceImpl: MediaDataSource,
    private val localDataSourceImpl: LocalDataSource
) {

    suspend fun getAllBooksMedia() = mediaDataSourceImpl.getAllBooksMedia()

    suspend fun getAllLocalBooks() = localDataSourceImpl.getAllBooks()

    suspend fun getBooksByCategory(categories: Categories) = localDataSourceImpl.getBooksByCategory(categories)

    suspend fun insertBooks(books: List<Book>) = localDataSourceImpl.insertAll(books)

    suspend fun insertBook(book: Book) = localDataSourceImpl.insert(book)
}