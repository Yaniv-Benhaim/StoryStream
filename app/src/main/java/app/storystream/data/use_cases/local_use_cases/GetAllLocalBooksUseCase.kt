package app.storystream.data.use_cases.local_use_cases

import app.storystream.domain.repositories.MediaRepository

class GetAllLocalBooksUseCase(private val mediaRepository: MediaRepository) {

    suspend operator fun invoke() = mediaRepository.getAllLocalBooks()

}