package com.example.focustime.di

import com.example.focustime.data.network.repositories.RemoteDatabaseRepository
import com.example.focustime.data.network.repositories.RemoteDatabaseRepositoryImpl
import com.example.focustime.domain.usecases.AuthorizationUserUseCase
import com.example.focustime.domain.usecases.AuthorizationUserUseCaseImpl
import com.example.focustime.domain.usecases.RegistrationUserUseCase
import com.example.focustime.domain.usecases.RegistrationUserUseCaseImpl
import dagger.Binds
import dagger.Module

@Module
interface AppBindsModule {

    @Binds
    fun bindRemoteDatabaseRepository(repository: RemoteDatabaseRepositoryImpl): RemoteDatabaseRepository

    @Binds
    fun bindAuthorizationUserUseCase(repository: AuthorizationUserUseCaseImpl): AuthorizationUserUseCase

    @Binds
    fun bindRegistrationUserUseCase(repository: RegistrationUserUseCaseImpl): RegistrationUserUseCase

}