package app.storystream.data.framework

import app.storystream.data.datasources.MediaDataSource
import app.storystream.domain.models.Book
import app.storystream.domain.utils.Constants.BOOK_COLLECTION
import app.storystream.domain.utils.Constants.BOOK_DOCUMENT
import app.storystream.domain.utils.Constants.MEDIA_COLLECTION
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await

class MediaDataSourceImpl : MediaDataSource {

    private val db = FirebaseFirestore.getInstance()

    override suspend fun getAllBooksMedia(): List<Book> {

        val result = db.collection(MEDIA_COLLECTION).document(BOOK_DOCUMENT).collection(
            BOOK_COLLECTION).get().await()

        return if(result.isEmpty) {
            emptyList()
        } else {

            val books: ArrayList<Book> = ArrayList()
            result.documents.forEach {
                val convertedBook = it.toObject<Book>()
                convertedBook?.let { book ->
                    books.add(book)
                }
            }
            books
        }
    }
}