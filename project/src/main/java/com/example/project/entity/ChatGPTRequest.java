package com.example.project.entity;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class ChatGPTRequest {
    private String model = "gpt-4o"; // Adjust model if needed
    private List<Message> messages;
    private double temperature = 1.0;

    @SerializedName(value = "max_tokens")
    private int maxTokens = 1000; // Adjusted to a lower, more cost-effective token limit

    // Constructor for creating a request with messages
    public ChatGPTRequest(List<Message> messages) {
        this.messages = messages;
    }

    // Inner class for Message to match the new API structure
    @Data
    public static class Message {
        private String role;
        private String content;

        public Message(String role, String content) {
            this.role = role;
            this.content = content;
        }
    }
}
