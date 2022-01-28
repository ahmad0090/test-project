package com.evaluation.testproject.viewModels

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.evaluation.testproject.R
import com.evaluation.testproject.helpers.ResourceProvider
import com.evaluation.testproject.helpers.Utils
import com.evaluation.testproject.listeners.RepositoryListener
import com.evaluation.testproject.models.category.CategoriesResponse
import com.evaluation.testproject.network.APIKeys
import com.evaluation.testproject.repositories.HomeRepository
import com.evaluation.testproject.viewModels.base.BaseFragmentVM
import com.evaluation.testproject.views.fragments.CategoriesDetailFragment
import com.evaluation.testproject.vmCallbacks.FragmentVMCallback
import com.evaluation.testproject.vmCallbacks.category.MainCallBack

class MainVM : BaseFragmentVM(), RepositoryListener {
    private val TAG = MainVM::class.java.simpleName

    private val homeRepository = HomeRepository(this)
    private var objData: CategoriesResponse.MediaMeta? = null

    private val callback: MutableLiveData<MainCallBack> by lazy {
        MutableLiveData<MainCallBack>()
    }

    val subCategoryResponse: MutableLiveData<CategoriesResponse> by lazy {
        MutableLiveData<CategoriesResponse>()
    }


    fun getHomeCategoriesAPi() {
        if (Utils.isNetworkAvailable()) {
            vmFragmentCB.value = FragmentVMCallback.showProgressBar(true)
            homeRepository.getHomeCategoriesApi(ResourceProvider.getString(R.string.API_KEY))
        }
    }

    fun onCategoryClicked(response: ArrayList<CategoriesResponse.MediaMeta>) {
        val bundle = Bundle()
        bundle.putString("screen", "categoryDetail")
        bundle.putSerializable("object", response)
        val fragment = CategoriesDetailFragment()
        fragment.arguments = bundle
        vmFragmentCB.value = FragmentVMCallback.onFragmentChanged(fragment)
    }

    fun getCallback(): LiveData<MainCallBack> {
        return callback
    }

    override fun <T : Any> onSuccessResponse(key: String, result: T) {
        super.onSuccessResponse(key, result)
        when (key) {
            APIKeys.categoryKey -> {
                getHomeCategoriesResponse(result as CategoriesResponse)
            }
        }
    }

    private fun getHomeCategoriesResponse(categoriesResponse: CategoriesResponse) {

        if (categoriesResponse.status == "OK") {
            this.subCategoryResponse.value = categoriesResponse
            callback.value = MainCallBack.PopulateData
        }
    }
}