package com.evaluation.testproject.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.evaluation.testproject.app.MyApplication
import com.evaluation.testproject.databinding.ItemAddressBinding
import com.evaluation.testproject.databinding.ItemSubCategoriesBinding
import com.evaluation.testproject.models.category.CategoriesResponse
import com.evaluation.testproject.viewModels.MainVM
import java.util.ArrayList

class SubCategoriesAdapter (private val orderHistoryVM: MainVM) :
    RecyclerView.Adapter<SubCategoriesAdapter.ViewHolder>() {
    var selectedPosition = RecyclerView.NO_POSITION

    private var mSliderItems: ArrayList<CategoriesResponse.MediaMeta> =
        ArrayList<CategoriesResponse.MediaMeta>()

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding =
            ItemSubCategoriesBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    fun renewItems(sliderItems: ArrayList<CategoriesResponse.MediaMeta>?) {
        mSliderItems = sliderItems!!
        notifyDataSetChanged()
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(
            position,
            mSliderItems[position]
        )
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return mSliderItems.size
    }

    //the class is holding the list view
    inner class ViewHolder(private val binding: ItemSubCategoriesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int,categoriesResponse: CategoriesResponse.MediaMeta) {

            binding.position = position
            binding.viewModel = orderHistoryVM
//            binding.categoryData = mSliderItems

            Glide.with(MyApplication.applicationContext())
                .load(mSliderItems.get(position).url)
                .into(binding.ivRecipy)

            binding.tvCategoryName.text = mSliderItems.get(position).format
            binding.tvDate.text = mSliderItems.get(position).height.toString()
            binding.tvTime.text = mSliderItems.get(position).width.toString()

            binding.executePendingBindings()
        }
    }
}