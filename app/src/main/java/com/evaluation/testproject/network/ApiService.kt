package com.evaluation.testproject.network

import com.evaluation.testproject.models.category.CategoriesResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*


interface ApiService {

    //Categories
    @GET(APILinks.MOST_VIEWED_API)
    fun getCategories(
        @Query("api-key") apikey: String
    ): Call<CategoriesResponse>

}
