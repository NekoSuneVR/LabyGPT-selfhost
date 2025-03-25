package com.nekosunevr.labygpt.api;

import com.google.gson.annotations.SerializedName;

public class GPTMessage {
    public String content;
    public GPTRole role;
    public String name;

    public GPTMessage(String content, GPTRole role, String name) {
        this.content = content;
        this.role = role;
    }

    public enum GPTRole {
        @SerializedName("system")
        System,
        @SerializedName("user")
        User,
        @SerializedName("assistant")
        Assistant
    }
}
