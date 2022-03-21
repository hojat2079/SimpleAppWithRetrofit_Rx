package com.example.ListStudentsWithRetrofitWithRxJava

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var studentAdapter: StudentAdapter
    private lateinit var apiService: ApiService
    private lateinit var disposable: Disposable

    companion object {
        private const val TAG = "MainActivity"
        private const val ADD_STUDENT_REQUEST_ID = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        apiService = ApiService()
        setSupportActionBar(toolbar_main)
        fab_main_addStudent.setOnClickListener {
            startActivityForResult(
                Intent(this, AddStudentActivity::class.java),
                MainActivity.ADD_STUDENT_REQUEST_ID
            )
        }
        apiService.getStudent()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<ArrayList<Student>> {
                override fun onSubscribe(d: Disposable) {
                    disposable = d
                }

                override fun onSuccess(students: ArrayList<Student>) {
                    studentAdapter = StudentAdapter(students)
                    rv_main_student.adapter = studentAdapter
                    rv_main_student.layoutManager =
                        LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
                }


                override fun onError(e: Throwable) {
                    Toast.makeText(this@MainActivity, "خطای نا مشخص", Toast.LENGTH_SHORT).show()

                }

            })


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == ADD_STUDENT_REQUEST_ID && resultCode == Activity.RESULT_OK) {
            val student = data?.getParcelableExtra<Student>("student")
            val index = studentAdapter.addStudent(student!!)
            rv_main_student.smoothScrollToPosition(index)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}