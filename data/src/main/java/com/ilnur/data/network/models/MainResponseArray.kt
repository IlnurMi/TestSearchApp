package com.ilnur.data.network.models

import com.google.gson.annotations.SerializedName

class MainResponseArray<T: IPojo.JsonArray> {
    @SerializedName("total_count")
    var totalCount: Int? = null
    @SerializedName("incomplete_results")
    var incompleteResults: Boolean? = null
    @SerializedName("items")
    var items: List<T> = emptyList()
}