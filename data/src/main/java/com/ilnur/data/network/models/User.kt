package com.ilnur.data.network.models

import com.google.gson.annotations.SerializedName

class User(
    @SerializedName("login")
    val login: String,
    @SerializedName("avatar_url")
    val avatarUrl: String
) : IPojo.JsonArray