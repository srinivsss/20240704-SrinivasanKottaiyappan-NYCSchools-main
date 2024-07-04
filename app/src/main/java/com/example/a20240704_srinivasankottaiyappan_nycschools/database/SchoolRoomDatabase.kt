package com.example.a20240109_srinivasankottaiyappan_nycschools.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.a20240109_srinivasankottaiyappan_nycschools.model.SATScores
import com.example.a20240109_srinivasankottaiyappan_nycschools.model.School
import java.util.concurrent.Executors
import kotlin.concurrent.Volatile


@Database(entities = [School::class, SATScores::class], version = 1, exportSchema = false)
abstract class SchoolRoomDatabase : RoomDatabase() {
    abstract fun schoolDao(): SchoolDao
    abstract fun satScoresDao(): SATScoreDao

    companion object {
        @Volatile
        private var INSTANCE: SchoolRoomDatabase? = null
        private const val NUMBER_OF_THREADS = 10

        val databaseWriteExecutor = Executors.newFixedThreadPool(
            NUMBER_OF_THREADS
        )

        /**
         * DB Singleton
         * @param context
         * @return
         */
        fun getDatabase(context: Context): SchoolRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = databaseBuilder(
                    context.applicationContext,
                    SchoolRoomDatabase::class.java,
                    "school_database"
                ).addCallback(sRoomDatabaseCallback).build()
                INSTANCE = instance
                instance
            }

        }

        /**
         * Handle any setup after DB is created for the first time
         */
        private val sRoomDatabaseCallback: Callback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                databaseWriteExecutor.execute {
                    val schoolDao = INSTANCE!!.schoolDao()
                    schoolDao.deleteAll()
                    val scoresDao: SATScoreDao =
                        INSTANCE!!.satScoresDao()
                    scoresDao.deleteAll()
                }
            }
        }
    }
}

