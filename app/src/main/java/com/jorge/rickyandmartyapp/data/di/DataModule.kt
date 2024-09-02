package com.jorge.rickyandmartyapp.data.di

import com.jorge.rickyandmartyapp.data.repository.CharacterRepositoryImpl
import com.jorge.rickyandmartyapp.domain.repository.CharacterRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindsUserRepository(impl: CharacterRepositoryImpl) : CharacterRepository
}