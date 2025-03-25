package com.nekosunevr.labygpt-selfhost.api;

import com.google.gson.annotations.SerializedName;

public class GPTMessage {
    public String content;
    public GPTRole role;
    public String name;

    public GPTMessage(String content, GPTRole role, String name) {
        this.content = content;
        this.role = role;
        this.name = name;
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
