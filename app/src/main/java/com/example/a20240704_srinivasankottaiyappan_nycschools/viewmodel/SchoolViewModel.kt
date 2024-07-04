package com.example.a20240109_srinivasankottaiyappan_nycschools.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.a20240109_srinivasankottaiyappan_nycschools.model.School
import com.example.a20240109_srinivasankottaiyappan_nycschools.repository.SchoolRepository


class SchoolViewModel(application: Application) : AndroidViewModel(application) {
    private val mRepository: SchoolRepository
    private val mAllSchools: LiveData<List<School>>

    init {
        mRepository = SchoolRepository.getRepository(application.applicationContext)!!
        mAllSchools = mRepository.allSchools
    }

    val allSchools: LiveData<List<School>>
        get() = mAllSchools

    fun getFilteredSchools(searchString: String?): LiveData<List<School>> {
        return mRepository.getFilteredSchools(searchString)
    }

    fun loadSchools() {
        mRepository.loadSchools()
    }
}
