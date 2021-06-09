package com.sssakib.mobilerechargecloneapp
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Contact::class), version = 1)
abstract class ContactDatabase : RoomDatabase() {

    abstract fun contactDao(): ContactDao

    companion object {
        private var INSTANCE: ContactDatabase? = null

        fun getDatabase(context: Context): ContactDatabase? {

            if (INSTANCE == null) {

                INSTANCE = Room.databaseBuilder<ContactDatabase>(

                    context.applicationContext,
                    ContactDatabase::class.java,
                    "contactdatabase"
                )
                    .allowMainThreadQueries()
                    .build()

            }
            return INSTANCE
        }

    }
}