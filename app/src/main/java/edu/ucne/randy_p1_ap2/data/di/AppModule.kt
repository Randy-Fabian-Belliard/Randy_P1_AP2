package edu.ucne.randy_p1_ap2.data.di

import android.content.Context
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ucne.randy_p1_ap2.data.local.database.AppDb
import edu.ucne.randy_p1_ap2.data.remote.a.TicketsApi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
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

    @Provides
    @Singleton
    fun providesMoshi(): Moshi =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    @Provides
    @Singleton
    fun providesTicketApi(moshi: Moshi): TicketsApi {
        return Retrofit.Builder()
            .baseUrl("https://ap2ticket.azurewebsites.net/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(TicketsApi::class.java)
    }




}
