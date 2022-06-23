package app.storystream.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import app.storystream.domain.models.Book

@Database(entities = [Book::class], version = 1, exportSchema = false)
abstract class RoomMediaDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao
}