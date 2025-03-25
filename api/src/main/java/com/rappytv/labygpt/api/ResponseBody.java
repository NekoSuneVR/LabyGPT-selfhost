package com.nekosunevr.labygpt-selfhost.api;

import java.util.ArrayList;

public class ResponseBody {

    public ArrayList<Choice> choices;
    public Error error;

    public static class Choice {
        public GPTMessage message;
    }
    public static class Error {
        public String message;
        public String code;
    }
}
