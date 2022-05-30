package com.mypc.jettrivia.screens.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mypc.jettrivia.model.TopScorers
import com.mypc.jettrivia.repository.ScoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

class ScoresViewModel @Inject constructor(private val repository: ScoreRepository):ViewModel() {
    private var _scoresList = MutableStateFlow<List<TopScorers>>(emptyList())
    val scorersList = _scoresList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
             repository.getAllRecords().distinctUntilChanged().collect {listOfScores ->
                if(listOfScores.isNullOrEmpty()) {
                    Log.d("empty scores",":Empty List")
                }else {
                    _scoresList.value = listOfScores
                }
            }
        }
    }


    fun addScore(score:TopScorers) {
        viewModelScope.launch {
            repository.addRecord(score)
        }
    }

    fun removeScore(score:TopScorers) {
        viewModelScope.launch {
            repository.deleteRecord(score)
        }
    }

    fun removeAllScores(){
        viewModelScope.launch {
            repository.deleteAllRecords()
        }
    }


}