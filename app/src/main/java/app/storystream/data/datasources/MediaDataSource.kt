package app.storystream.data.datasources

import app.storystream.domain.models.Book

interface MediaDataSource {

    suspend fun getAllBooksMedia() : List<Book>
}