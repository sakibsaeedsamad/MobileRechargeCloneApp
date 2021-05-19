package com.sssakib.mobilerechargecloneapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contact")
data class Contact(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,
    @ColumnInfo(name = "name")
    var name: String?,
    @ColumnInfo(name = "number")
    var number: String?

)
{
    constructor(name: String?,number: String?) : this(0,name,number)

}