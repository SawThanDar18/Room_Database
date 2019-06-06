package com.example.room_database.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    val title : String,
    val note : String
){
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
}