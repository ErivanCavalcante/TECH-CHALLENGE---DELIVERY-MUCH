package com.erivan.santos.deliverymuchtest.datasource.api.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Owner (
    @Expose(serialize = false) @SerializedName("avatar_url") val avatarUrl: String,
    @Expose(serialize = false) @SerializedName("login") val login: String,
)