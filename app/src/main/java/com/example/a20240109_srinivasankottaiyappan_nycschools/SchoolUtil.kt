package com.example.a20240109_srinivasankottaiyappan_nycschools

import android.content.Context
import com.example.a20240109_srinivasankottaiyappan_nycschools.model.SATScores

object SchoolUtil {
    val SEPARATOR: String = " : "

    fun displayScore(score: String,displayString: String): String {
       return "$displayString $SEPARATOR $score"
    }
}