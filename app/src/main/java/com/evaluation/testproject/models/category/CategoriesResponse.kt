package com.evaluation.testproject.models.category

import com.evaluation.testproject.models.BaseResponse2
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class CategoriesResponse : BaseResponse2<CategoriesResponse.Results>() {

    class Results(
        var uri: String,
        var url: String,
        var source: String,
        var published_date: String,
        var updated: String,
        var section: String,
        var subsection: String,
        var adx_keywords: String,
        var title: String,

        @SerializedName("media")
        val mediaCategories: ArrayList<MediaCategories>
    )

    class MediaCategories(
        var type: String,
        var subtype: String,
        var caption: String,
        var copyright: String,
        var approved_for_syndication: Int,

        @SerializedName("media-metadata")
        val mediaMeta: ArrayList<MediaMeta>

    )

    class MediaMeta(
        var url: String,
        var format: String,
        var height: Int,
        var width: Int

    ): Serializable
}