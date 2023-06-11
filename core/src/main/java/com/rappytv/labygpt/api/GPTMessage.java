package com.rappytv.labygpt.api;

public class GPTMessage {
    public String content;
    public GPTRole role;
    public String name;

    public GPTMessage(String content, GPTRole role, String name) {
        this.content = content;
        this.role = role;
        this.name = name;
    }
}
