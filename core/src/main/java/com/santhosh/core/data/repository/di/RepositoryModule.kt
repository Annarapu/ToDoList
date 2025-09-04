package com.santhosh.core.data.repository.di

import com.santhosh.core.data.repository.ToDoRepository
import com.santhosh.core.data.repository.ToDoRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindToDoRepository(impl: ToDoRepositoryImpl): ToDoRepository
}