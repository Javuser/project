package com.example.project.service;

import com.example.project.entity.BalanceWheel;
import com.example.project.entity.ChatGPTRequest;
import com.example.project.entity.ChatGPTResponse;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RecommendationServiceImpl implements RecommendationService {

    @Value("${OPEN_AI_URL}")
    private String OPEN_AI_URL;

    @Value("${OPEN_AI_KEY}")
    private String OPEN_AI_KEY;

    public String getRecommendation(BalanceWheel balanceWheel) {
        // Create the list of messages for the request
        List<ChatGPTRequest.Message> messages = new ArrayList<>();
        messages.add(new ChatGPTRequest.Message("user", createPrompt(balanceWheel)));

        // Create ChatGPTRequest with the list of messages
        ChatGPTRequest chatGPTRequest = new ChatGPTRequest(messages);

        String url = OPEN_AI_URL;
        HttpPost post = new HttpPost(url);
        post.addHeader("Content-Type", "application/json");
        post.addHeader("Authorization", "Bearer " + OPEN_AI_KEY);

        Gson gson = new Gson();
        String body = gson.toJson(chatGPTRequest);

        log.info("Request body: " + body);

        try {
            final StringEntity entity = new StringEntity(body);
            post.setEntity(entity);

            try (CloseableHttpClient httpClient = HttpClients.custom().build()) {
                CloseableHttpResponse response = httpClient.execute(post);

                int statusCode = response.getStatusLine().getStatusCode();
                log.info("HTTP Status Code: " + statusCode);

                // If the status code is not 2xx, log the error and return an appropriate message
                if (statusCode != 200) {
                    log.error("Error: Received non-200 status code: {}", statusCode);
                    return "Failed to get recommendation. Please try again later.";
                }

                String responseBody = EntityUtils.toString(response.getEntity());
                log.info("Response body: " + responseBody);

                // Parse the response to a ChatGPTResponse object
                ChatGPTResponse chatGPTResponse = gson.fromJson(responseBody, ChatGPTResponse.class);

                // Ensure choices is not null and has at least one choice
                if (chatGPTResponse.getChoices() != null && !chatGPTResponse.getChoices().isEmpty()) {
                    // Return the content of all choices as a concatenated string
                    return chatGPTResponse.getChoices().stream()
                            .map(choice -> choice.getMessage().getContent())  // Access content through the message object
                            .collect(Collectors.joining("\n"));  // Each recommendation in new line
                } else {
                    log.error("No choices found in the response.");
                    return "No recommendations available.";
                }
            } catch (Exception e) {
                log.error("Error executing request: {}", e.getMessage(), e);
                return "Error executing request. Please try again later.";
            }
        } catch (Exception e) {
            log.error("Error creating request: {}", e.getMessage(), e);
            return "Error creating request. Please try again later.";
        }
    }

    // Validate the BalanceWheel values
    private boolean isValidBalanceWheel(BalanceWheel balanceWheel) {
        return balanceWheel.getHealth() >= 1 && balanceWheel.getHealth() <= 10 &&
                balanceWheel.getCareer() >= 1 && balanceWheel.getCareer() <= 10 &&
                balanceWheel.getRelationships() >= 1 && balanceWheel.getRelationships() <= 10 &&
                balanceWheel.getPersonalDevelopment() >= 1 && balanceWheel.getPersonalDevelopment() <= 10 &&
                balanceWheel.getFinances() >= 1 && balanceWheel.getFinances() <= 10;
    }

    private String createPrompt(BalanceWheel balanceWheel) {
        return String.format("Given the following values of Balance Wheel: Health: %d, Career: %d, Relationships: %d, Personal Development: %d, Finances: %d, " +
                        "provide recommendations for improving the balance in life. " +
                        "Where the scale for each is between 1 to 10, where 1 the lowest and 10 is the highest value, if needed you can attach photo or something else" +
                        "and provide detailed recommendation",
                balanceWheel.getHealth(),
                balanceWheel.getCareer(),
                balanceWheel.getRelationships(),
                balanceWheel.getPersonalDevelopment(),
                balanceWheel.getFinances());
    }

}
