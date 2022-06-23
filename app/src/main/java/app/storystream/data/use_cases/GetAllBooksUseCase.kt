package app.storystream.data.use_cases


import app.storystream.domain.repositories.MediaRepository

class GetAllBooksUseCase(private val mediaRepository: MediaRepository) {

    suspend operator fun invoke() = mediaRepository.getAllBooksMedia()

}