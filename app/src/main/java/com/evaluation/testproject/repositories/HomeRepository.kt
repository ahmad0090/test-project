package com.evaluation.testproject.repositories

import com.evaluation.testproject.listeners.RepositoryListener
import com.evaluation.testproject.network.APIKeys
import com.evaluation.testproject.network.RetrofitCalling
import com.evaluation.testproject.network.RetrofitController

class HomeRepository(private val repositoryListener: RepositoryListener) :
    BaseRepository(repositoryListener) {

    fun getHomeCategoriesApi(apiKey: String) {
        val key = APIKeys.categoryKey
        val apiService = RetrofitController.apiService.getCategories(apiKey)
        val retrofitCalling = RetrofitCalling(this, key, apiService)
        retrofitCalling.apiCalling()
    }

}