package com.example.focustime.di

import com.example.focustime.data.network.services.RemoteDatabaseService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule {
    @Provides
    fun provideIntercomService(): RemoteDatabaseService =
        Retrofit.Builder()
            .baseUrl("http://192.168.0.104/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RemoteDatabaseService::class.java)
}