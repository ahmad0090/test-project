package com.evaluation.testproject.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

open class BaseResponse2<T>(t: ArrayList<T>? = null) : Serializable {
    @SerializedName("status")
    var status: String = ""
    @SerializedName("copyright")
    var copyright: String = ""
    @SerializedName("num_results")
    var num_results: Int = 0
    @SerializedName("results")
    var results: ArrayList<T>? = t
}



