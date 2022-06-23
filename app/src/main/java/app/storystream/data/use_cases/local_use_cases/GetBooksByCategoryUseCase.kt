package app.storystream.data.use_cases.local_use_cases

import app.storystream.domain.enums.Categories
import app.storystream.domain.repositories.MediaRepository

class GetBooksByCategoryUseCase(private val mediaRepository: MediaRepository) {

    suspend operator fun invoke(category: Categories) = mediaRepository.getBooksByCategory(category)

}