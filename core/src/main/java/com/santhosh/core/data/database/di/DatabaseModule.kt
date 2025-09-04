package com.santhosh.core.data.database.di

import android.content.Context
import com.santhosh.core.data.database.AppDatabase
import com.santhosh.core.data.database.dao.ToDoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        AppDatabase.getInstance(context)


    @Provides
    fun provideToDoDao(appDatabase: AppDatabase): ToDoDao = appDatabase.toDoDao()
}