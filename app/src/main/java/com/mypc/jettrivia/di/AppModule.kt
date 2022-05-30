package com.mypc.jettrivia.di

import android.content.Context
import androidx.room.Room
import com.mypc.jettrivia.data.ScorerDao
import com.mypc.jettrivia.data.TriviaDataBAse
import com.mypc.jettrivia.network.QuestionApi
import com.mypc.jettrivia.repository.QuestionRepository
import com.mypc.jettrivia.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideQuestionRepository(api:QuestionApi) = QuestionRepository(api)


    @Singleton
    @Provides
    fun provideScorerDao(scorerDB: TriviaDataBAse): ScorerDao = scorerDB.ScorerDao()

    @Singleton
    @Provides
    fun provideQuestionApi() :QuestionApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuestionApi::class.java)
            }


    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context):TriviaDataBAse {
        val build = Room.databaseBuilder(
            context,
            TriviaDataBAse::class.java,
            "scorers_db"
        ).fallbackToDestructiveMigration().build()
        return build
    }

}