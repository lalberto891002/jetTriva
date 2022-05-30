package com.mypc.jettrivia.model

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant
import java.util.*

@Entity(tableName = "tops_scorers_table")
data class TopScorers @RequiresApi(Build.VERSION_CODES.O) constructor(
     @PrimaryKey
     val uid:UUID = UUID.randomUUID(),
     @ColumnInfo(name = "full_name")
     val name:String?,
     @ColumnInfo(name = "total_score")
     val score:Int?,
     @ColumnInfo(name = "note_entry_date")
     val entryDate: Date = Date.from(Instant.now())




)