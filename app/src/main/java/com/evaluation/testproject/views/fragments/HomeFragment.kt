package com.evaluation.testproject.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.evaluation.testproject.R
import com.evaluation.testproject.databinding.FragmentHomeBinding
import com.evaluation.testproject.viewModels.MainVM
import com.evaluation.testproject.views.adapters.CategoriesAdapter
import com.evaluation.testproject.views.fragments.base.BaseFragment
import com.evaluation.testproject.vmCallbacks.category.MainCallBack

class HomeFragment : BaseFragment() {
    private val TAG = HomeFragment::class.java.simpleName
    private lateinit var binding: FragmentHomeBinding

    private val homeVM: MainVM by lazy {
        ViewModelProvider(this).get(
            MainVM::class.java
        )
    }

    private val categoriesAdapter: CategoriesAdapter by lazy {
        CategoriesAdapter(homeVM)
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

        homeVM.getHomeCategoriesAPi()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_home, container, false)
            binding = FragmentHomeBinding.bind(mView!!)
                .apply {
                    this.lifecycleOwner = this@HomeFragment
                    this.viewModel = homeVM
                }
        }
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeVM.getFragmentCB().observe(viewLifecycleOwner, vmCallbackObserver)
        homeVM.getCallback().observe(viewLifecycleOwner, callbackObserver)

        init()

    }

    private val callbackObserver = Observer<MainCallBack> { callBack ->
        when (callBack) {
            MainCallBack.PopulateData -> {
                populateAdapter()
            }
        }
    }

    private fun init(){

        binding.rvCategories.apply {
            adapter = categoriesAdapter
        }
    }

    private fun populateAdapter() {
        binding.rvCategories.adapter?.notifyDataSetChanged()
    }


    override fun onDestroyView() {
        homeVM.clearObservers(vmCallbackObserver)
        super.onDestroyView()
    }
}