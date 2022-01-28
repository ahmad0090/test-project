package com.evaluation.testproject.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.evaluation.testproject.R
import com.evaluation.testproject.databinding.FragmentCategoryDetailBinding
import com.evaluation.testproject.models.category.CategoriesResponse
import com.evaluation.testproject.viewModels.CategoryDetailVM
import com.evaluation.testproject.viewModels.MainVM
import com.evaluation.testproject.views.adapters.CategoriesAdapter
import com.evaluation.testproject.views.adapters.SubCategoriesAdapter
import com.evaluation.testproject.views.fragments.base.BaseFragment
import com.evaluation.testproject.vmCallbacks.category.MainCallBack

class CategoriesDetailFragment : BaseFragment() {
    private val TAG = CategoriesDetailFragment::class.java.simpleName
    private lateinit var binding: FragmentCategoryDetailBinding

    var objData: ArrayList<CategoriesResponse.MediaMeta> = ArrayList()

    private val homeVM: MainVM by lazy {
        ViewModelProvider(this).get(
            MainVM::class.java
        )
    }

    private val categoriesAdapter: SubCategoriesAdapter by lazy {
        SubCategoriesAdapter(homeVM)
    }

    private val categoryDetailVM: MainVM by lazy {
        ViewModelProvider(this).get(
            MainVM::class.java
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments?.getString("screen") as String == "categoryDetail") {
            objData =
                arguments?.getSerializable("object") as ArrayList<CategoriesResponse.MediaMeta>
        }

    }

    override fun onResume() {
        super.onResume()
        homeListener.onHomeDataChangeListener(
            false,
            false,
            false,
            View.GONE,
            View.GONE,
            "",
            View.GONE,
            View.GONE,
            View.GONE
        )

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_category_detail, container, false)
            binding = FragmentCategoryDetailBinding.bind(mView!!)
                .apply {
                    this.lifecycleOwner = this@CategoriesDetailFragment
                    this.viewModel = categoryDetailVM
                }
        }
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categoryDetailVM.getFragmentCB().observe(viewLifecycleOwner, vmCallbackObserver)
        categoryDetailVM.getCallback().observe(viewLifecycleOwner, callbackObserver)

        init()

    }

    private val callbackObserver = Observer<MainCallBack> { callBack ->
        when (callBack) {
            MainCallBack.PopulateSubCategoriesData -> {
                populateAdapter()
            }
        }
    }

    private fun init(){

        binding.rvSubCategories.apply {
            adapter = categoriesAdapter
        }

        categoriesAdapter.renewItems(objData)
    }

    private fun populateAdapter() {
        binding.rvSubCategories.adapter?.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        categoryDetailVM.clearObservers(vmCallbackObserver)
        super.onDestroyView()
    }
}