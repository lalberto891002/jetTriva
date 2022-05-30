package com.mypc.jettrivia.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "tops_scorers_table")
data class TopScorers (
     @PrimaryKey
     val uid:UUID = UUID.randomUUID(),
     @ColumnInfo(name = "full_name")
     val name:String?,
     @ColumnInfo(name = "total_score")
     val score:Int?
)