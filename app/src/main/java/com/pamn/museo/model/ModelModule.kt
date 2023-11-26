package com.pamn.museo.model

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserDataModule {

    @Singleton
    @Provides
    fun provideUserDataClass(): Class<UserData> {
        return UserData::class.java
    }
}