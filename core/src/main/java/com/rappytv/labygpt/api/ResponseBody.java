package com.rappytv.labygpt.api;

import java.util.ArrayList;

public class ResponseBody {

    public ArrayList<Choice> choices;
    public Error error;

    public static class Choice {
        public GPTMessage message;
    }
    private static class Error {
        public String message;
    }
}
