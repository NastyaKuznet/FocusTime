package com.example.focustime.di

import com.example.focustime.data.network.repositories.*
import com.example.focustime.domain.usecases.*
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
    fun bindUploadImageUseCase(repository: UploadImageUseCaseImpl): UploadImageUseCase

    @Binds
    fun bindAddTypeIndicatorUseCase(repository: AddTypeIndicatorUseCaseImpl): AddTypeIndicatorUseCase

    @Binds
    fun bindGetTypesIndicatorsUseCase(repository: GetTypesIndicatorsUseCaseImpl): GetTypesIndicatorsUseCase

    @Binds
    fun bindGetImagesUseCase(repository: GetImagesUseCaseImpl): GetImagesUseCase

    @Binds
    fun bindDeleteTypeIndicatorUseCase(repository: DeleteTypeIndicatorUseCaseImpl): DeleteTypeIndicatorUseCase
}