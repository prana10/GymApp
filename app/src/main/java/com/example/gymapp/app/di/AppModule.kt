package com.example.gymapp.app.di

import com.example.gymapp.app.data.repository.UserRepositoryImpl
import com.example.gymapp.app.domain.repository.UserRepository
import com.example.gymapp.app.domain.usecase.LogoutUseCase
import com.example.gymapp.app.presentation.viewmodel.MainViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideUserRepository(): UserRepository {
        return UserRepositoryImpl()
    }

    @Provides
    fun provideLogoutUseCase(userRepository: UserRepository): LogoutUseCase {
        return LogoutUseCase(userRepository)
    }

    @Provides
    fun provideMainViewModel(logoutUseCase: LogoutUseCase): MainViewModel {
        return MainViewModel(logoutUseCase)
    }
}