package com.example.ListStudentsWithRetrofitWithRxJava

import com.google.gson.JsonObject
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RetrofitApiService {
    @GET("experts/student")
    fun getStudent(): Single<ArrayList<Student>>

    @POST("experts/student")
    fun saveStudent(@Body jsonObj: JsonObject):Single<Student>
}