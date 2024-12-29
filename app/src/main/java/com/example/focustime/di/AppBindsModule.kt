package com.example.focustime.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.focustime.data.database.LocalDatabase
import com.example.focustime.data.database.dao.UserInfoDAO
import com.example.focustime.data.database.repository.*
import com.example.focustime.data.network.repositories.*
import com.example.focustime.domain.usecases.*
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

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

    @Binds
    fun bindAddIndicatorUseCase(repository: AddIndicatorUseCaseImpl): AddIndicatorUseCase

    @Binds
    fun bindAllIndicatorsUseCase(repository: GetAllIndicatorsUseCaseImpl): GetAllIndicatorsUseCase
    @Binds
    fun bindGetFriendsUseCase(repository: GetFriendsUseCaseImpl): GetFriendsUseCase

    @Binds
    fun bindGetRequestUseCase(repository: GetRequestUseCaseImpl): GetRequestUseCase

    @Binds
    fun bindSendFriendRequestUseCase(repository: SendFriendRequestUseCaseImpl): SendFriendRequestUseCase

    @Binds
    fun getUserInfoUseCase(repository: getUserInfoUseCaseImpl): getUserInfoUseCase

    @Binds
    fun updateUserInfoUseCase(repository: UpdateUserInfoUseCaseImpl): UpdateUserInfoUseCase

    @Binds
    fun getRequestUseCaseImpl(repository: GetRequestUseCaseImpl): GetRequestUseCase

    @Binds
    fun updateAvatarUseCase(repository: UpdateAvatarUseCaseImpl): UpdateAvatarUseCase

    @Binds
    fun bindSaveUserIdInLocaleUseCase(repository: SaveUserIdInLocaleUseCaseImpl): SaveUserIdInLocaleUseCase

    @Binds
    fun bindGetUserIdLocaleUseCase(repository: GetUserIdLocaleUseCaseImpl): GetUserIdLocaleUseCase

    @Binds
    fun bindDeleteUserIdLocaleUseCase(useCase: DeleteUserIdLocaleUseCaseImpl): DeleteUserIdLocaleUseCase

    @Binds
    //@Singleton
    fun bindLocalDatabaseRepository(repository: LocalDatabaseRepositoryImpl): LocalDatabaseRepository

    companion object {

        @Provides
        fun provideContext(app: Application): Context = app.applicationContext

        @Provides
        //@Singleton
        fun provideLocalDataBase(context: Context): LocalDatabase =
            Room.databaseBuilder(
                context,
                LocalDatabase::class.java,
                "local.db"
            ).build()

        @Provides
        //@Singleton
        fun provideUserInfoDAO(db: LocalDatabase): UserInfoDAO = db.userInfoDao
    }
}