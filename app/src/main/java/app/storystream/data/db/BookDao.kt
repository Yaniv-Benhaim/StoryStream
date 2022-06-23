package app.storystream.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import app.storystream.domain.models.Book

@Dao
interface BookDao {

    @Query("SELECT * FROM books")
    fun getAll(): LiveData<List<Book>>

    @Insert
    suspend fun insert(book: Book)
}