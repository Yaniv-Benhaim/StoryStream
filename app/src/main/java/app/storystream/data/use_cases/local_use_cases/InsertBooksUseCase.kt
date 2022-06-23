package app.storystream.data.use_cases.local_use_cases

import app.storystream.domain.enums.Categories
import app.storystream.domain.models.Book
import app.storystream.domain.repositories.MediaRepository

class InsertBooksUseCase(private val mediaRepository: MediaRepository) {

    suspend operator fun invoke(books: List<Book>) = mediaRepository.insertBooks(books)

}