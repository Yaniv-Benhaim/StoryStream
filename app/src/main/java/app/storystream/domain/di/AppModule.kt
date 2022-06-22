package app.storystream.domain.di

import app.storystream.data.framework.AuthDataSourceImpl
import app.storystream.data.repositories.AuthRepository
import app.storystream.data.use_cases.*
import app.storystream.presentation.ui.activities.MainActivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {




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

}