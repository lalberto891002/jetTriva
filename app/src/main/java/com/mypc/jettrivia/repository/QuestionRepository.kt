package com.mypc.jettrivia.repository

import android.util.Log
import com.mypc.jettrivia.data.DataOrException
import com.mypc.jettrivia.model.QuestionItem
import com.mypc.jettrivia.network.QuestionApi
import javax.inject.Inject

class QuestionRepository @Inject constructor(private val api:QuestionApi) {
    private val dataOrException =
        DataOrException<ArrayList<QuestionItem>,
                Boolean,
                Exception>(null)

    suspend fun getAllQuestions() :DataOrException<ArrayList<QuestionItem>,Boolean,Exception> {
        try {
            dataOrException.loading = true
            dataOrException.data = api.getAllQuestions()
            if(dataOrException.data.toString().isNotEmpty()) dataOrException.loading = false

        }catch (exception:Exception) {
            dataOrException.e = exception
            Log.d("Exception","getAllQuestions:${dataOrException.e!!.localizedMessage}")
        }
        return dataOrException
    }

}