package com.rappytv.labygpt.api;

import com.google.gson.Gson;
import com.rappytv.labygpt.api.GPTMessage.GPTRole;
import net.labymod.api.util.I18n;
import net.labymod.api.util.io.web.request.Request;
import net.labymod.api.util.io.web.request.Request.Method;
import java.util.ArrayList;
import java.util.Map;
import java.util.function.Consumer;

public class GPTRequest {

    private final static Gson gson = new Gson();
    public static final ArrayList<GPTMessage> queryHistory = new ArrayList<>();

    public static void sendRequestAsync(String query, String key, String username,
        String model, String behavior, Consumer<ApiResponse> responseConsumer) {

        if(queryHistory.isEmpty()) {
            queryHistory.add(new GPTMessage(behavior, GPTRole.System, "System"));
        }
        queryHistory.add(new GPTMessage(query, GPTRole.User, username));

        Map<String, String> body = Map.of(
            "model", model,
            "messages", gson.toJson(queryHistory),
            "user", username
        );

        Request.ofGson(ResponseBody.class)
            .url("https://api.openai.com/v1/chat/completions")
            .method(Method.POST)
            .addHeader("Content-Type", "application/json")
            .addHeader("Authorization", "Bearer " + key)
            .json(body)
            .handleErrorStream()
            .async()
            .execute(response -> {
                boolean successful;
                String output = null;
                String error = null;
                ResponseBody responseBody = response.get();

                if(response.hasException()) {
                    successful = false;
                    error = response.exception().getLocalizedMessage();
                } else if(responseBody.error != null) {
                    error = responseBody.error.message;
                    if (error.isEmpty() && responseBody.error.code.equals("invalid_api_key")) {
                        error = I18n.translate("labygpt.messages.invalidBearer");
                    }
                    successful = false;
                } else if(responseBody.choices.isEmpty()) {
                    successful = false;
                } else {
                    GPTMessage message = responseBody.choices.getFirst().message;
                    output = message.content.replace("\n\n", "");
                    queryHistory.add(new GPTMessage(output, GPTRole.Assistant, username));
                    successful = true;
                }
                responseConsumer.accept(new ApiResponse(successful, output, error));
            });
    }

    public record ApiResponse(boolean successful, String output, String error) {

    }
}