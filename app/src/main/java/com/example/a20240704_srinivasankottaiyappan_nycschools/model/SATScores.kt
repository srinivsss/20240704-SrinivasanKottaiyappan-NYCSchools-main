package com.example.a20240109_srinivasankottaiyappan_nycschools.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sat_table")
data class SATScores(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "dbn")
    val dbn: String,

    @NonNull
    @ColumnInfo(name = "school_name")
    val school_name: String = "",

    @NonNull
    @ColumnInfo(name = "num_of_sat_test_takers")
    val num_of_sat_test_takers: String = "",

    @NonNull
    @ColumnInfo(name = "sat_critical_reading_avg_score")
    val sat_critical_reading_avg_score: String = "",

    @NonNull
    @ColumnInfo(name = "sat_math_avg_score")
    val sat_math_avg_score: String = "",

    @NonNull
    @ColumnInfo(name = "sat_writing_avg_score")
    val sat_writing_avg_score: String = ""
)


