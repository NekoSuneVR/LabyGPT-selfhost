package com.rappytv.labygpt.api;

import com.google.gson.annotations.SerializedName;

public enum GPTRole {
    @SerializedName("system")
    System,
    @SerializedName("user")
    User,
    @SerializedName("assistant")
    Assistant
}
