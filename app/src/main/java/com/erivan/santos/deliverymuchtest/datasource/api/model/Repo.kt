package com.erivan.santos.deliverymuchtest.datasource.api.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Repo (
    @Expose(serialize = false) @SerializedName("id") val id: Int,
    @Expose(serialize = false) @SerializedName("name") val name: String,
    @Expose(serialize = false) @SerializedName("full_name") val fullName: String,
    @Expose(serialize = false) @SerializedName("description") val description: String,
    @Expose(serialize = false) @SerializedName("owner") val owner: Owner
)