package com.mypc.jettrivia.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mypc.jettrivia.model.TopScorers


@Database(entities = [TopScorers::class], version = 1)
abstract class TriviaDataBAse: RoomDatabase() {
    abstract fun ScorerDao():ScorerDao
}