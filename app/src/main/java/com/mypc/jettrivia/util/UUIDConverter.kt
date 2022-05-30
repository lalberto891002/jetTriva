package com.mypc.jettrivia.util

import androidx.room.TypeConverter
import java.util.*

class UUIDConverter {

    @TypeConverter
    fun fromUUID(uuid:UUID):String? {
        return uuid.toString()
    }

    @TypeConverter
    fun uuidFromString(id:String):UUID {
        return UUID.fromString(id)
    }
}