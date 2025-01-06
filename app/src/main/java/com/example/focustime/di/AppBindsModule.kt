package com.example.focustime.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.focustime.data.database.LocalDatabase
import com.example.focustime.data.database.dao.*
import com.example.focustime.data.database.repository.*
import com.example.focustime.data.network.repositories.*
import com.example.focustime.domain.usecases.*
import com.example.focustime.domain.usecases.localDatabase.image.*
import com.example.focustime.domain.usecases.localDatabase.indicator.*
import com.example.focustime.domain.usecases.localDatabase.typeIndicator.*
import dagger.Binds
import dagger.Module
import dagger.Provides

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
    fun updateAvatarUseCase(repository: UpdateAvatarUseCaseImpl): UpdateAvatarUseCase

    @Binds
    fun bindAcceptRequestUseCase(repository: AcceptRequestUseCaseImpl): AcceptRequestUseCase

    @Binds
    fun bindGetAllIndicatorsLocalUseCase(useCase: GetAllIndicatorsLocalUseCaseImpl): GetAllIndicatorsLocalUseCase

    @Binds
    fun bindAddTypeIndicatorLocalUseCase(useCase: AddTypeIndicatorLocalUseCaseImpl): AddTypeIndicatorLocalUseCase

    @Binds
    fun bindGetTypesIndicatorLocalUseCase(useCase: GetTypesIndicatorLocalUseCaseImpl): GetTypesIndicatorLocalUseCase

    @Binds
    fun bindGetImageByIdTypeLocalUseCase(useCase: GetImagesByIdTypeLocalUseCaseImpl): GetImagesByIdTypeLocalUseCase

    @Binds
    fun bindDeleteTypeIndicatorLocalUseCase(useCase: DeleteTypeIndicatorLocalUseCaseImpl): DeleteTypeIndicatorLocalUseCase

    @Binds
    fun bindAddIndicatorLocalUseCase(useCase: AddIndicatorLocalUseCaseImpl): AddIndicatorLocalUseCase

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
        fun provideIndicatorDAO(db: LocalDatabase): IndicatorDAO = db.indicatorDao

        @Provides
        //@Singleton
        fun provideImageDAO(db: LocalDatabase): ImageDAO = db.imageDao

        @Provides
        //@Singleton
        fun provideTypeIndicatorDAO(db: LocalDatabase): TypeIndicatorDAO = db.typeIndicatorDAO
    }
}