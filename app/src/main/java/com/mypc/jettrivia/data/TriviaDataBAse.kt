package com.mypc.jettrivia.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mypc.jettrivia.model.TopScorers
import com.mypc.jettrivia.util.DateConverter
import com.mypc.jettrivia.util.UUIDConverter


@Database(entities = [TopScorers::class], version = 1)
@TypeConverters(DateConverter::class, UUIDConverter::class)
abstract class TriviaDataBAse: RoomDatabase() {
    abstract fun ScorerDao():ScorerDao
}