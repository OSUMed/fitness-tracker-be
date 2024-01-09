package com.srikanth.fitnesstrackerbe.service.workout;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.srikanth.fitnesstrackerbe.domain.User;
import com.srikanth.fitnesstrackerbe.repository.UserRepository;

@Service
public class TodayWorkoutTableService {

    @Autowired
    private UserRepository userRepository;

    // Other repositories autowired

    public void addWorkout(Map<String, Object> workoutData) {
        Integer userId = (Integer) workoutData.get("userId");
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        // Parse workout data and create Exercise instance
        // Create Set instances
        // Link to ExerciseDetail
        // Create or update TodaysWorkout
        // Save entities using repositories
    }
}