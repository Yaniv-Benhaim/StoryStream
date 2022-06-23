package app.storystream.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import app.storystream.domain.utils.Constants.BOOK_COLLECTION

@Entity(tableName = BOOK_COLLECTION)
data class Book(
    @PrimaryKey(autoGenerate = false) var id: String = "1",
    val title: String = "",
    val subtitle: String = "",
    val categories: String = "",
    val author: String = "",
    val thumbNail: String = "",
    val story: String = ""
)

