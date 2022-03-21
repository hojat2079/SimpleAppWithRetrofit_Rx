package com.example.ListStudentsWithRetrofitWithRxJava

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_studnet.view.*
import java.util.*
import kotlin.collections.ArrayList

class StudentAdapter(private val list: ArrayList<Student>) :
    RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {
    inner class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val profileStudent = itemView.txt_student_profile
        private val fullName = itemView.txt_student_name
        private val course = itemView.txt_student_course
        private val score = itemView.txt_student_score

        fun onBindStudent(student: Student) {
            val name = student.firstName + " " + student.lastName
            fullName.text = name
            profileStudent.text = student.firstName[0].toString().toUpperCase(Locale.ROOT)
            course.text = student.course
            score.text = student.score.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        return StudentViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_studnet, parent, false)
        )
    }


    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        holder.onBindStudent(list[position])
    }

    fun addStudent(student: Student):Int {
        list.add(student)
        notifyItemInserted(list.size)
        return list.size
    }

    override fun getItemCount(): Int = list.size
}