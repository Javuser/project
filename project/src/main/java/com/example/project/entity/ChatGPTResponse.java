package com.example.project.entity;

import lombok.Data;
import java.util.List;

@Data
public class ChatGPTResponse {
    private List<Choice> choices;

    @Data
    public static class Choice {
        Message message;
    }

    @Data
    public static class Message {
        private String role;  // "assistant"
        private String content;  // The response content
    }
}
