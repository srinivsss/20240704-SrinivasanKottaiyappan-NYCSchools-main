package com.example.a20240109_srinivasankottaiyappan_nycschools.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.a20240109_srinivasankottaiyappan_nycschools.model.SATScores
import com.example.a20240109_srinivasankottaiyappan_nycschools.repository.SchoolRepository


class SATScoresViewModel(application: Application) : AndroidViewModel(application) {
    private val mRepository: SchoolRepository?

    init {
        mRepository = SchoolRepository.getRepository(application.applicationContext)
    }

    fun getScoresForSchool(schoolDBN: String?): LiveData<SATScores> {
        return mRepository!!.getSATScoresForSchool(schoolDBN)
    }
}

