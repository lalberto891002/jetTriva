package com.mypc.jettrivia.repository

import com.mypc.jettrivia.data.ScorerDao
import com.mypc.jettrivia.model.TopScorers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class ScoreRepository @Inject() constructor(val scorerDao: ScorerDao) {

    suspend fun addRecord(scorers: TopScorers) = scorerDao.insert(scorer = scorers)

    suspend fun deleteRecord(scorers: TopScorers) = scorerDao.delete(scorer = scorers)

    suspend fun deleteAllRecords() = scorerDao.deleteAll()

    suspend fun getAllRecords() : Flow<List<TopScorers>> = scorerDao.getTopScorers().
        flowOn(Dispatchers.IO).
        conflate()


}