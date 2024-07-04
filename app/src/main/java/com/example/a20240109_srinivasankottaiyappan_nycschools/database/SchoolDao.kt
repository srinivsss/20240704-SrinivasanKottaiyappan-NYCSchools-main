package com.example.a20240109_srinivasankottaiyappan_nycschools.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.a20240109_srinivasankottaiyappan_nycschools.model.School


@Dao
interface SchoolDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(school: List<School>)

    @Query("DELETE FROM school_table")
    fun deleteAll()


    @Transaction
    @Query("SELECT * FROM school_table ORDER BY school_name ASC")
    fun getSchools(): LiveData<List<School>>

    @Transaction
    @Query("SELECT * FROM school_table where school_name like :searchString ORDER BY school_name ASC")
    fun getSchoolsFiltered(searchString: String?): LiveData<List<School>>
}

