package com.example.focustime.di

import com.example.focustime.data.network.repositories.RemoteDatabaseRepository
import com.example.focustime.data.network.repositories.RemoteDatabaseRepositoryImpl
import com.example.focustime.domain.usecases.AuthorizationUserUseCase
import com.example.focustime.domain.usecases.AuthorizationUserUseCaseImpl
import com.example.focustime.domain.usecases.GetFriendsUseCase
import com.example.focustime.domain.usecases.GetFriendsUseCaseImpl
import com.example.focustime.domain.usecases.GetRequestUseCase
import com.example.focustime.domain.usecases.GetRequestUseCaseImpl
import com.example.focustime.domain.usecases.RegistrationUserUseCase
import com.example.focustime.domain.usecases.RegistrationUserUseCaseImpl
import com.example.focustime.domain.usecases.SendFriendRequestUseCase
import com.example.focustime.domain.usecases.SendFriendRequestUseCaseImpl
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

    @Binds
    fun bindGetFriendsUseCase(repository: GetFriendsUseCaseImpl): GetFriendsUseCase

    @Binds
    fun bindGetRequestUseCase(repository: GetRequestUseCaseImpl): GetRequestUseCase

    @Binds
    fun bindSendFriendRequestUseCase(repository: SendFriendRequestUseCaseImpl): SendFriendRequestUseCase
}