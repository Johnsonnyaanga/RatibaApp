package com.example.ratiba.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "cartegories_table")
@Parcelize
data class Cartegories(
    @PrimaryKey(autoGenerate = true)
    val cartegory_ID:Int,
    val cartegoryName:String
):Parcelable
