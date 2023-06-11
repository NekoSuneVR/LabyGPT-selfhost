package com.rappytv.labygpt.api;

import com.google.gson.Gson;
import com.rappytv.labygpt.GPTAddon;
import net.labymod.api.util.I18n;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

public class GPTRequest {

    private boolean successful;
    private String output;
    private String error;

    public GPTRequest(String query, String key, String username) {
        Gson gson = new Gson();

        try {
            if(GPTAddon.queryHistory.isEmpty())
                GPTAddon.queryHistory.add(new GPTMessage("You are a helpful assistant.", GPTRole.System, "System"));
            GPTAddon.queryHistory.add(new GPTMessage(query, GPTRole.User, username));
            RequestBody apiRequestBody = new RequestBody("gpt-3.5-turbo", GPTAddon.queryHistory, username);

            HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:2000"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + key)
                .POST(BodyPublishers.ofString(gson.toJson(apiRequestBody)))
                .build();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

            ResponseBody responseBody = gson.fromJson(response.body(), ResponseBody.class);

            if(responseBody.error != null) {
                error = responseBody.error.message;
                if(error.isEmpty() && responseBody.error.code.equals("invalid_api_key"))
                    error = I18n.translate("labygpt.messages.invalidBearer");
                successful = false;
                return;
            }
            if(responseBody.choices.size() < 1) {
                successful = false;
                return;
            }

            GPTMessage message = responseBody.choices.get(0).message;
            output = message.content.replace("\n\n", "");
            GPTAddon.queryHistory.add(new GPTMessage(message.content, GPTRole.Assistant, "LabyGPT"));
            successful = true;
        } catch (IOException | InterruptedException | URISyntaxException e) {
            e.printStackTrace();
            successful = false;
        }
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
