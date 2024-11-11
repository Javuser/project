package com.example.project.service;


import com.example.project.entity.BalanceWheel;
import com.example.project.entity.User;
import com.example.project.repository.BalanceWheelRepository;
import com.example.project.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BalanceWheelServiceImpl implements BalanceWheelService {

    private final BalanceWheelRepository balanceWheelRepository;
    private final UserRepository userRepository;

    public BalanceWheelServiceImpl(BalanceWheelRepository balanceWheelRepository, UserRepository userRepository) {
        this.balanceWheelRepository = balanceWheelRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<BalanceWheel> getBalanceWheel() {
        return balanceWheelRepository.findAll();
    }

    public void saveBalanceWheel(BalanceWheel balanceWheel) {
        balanceWheelRepository.save(balanceWheel);
    }

    public BalanceWheel getBalanceWheelByUserId(Long userId) {
        return balanceWheelRepository.findByUserId(userId);
    }

}
//
////    @Value("${openai.api.key}")
////    private String openAiApiKey;
//
//    private final RestTemplate restTemplate = new RestTemplate();
//
//    @Override
//    public Map<String, String> getRecommendationsForUser(Long userId) {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        List<BalanceWheel> categories = user.getBalanceCategories();
//
//        // Подготовка промпта для API на основе категорий пользователя
//        String prompt = createPromptFromCategories(categories);
//
//        // Запрос к API OpenAI для генерации рекомендаций
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
////        headers.setBearerAuth(openAiApiKey);
//
//        Map<String, Object> requestBody = new HashMap<>();
//        requestBody.put("model", "text-davinci-003");
//        requestBody.put("prompt", prompt);
//        requestBody.put("max_tokens", 150);
//
//        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
//        ResponseEntity<Map> response = restTemplate.postForEntity(
//                "https://api.openai.com/v1/completions",
//                request,
//                Map.class
//        );
//
//        // Получение текста рекомендаций из ответа API
//        Map<String, String> recommendations = new HashMap<>();
//        String advice = (String) ((Map<String, Object>) response.getBody().get("choices")).get("text");
//        recommendations.put("Advice", advice);
//
//        return recommendations;
//    }
//
//    private String createPromptFromCategories(List<BalanceWheel> categories) {
//        StringBuilder prompt = new StringBuilder("Provide self-coaching recommendations based on the following categories:\n");
//        for (BalanceWheel category : categories) {
//            prompt.append(category.getName()).append(": ").append(category.getPoints()).append("/10\n");
//        }
//        prompt.append("Recommendations:");
//        return prompt.toString();
//    }


