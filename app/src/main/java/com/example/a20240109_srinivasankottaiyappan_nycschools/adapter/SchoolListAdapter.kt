package com.example.a20240109_srinivasankottaiyappan_nycschools.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.a20240109_srinivasankottaiyappan_nycschools.databinding.SchoolsRecyclerviewItemBinding
import com.example.a20240109_srinivasankottaiyappan_nycschools.model.School
import com.example.a20240109_srinivasankottaiyappan_nycschools.view.SchoolActivity

class SchoolListAdapter() : RecyclerView.Adapter<SchoolListAdapter.MyViewHolder>() {
    private lateinit var schoolBinding : SchoolsRecyclerviewItemBinding
    private var schoolList : List<School> = listOf()
    fun updateList(list: List<School>){
        this.schoolList = list
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        schoolBinding = SchoolsRecyclerviewItemBinding.inflate(LayoutInflater.from(parent.context))
        schoolBinding.listItem.layoutParams = LinearLayout.LayoutParams(
            MATCH_PARENT, WRAP_CONTENT)
        return MyViewHolder(schoolBinding)
    }

    class MyViewHolder(val schoolBinding: SchoolsRecyclerviewItemBinding) : RecyclerView.ViewHolder(schoolBinding.root) {
        fun bindData(school: School) {
            schoolBinding.textView.text = school.school_name
        }

    }


    override fun getItemCount(): Int {
       return schoolList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindData(schoolList.get(position))
        holder.itemView.setOnClickListener {view ->
            val intent = Intent(
                view.getContext(),
                SchoolActivity::class.java
            )
            intent.putExtra("School", schoolList.get(position))
            view.getContext().startActivity(intent)
        }
    }

}