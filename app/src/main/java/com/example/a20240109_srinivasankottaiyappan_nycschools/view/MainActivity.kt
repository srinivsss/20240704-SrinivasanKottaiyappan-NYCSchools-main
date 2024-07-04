package com.example.a20240109_srinivasankottaiyappan_nycschools.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a20240109_srinivasankottaiyappan_nycschools.R
import com.example.a20240109_srinivasankottaiyappan_nycschools.adapter.SchoolListAdapter
import com.example.a20240109_srinivasankottaiyappan_nycschools.databinding.ActivityMainBinding
import com.example.a20240109_srinivasankottaiyappan_nycschools.viewmodel.SchoolViewModel
import java.util.Locale

class MainActivity : AppCompatActivity(),SearchView.OnQueryTextListener {

    lateinit var mSchoolViewModel: SchoolViewModel
    private var adapter: SchoolListAdapter? = null
    lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = SchoolListAdapter()
        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = LinearLayoutManager(this)
        mSchoolViewModel = ViewModelProvider(this).get(SchoolViewModel::class.java)

        mSchoolViewModel.allSchools.observe(this, { schoolsList->
            adapter?.updateList(schoolsList)
        })
        mSchoolViewModel.loadSchools()
    }

    @SuppressLint("ResourceType")
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.layout.menu, menu)
        val search = menu.findItem(R.id.action_search)
        val searchView = search.actionView as SearchView?
        searchView!!.setOnQueryTextListener(this)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.menu_refresh) {
            mSchoolViewModel.loadSchools()
            return true
        } else if (id == R.id.action_search) {
            val searchView = item.actionView as SearchView?
            searchView!!.setOnQueryTextListener(this)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String): Boolean {
        var newText = newText
        newText = "%" + newText.lowercase(Locale.getDefault()) + "%"
        mSchoolViewModel.getFilteredSchools(newText).observe(this, { filteredList->
            adapter?.updateList(filteredList)
        })
        return true
    }
}
