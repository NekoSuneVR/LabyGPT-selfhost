package com.rappytv.labygpt.api;

import com.google.gson.Gson;
import com.rappytv.labygpt.GPTAddon;
import com.rappytv.labygpt.api.ResponseBody.Choice;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;

public class GPTRequest {

    public final boolean successful;
    public String output;

    public GPTRequest(String query, String key, String username) {
        Gson gson = new Gson();
        boolean success;

        try {
            if(GPTAddon.queryHistory.isEmpty())
                GPTAddon.queryHistory.add(new GPTMessage("You are a helpful assistant.", GPTRole.System, "System"));
            GPTAddon.queryHistory.add(new GPTMessage(query, GPTRole.User, username));
            RequestBody apiRequestBody = new RequestBody("gpt-3.5-turbo", GPTAddon.queryHistory, username);

            HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://api.openai.com/v1/chat/completions"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + key)
                .POST(BodyPublishers.ofString(gson.toJson(apiRequestBody)))
                .build();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

            System.out.println(response.body() + " " + response.statusCode());

            ResponseBody responseBody = gson.fromJson(response.body(), ResponseBody.class);
            GPTMessage message = responseBody.choices.get(0).message;
            output = message.content.replace("\n\n", "");
            GPTAddon.queryHistory.add(new GPTMessage(message.content, GPTRole.Assistant, "LabyGPT"));
            System.out.println(GPTAddon.queryHistory);
            success = true;
        } catch (IOException | InterruptedException | URISyntaxException e) {
            e.printStackTrace();
            success = false;
        }

        successful = success;
    }
}
