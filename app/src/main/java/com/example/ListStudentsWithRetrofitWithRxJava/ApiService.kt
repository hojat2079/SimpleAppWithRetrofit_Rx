package com.example.ListStudentsWithRetrofitWithRxJava

import com.google.gson.JsonObject
import io.reactivex.Single
import okhttp3.Interceptor
import okhttp3.OkHttpClient

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

class ApiService() {
    private var retrofitApiService: RetrofitApiService

    companion object {
        private const val BASE_URL = "http://expertdevelopers.ir/api/v1/"
    }

    init {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(Interceptor { chain ->
                val oldRequest = chain.request()
                val newRequestBuilder = oldRequest.newBuilder()
                newRequestBuilder.addHeader("Accept", "Application/json")
                return@Interceptor chain.proceed(newRequestBuilder.build())
            }).build()


        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()

        retrofitApiService = retrofit.create(RetrofitApiService::class.java)

    }


    fun getStudent(): Single<ArrayList<Student>> {
        return retrofitApiService.getStudent()
    }

    fun saveStudent(
        firstName: String,
        lastName: String,
        score: Int,
        course: String,
    ): Single<Student> {

        val jsonObj = JsonObject().apply {
            addProperty("first_name", firstName)
            addProperty("last_name", lastName)
            addProperty("score", score)
            addProperty("course", course)
        }
        return retrofitApiService.saveStudent(jsonObj)

    }


}