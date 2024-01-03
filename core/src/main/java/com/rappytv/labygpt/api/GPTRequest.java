package com.rappytv.labygpt.api;

import com.google.gson.Gson;
import com.rappytv.labygpt.GPTAddon;
import net.labymod.api.util.I18n;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.concurrent.CompletableFuture;

public class GPTRequest {

    private boolean successful;
    private String output;
    private String error;

    public CompletableFuture<Void> sendRequestAsync(String query, String key, String username,
        String model, String behavior) {
        Gson gson = new Gson();
        CompletableFuture<Void> future = new CompletableFuture<>();

        try {
            if (GPTAddon.queryHistory.isEmpty()) {
                GPTAddon.queryHistory.add(new GPTMessage(behavior, GPTRole.System, "System"));
            }
            GPTAddon.queryHistory.add(new GPTMessage(query, GPTRole.User, username));
            RequestBody apiRequestBody = new RequestBody(model, GPTAddon.queryHistory, username);

            HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://api.openai.com/v1/chat/completions"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + key)
                .POST(BodyPublishers.ofString(gson.toJson(apiRequestBody)))
                .build();

            HttpClient client = HttpClient.newHttpClient();
            client.sendAsync(request, BodyHandlers.ofString())
                .thenAccept(response -> {
                    ResponseBody responseBody = gson.fromJson(response.body(), ResponseBody.class);

                    if (responseBody.error != null) {
                        error = responseBody.error.message;
                        if (error.isEmpty() && responseBody.error.code.equals("invalid_api_key")) {
                            error = I18n.translate("labygpt.messages.invalidBearer");
                        }
                        successful = false;
                    } else if (responseBody.choices.isEmpty()) {
                        successful = false;
                    } else {
                        GPTMessage message = responseBody.choices.get(0).message;
                        output = message.content.replace("\n\n", "");
                        GPTAddon.queryHistory.add(
                            new GPTMessage(message.content, GPTRole.Assistant, "LabyGPT"));
                        successful = true;
                    }

                    future.complete(null);
                }).exceptionally((e) -> {
                    future.completeExceptionally(e);
                    error = e.getMessage();
                    return null;
                });
        } catch (Exception e) {
            e.printStackTrace();
            future.completeExceptionally(e);
            error = e.getMessage();
        }

        return future;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public String getOutput() {
        return output;
    }

    public String getError() {
        return error;
    }
}