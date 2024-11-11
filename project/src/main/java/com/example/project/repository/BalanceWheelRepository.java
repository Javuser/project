package com.example.project.repository;


import com.example.project.entity.BalanceWheel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BalanceWheelRepository extends JpaRepository<BalanceWheel, Long> {
    BalanceWheel findByUserId(Long userId);
}
