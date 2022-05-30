package com.mypc.jettrivia.screens.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mypc.jettrivia.data.DataOrException
import com.mypc.jettrivia.model.QuestionItem
import com.mypc.jettrivia.repository.QuestionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject


@HiltViewModel
class QuestionsViewModel @Inject constructor(private val repository: QuestionRepository):ViewModel() {
    val data:MutableState<DataOrException<ArrayList<QuestionItem>,Boolean,Exception>> = mutableStateOf(
        DataOrException(null,true,Exception(""))
    )

    init {
        getAllQuestions()
    }

    private fun getAllQuestions() {
        viewModelScope.launch {
            data.value.loading = true
            data.value = repository.getAllQuestions()
            if(data.value.data.toString().isNotEmpty()) {
                data.value.loading = false
                if(data.value.e?.equals("") == false) {
                    Log.d("Exception",data.value.e!!.message.toString())
                }
            }else {

                Log.d("MSG",data.value.e!!.message.toString())
            }

        }
    }

    fun getQuestionSize(): Int {
        if(data.value.data != null) {
            return data.value.data!!.size
        }else {
            return 0
        }

    }

}