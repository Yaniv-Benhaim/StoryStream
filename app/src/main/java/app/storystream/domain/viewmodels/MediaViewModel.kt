package app.storystream.domain.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.storystream.data.use_cases.GetAllBooksUseCase
import app.storystream.data.use_cases.local_use_cases.GetAllLocalBooksUseCase
import app.storystream.data.use_cases.local_use_cases.GetBooksByCategoryUseCase
import app.storystream.data.use_cases.local_use_cases.InsertBookUseCase
import app.storystream.data.use_cases.local_use_cases.InsertBooksUseCase
import app.storystream.domain.enums.Categories
import app.storystream.domain.models.Book
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MediaViewModel @Inject constructor(
    private val getAllBooksUseCase: GetAllBooksUseCase,
    private val getAllLocalBooksUseCase: GetAllLocalBooksUseCase,
    private val insertBooksUseCase: InsertBooksUseCase,
    private val getBooksByCategoryUseCase: GetBooksByCategoryUseCase,
    private val insertBookUseCase: InsertBookUseCase
) : ViewModel() {

    private val _allBooks = MutableLiveData<List<Book>>()
    val allBooks: LiveData<List<Book>> = _allBooks

    private val _filteredBooks = MutableLiveData<List<Book>>()
    val filteredBooks: LiveData<List<Book>> = _filteredBooks


    fun getAllBooksNetworkRequest() = viewModelScope.launch {
        val books = getAllBooksUseCase.invoke()
        if (books.isNotEmpty()) {
            saveBooksToLocalDatabase(books)
        }
    }

    fun getAllBooksFromLocalStorage() = viewModelScope.launch {
        _allBooks.value = getAllLocalBooksUseCase.invoke().value
    }

    fun getBooksByCategory(category: String) = viewModelScope.launch {
        when(category) {
            Categories.FANTASY.name -> _filteredBooks.value = getBooksByCategoryUseCase.invoke(Categories.FANTASY).value
            Categories.ALIENS.name -> _filteredBooks.value = getBooksByCategoryUseCase.invoke(Categories.ALIENS).value
            Categories.SCARY.name -> _filteredBooks.value = getBooksByCategoryUseCase.invoke(Categories.SCARY).value
            Categories.SCIENCE_FICTION.name -> _filteredBooks.value = getBooksByCategoryUseCase.invoke(Categories.SCIENCE_FICTION).value
            Categories.ROMANCE.name -> _filteredBooks.value = getBooksByCategoryUseCase.invoke(Categories.ROMANCE).value
            Categories.TRUE_STORIES.name -> _filteredBooks.value = getBooksByCategoryUseCase.invoke(Categories.TRUE_STORIES).value
            Categories.THRILLER.name -> _filteredBooks.value = getBooksByCategoryUseCase.invoke(Categories.THRILLER).value
            Categories.MYSTERY.name -> _filteredBooks.value = getBooksByCategoryUseCase.invoke(Categories.MYSTERY).value
        }
    }

    private fun saveBooksToLocalDatabase(books: List<Book>) = viewModelScope.launch {
        insertBooksUseCase.invoke(books)
    }
}