package com.example.talkcompanion.data.model.phrase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "phrase")
data class PhraseEntity(
    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo(name = "userId") val userId: String?,
    @ColumnInfo(name = "userName") val userName: String?,
    @ColumnInfo(name = "phrase") var phrase: String?,
    @ColumnInfo(name = "orderNumber") var orderNumber: Int?
)