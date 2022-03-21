package com.example.ListStudentsWithRetrofitWithRxJava

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class Student() :Parcelable {
    private var id: Int? = null
    @SerializedName("first_name")
    lateinit var firstName: String
    @SerializedName("last_name")
    lateinit var lastName: String
    lateinit var course: String
    var score: Int? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readValue(Int::class.java.classLoader) as? Int
        firstName = parcel.readString().toString()
        lastName = parcel.readString().toString()
        course = parcel.readString().toString()
        score = parcel.readValue(Int::class.java.classLoader) as? Int
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(firstName)
        parcel.writeString(lastName)
        parcel.writeString(course)
        parcel.writeValue(score)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Student> {
        override fun createFromParcel(parcel: Parcel): Student {
            return Student(parcel)
        }

        override fun newArray(size: Int): Array<Student?> {
            return arrayOfNulls(size)
        }
    }

}