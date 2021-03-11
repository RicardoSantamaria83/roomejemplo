package com.ricardo.roomejemplo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "palabra_table")
data class Palabra (
    @PrimaryKey @ColumnInfo(name = "palabra")
    val palabra: String
        )