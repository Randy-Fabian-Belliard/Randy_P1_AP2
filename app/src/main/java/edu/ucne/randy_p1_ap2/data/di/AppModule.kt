package edu.ucne.randy_p1_ap2.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ucne.randy_p1_ap2.data.local.database.AppDb
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object AppModule{
    @Provides
    @Singleton
    fun provideDb(@ApplicationContext appContext: Context) =
        Room.databaseBuilder(
            appContext,
            AppDb::class.java,
            "DishWasher.db"
        ).fallbackToDestructiveMigration()
            .build()
    @Provides
    @Singleton
    fun provideSistemasDao(AppDb: AppDb) = AppDb.sistemasDao()



}
