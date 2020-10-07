package com.erivan.santos.deliverymuchtest.datasource.api.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Query (
    @Expose(serialize = false) @SerializedName("total_count") val count: Long,
    @Expose(serialize = false) @SerializedName("items") val items: List<Repo>
)