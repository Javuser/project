package com.example.project.controller;


import com.example.project.entity.BalanceWheel;
import com.example.project.entity.User;
import com.example.project.service.BalanceWheelService;
import com.example.project.service.BalanceWheelServiceImpl;
import com.example.project.service.RecommendationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/balance-category")
public class BalanceWheelController {

    private final BalanceWheelServiceImpl balanceWheelService;
    private final RecommendationServiceImpl recommendationService;

    @Autowired
    public BalanceWheelController(BalanceWheelServiceImpl balanceWheelService, RecommendationServiceImpl recommendationService) {
        this.balanceWheelService = balanceWheelService;
        this.recommendationService = recommendationService;
    }

    @PostMapping("/submit")
    public ResponseEntity<String> submitBalanceWheel(@RequestBody BalanceWheel balanceWheel) {
        balanceWheelService.saveBalanceWheel(balanceWheel);
        return ResponseEntity.ok("Данные колеса баланса успешно сохранены.");
    }

    @GetMapping("/{userId}")
    public ResponseEntity<BalanceWheel> getBalanceWheel(@PathVariable Long userId) {
        BalanceWheel balanceWheel = balanceWheelService.getBalanceWheelByUserId(userId); // Предполагается, что есть конструктор
        return ResponseEntity.ok(balanceWheel);
    }

    // New endpoint for getting recommendations
    @PostMapping("/recommendation")
    public ResponseEntity<String> getRecommendation(@RequestBody BalanceWheel balanceWheel) {
        String recommendation = recommendationService.getRecommendation(balanceWheel);

        // Log the generated recommendation
        System.out.println("Generated Recommendation: " + recommendation);
        return ResponseEntity.ok(recommendation);
    }

}