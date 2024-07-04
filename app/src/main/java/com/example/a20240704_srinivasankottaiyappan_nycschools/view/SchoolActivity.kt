package com.example.a20240109_srinivasankottaiyappan_nycschools.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import com.example.a20240109_srinivasankottaiyappan_nycschools.R
import com.example.a20240109_srinivasankottaiyappan_nycschools.SchoolUtil.displayScore
import com.example.a20240109_srinivasankottaiyappan_nycschools.databinding.ActivityNewSchoolBinding
import com.example.a20240109_srinivasankottaiyappan_nycschools.model.SATScores
import com.example.a20240109_srinivasankottaiyappan_nycschools.model.School
import com.example.a20240109_srinivasankottaiyappan_nycschools.viewmodel.SATScoresViewModel

class SchoolActivity : AppCompatActivity() {

    lateinit var school: School
    lateinit var satScoresViewModel: SATScoresViewModel
    lateinit var binding: ActivityNewSchoolBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewSchoolBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        //School object to setup the page
        if(intent.hasExtra("School")) {
            school = intent.getSerializableExtra("School") as School
        }
        val schoolNameTextView = binding.schoolNameText
        val descTextView = binding.descriptionText
        schoolNameTextView.text = school.school_name
        schoolNameTextView.isSingleLine = false
        schoolNameTextView.setHorizontallyScrolling(false)
        descTextView.text = school.overview_paragraph
        descTextView.isSingleLine = false
        descTextView.setHorizontallyScrolling(false)

        // Loads SAT Scores for the School
        satScoresViewModel = ViewModelProvider(this).get(SATScoresViewModel::class.java)
        val score: LiveData<SATScores> = satScoresViewModel.getScoresForSchool(school.dbn)
        score.observe(
            this
        ) { satScores -> satScoresUpdated(satScores) }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
    /**
     * Updates the SAT Scores once available from DB
     * @param score
     */
    private fun satScoresUpdated(score: SATScores?) {
        if (score != null) {
            binding.apply {
                readingScoreTextView.text = displayScore(score.sat_critical_reading_avg_score,getString(R.string.reading_score))
                writingScoreTextView.text = displayScore(score.sat_writing_avg_score,getString(R.string.writing_score))
                mathScoreTextView.text = displayScore(score.sat_math_avg_score,getString(R.string.math_score))
            }
        }
    }


}