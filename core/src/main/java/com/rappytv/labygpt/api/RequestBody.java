package com.rappytv.labygpt.api;

import java.util.ArrayList;

public class RequestBody {

    public String model;
    public ArrayList<GPTMessage> messages;
    public String user;

    public RequestBody(String model, ArrayList<GPTMessage> messages, String user) {
        this.model = model;
        this.messages = messages;
        this.user = user;
    }
}
