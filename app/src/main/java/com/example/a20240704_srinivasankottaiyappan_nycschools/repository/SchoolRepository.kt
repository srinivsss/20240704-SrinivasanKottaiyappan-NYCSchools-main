package com.example.a20240109_srinivasankottaiyappan_nycschools.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.a20240109_srinivasankottaiyappan_nycschools.model.SATScores
import com.example.a20240109_srinivasankottaiyappan_nycschools.model.School
import com.example.a20240109_srinivasankottaiyappan_nycschools.database.SATScoreDao
import com.example.a20240109_srinivasankottaiyappan_nycschools.database.SchoolDao
import com.example.a20240109_srinivasankottaiyappan_nycschools.database.SchoolRoomDatabase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import kotlin.concurrent.Volatile


class SchoolRepository (context: Context) {
    private val mSchoolDao: SchoolDao
    private val mScoresDao: SATScoreDao
    private val mAllSchools: LiveData<List<School>>

    init {
        val db: SchoolRoomDatabase = SchoolRoomDatabase.getDatabase(context)
        mSchoolDao = db.schoolDao()
        mScoresDao = db.satScoresDao()
        mAllSchools = mSchoolDao.getSchools()

    }

    val allSchools: LiveData<List<School>>
        /**
         * Will fetch Schools list as LiveData so that it can be executed in the background
         * @return
         */
        get() = mAllSchools

    /**
     * Search in DB
     * @param searchString
     * @return
     */
    fun getFilteredSchools(searchString: String?): LiveData<List<School>> {
        return mSchoolDao.getSchoolsFiltered(searchString)
    }

    /**
     * Get SATScores for School DBN
     * @param schoolDBN
     * @return
     */
    fun getSATScoresForSchool(schoolDBN: String?): LiveData<SATScores> {
        return mScoresDao.getScore(schoolDBN)
    }

    /**
     * Insert Schools into DB in background
     * @param schools
     */
    fun insertAll(schools: List<School>) {
        SchoolRoomDatabase.databaseWriteExecutor.execute { mSchoolDao.insertAll(schools) }
    }

    /**
     * Insert Scores into DB in background
     * @param scores
     */
    fun insertAllScores(scores: List<SATScores>) {
        SchoolRoomDatabase.databaseWriteExecutor.execute { mScoresDao.insertAll(scores) }
    }

    /**
     * From here lies all code related to REST API calls using OKHTTP.
     * We can put them in another class to handle them.
     */
    fun loadSchools() {
        fetchSchoolsData()
        fetchSATScores()
    }

    /**
     * Fetches School Data from NYC Schools API
     */
    fun fetchSchoolsData() {
        val client = OkHttpClient().newBuilder()
            .build()
        val request: Request = Request.Builder()
            .url(NYC_SCHOOLS_URL)
            .method("GET", null)
            .build()
        try {
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {}

                @Throws(IOException::class)
                override fun onResponse(call: Call, response: Response) {
                    val jsonData = response.body!!.string()
                    val listType = object : TypeToken<List<School?>?>() {}.type
                    val schools: List<School> = Gson().fromJson<List<School>>(jsonData, listType)
                    insertAll(schools)
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Fetches SATScores Data from NYC Schools API
     */
    fun fetchSATScores() {
        val client = OkHttpClient().newBuilder()
            .build()
        val request: Request = Request.Builder()
            .url(NYC_SCHOOLS_SAT_SCORES_URL)
            .method("GET", null)
            .build()
        try {
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {}

                @Throws(IOException::class)
                override fun onResponse(call: Call, response: Response) {
                    val jsonData = response.body!!.string()
                    val listType = object : TypeToken<List<SATScores?>?>() {}.type
                    val satScores: List<SATScores> =
                        Gson().fromJson(jsonData, listType)
                    insertAllScores(satScores)
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object {
        private const val NYC_SCHOOLS_URL = "https://data.cityofnewyork.us/resource/s3k6-pzi2.json"
        private const val NYC_SCHOOLS_SAT_SCORES_URL =
            "https://data.cityofnewyork.us/resource/f9bf-2cp4.json"

        @Volatile
        private var INSTANCE: SchoolRepository? = null

        /**
         * Singleton Instance
         * @param context
         * @return
         */
        fun getRepository(context: Context): SchoolRepository? {
            if (INSTANCE == null) {
                synchronized(SchoolRepository::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = SchoolRepository(context)
                    }
                }
            }
            return INSTANCE
        }
    }
}

