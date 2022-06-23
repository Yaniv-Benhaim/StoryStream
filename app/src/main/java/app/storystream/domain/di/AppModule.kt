package app.storystream.domain.di

import android.content.Context
import androidx.room.Room
import app.storystream.data.db.BookDao
import app.storystream.data.db.RoomMediaDatabase
import app.storystream.data.framework.AuthDataSourceImpl
import app.storystream.data.framework.LocalDataSourceImpl
import app.storystream.data.framework.MediaDataSourceImpl
import app.storystream.domain.repositories.AuthRepository
import app.storystream.domain.repositories.MediaRepository
import app.storystream.data.use_cases.*
import app.storystream.data.use_cases.local_use_cases.GetAllLocalBooksUseCase
import app.storystream.data.use_cases.local_use_cases.GetBooksByCategoryUseCase
import app.storystream.data.use_cases.local_use_cases.InsertBookUseCase
import app.storystream.data.use_cases.local_use_cases.InsertBooksUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {


    @Provides
    @Singleton
    fun provideRoomMediaDatabase(@ApplicationContext appContext: Context): RoomMediaDatabase {
        return Room.databaseBuilder(
            appContext,
            RoomMediaDatabase::class.java,
            "media_database"
        ).build()
    }

    @Provides
    fun provideBookDao(db: RoomMediaDatabase): BookDao {
        return db.bookDao()
    }

    @Singleton
    @Provides
    fun providesLocalMediaDataSourceImpl(bookDao: BookDao) = LocalDataSourceImpl(bookDao)

    @Singleton
    @Provides
    fun providesMediaDataSource() = MediaDataSourceImpl()

    @Singleton
    @Provides
    fun providesMediaRepository(mediaDataSourceImpl: MediaDataSourceImpl, localDataSourceImpl: LocalDataSourceImpl) = MediaRepository(mediaDataSourceImpl, localDataSourceImpl)

    @Singleton
    @Provides
    fun providesGetAllBooksUseCase(mediaRepository: MediaRepository) = GetAllBooksUseCase(mediaRepository)

    @Singleton
    @Provides
    fun providesAuthDataSource() = AuthDataSourceImpl()

    @Singleton
    @Provides
    fun provideAuthRepository(authDataSource: AuthDataSourceImpl) = AuthRepository(authDataSource)

    @Singleton
    @Provides
    fun providesSignInUseCase(authRepository: AuthRepository) = SignInUseCase(authRepository)

    @Singleton
    @Provides
    fun providesSignOutUseCase(authRepository: AuthRepository) = SignOutUseCase(authRepository)

    @Singleton
    @Provides
    fun providesRegisterUseCase(authRepository: AuthRepository) = RegisterUseCase(authRepository)

    @Singleton
    @Provides
    fun providesRepeatPasswordValidationUseCase(authRepository: AuthRepository) = RepeatPasswordValidationUseCase(authRepository)

    @Singleton
    @Provides
    fun providesIsLoggedInUseCase(authRepository: AuthRepository) = IsLoggedInUseCase(authRepository)

    //Local use cases
    @Singleton
    @Provides
    fun providesGetAllLocalBooksUseCase(mediaRepository: MediaRepository) = GetAllLocalBooksUseCase(mediaRepository)

    @Singleton
    @Provides
    fun providesBooksByCategoryUseCase(mediaRepository: MediaRepository) = GetBooksByCategoryUseCase(mediaRepository)

    @Singleton
    @Provides
    fun providesInsertBooksUseCase(mediaRepository: MediaRepository) = InsertBooksUseCase(mediaRepository)

    @Singleton
    @Provides
    fun providesInsertBookUseCase(mediaRepository: MediaRepository) = InsertBookUseCase(mediaRepository)
}