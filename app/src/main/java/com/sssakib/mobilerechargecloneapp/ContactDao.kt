package com.sssakib.mobilerechargecloneapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ContactDao {

    @Query("SELECT * from contact ORDER BY name ASC ")
    fun getAllContactInfo(): MutableList<Contact>?

    @Insert
    fun insertContact(contact: Contact)

    @Query("UPDATE contact SET name = :name, number= :number WHERE id =:id")
    fun updateContact(id:Int, name: String?, number: String?)


}