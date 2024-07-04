package com.example.a20240109_srinivasankottaiyappan_nycschools.database

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.a20240109_srinivasankottaiyappan_nycschools.model.SATScores
@Dao
interface SATScoreDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(scores: List<SATScores>)

    @Query("DELETE FROM sat_table")
    fun deleteAll()

    @Transaction
    @Query("SELECT * FROM sat_table where dbn = :schoolDBN")
    fun getScore(schoolDBN: String?): LiveData<SATScores>

    @VisibleForTesting
    @Transaction
    @Query("SELECT * FROM sat_table  ORDER BY school_name ASC")
    fun getAllScores(): LiveData<List<SATScores?>?>?

}