package com.example.a20240109_srinivasankottaiyappan_nycschools.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "school_table")
data class School(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "dbn")
    val dbn: String,

    @NonNull
    @ColumnInfo(name = "school_name")
    val school_name: String,

    @NonNull
    @ColumnInfo(name = "overview_paragraph")
    val overview_paragraph: String,

    @NonNull
    @ColumnInfo(name = "phone_number")
    val phone_number: String,

    @NonNull
    @ColumnInfo(name = "school_email")
    val school_email: String,

    @NonNull
    @ColumnInfo(name = "website")
    val website: String,

    @NonNull
    @ColumnInfo(name = "total_students")
    val total_students: String,

    @NonNull
    @ColumnInfo(name = "primary_address_line_1")
    val primary_address_line_1: String,

    @NonNull
    @ColumnInfo(name = "city")
    val city: String,

    @NonNull
    @ColumnInfo(name = "zip")
    val zip: String,

    @NonNull
    @ColumnInfo(name = "latitude")
    val latitude: String,

    @NonNull
    @ColumnInfo(name = "longitude")
    val longitude: String
) : Serializable
