package com.example.ListStudentsWithRetrofitWithRxJava

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import io.reactivex.Scheduler
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_add_student.*
import java.lang.Exception

class AddStudentActivity : AppCompatActivity() {


    private lateinit var apiService: ApiService
    private lateinit var disposable: Disposable


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)

        title = "Add New Student"

        //Volley
        apiService = ApiService()


        //toolbar
        setSupportActionBar(toolbar_addStudent)


        //enableButtonBack
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_back_24px)


        //field student
        val course = et_addNewStudent_course.text
        val firstName = et_addNewStudent_firstName.text
        val lastName = et_addNewStudent_lastName.text
        val score = et_addNewStudent_score.text

        //press save
        fab_addNewStudent_save.setOnClickListener {
            //check not empty fields
            if (course?.isNotEmpty()!!
                && firstName?.isNotEmpty()!!
                && lastName?.isNotEmpty()!! && score?.isNotEmpty()!!
            ) {
                fab_addNewStudent_save.isEnabled = false
                apiService.saveStudent(
                    firstName = firstName.toString(),
                    lastName = lastName.toString(),
                    score = score.toString().toInt(),
                    course = course.toString(),


                    ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        object : SingleObserver<Student> {
                            override fun onSubscribe(d: Disposable) {
                                disposable = d

                            }

                            override fun onSuccess(student: Student) {
                                val intent = Intent()
                                intent.putExtra("student", student)
                                setResult(Activity.RESULT_OK, intent)
                                finish()
                            }

                            override fun onError(e: Throwable) {
                                Toast.makeText(
                                    this@AddStudentActivity,
                                    "خطای نامشخص",
                                    Toast.LENGTH_SHORT
                                ).show()
                                fab_addNewStudent_save.isEnabled = true
                            }

                        })


            } else {
                Toast.makeText(
                    this@AddStudentActivity,
                    "همه فیلد ها باید پر شوند!!",
                    Toast.LENGTH_SHORT
                ).show()

            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}