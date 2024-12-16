package com.ikhdaamel.ucp2.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ikhdaamel.ucp2.data.dao.DosenDao
import com.ikhdaamel.ucp2.data.dao.MataKuliahDao
import com.ikhdaamel.ucp2.data.entity.Dosen
import com.ikhdaamel.ucp2.data.entity.MataKuliah


@Database(entities = [Dosen::class, MataKuliah::class], version = 1, exportSchema = false)
abstract class KRSDatabase : RoomDatabase() {

    abstract fun dosenDao(): DosenDao
    abstract fun mataKuliahDao(): MataKuliahDao

    companion object {
        @Volatile
        private var INSTANCE: KRSDatabase? = null
        fun getDatabase(context: Context): KRSDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    KRSDatabase::class.java,
                    "KRSDatabase"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
