package app.storystream.data.use_cases.local_use_cases

import app.storystream.domain.models.Book
import app.storystream.domain.repositories.MediaRepository

class InsertBookUseCase(private val mediaRepository: MediaRepository) {

    suspend operator fun invoke(book: Book) = mediaRepository.insertBook(book)

}