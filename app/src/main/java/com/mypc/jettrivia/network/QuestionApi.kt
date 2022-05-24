package com.mypc.jettrivia.network

import com.mypc.jettrivia.model.Question
import com.mypc.jettrivia.util.Constants
import retrofit2.http.GET
import javax.inject.Singleton

@Singleton
interface QuestionApi {
    @GET("world.json")
    suspend fun getAllQuestions(): Question

}