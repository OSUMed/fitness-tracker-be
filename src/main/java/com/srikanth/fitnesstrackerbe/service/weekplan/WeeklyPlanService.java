package com.srikanth.fitnesstrackerbe.service.weekplan;

import com.srikanth.fitnesstrackerbe.domain.weekplan.WeeklyPlan;
import com.srikanth.fitnesstrackerbe.domain.weekplan.DayPlan;
import com.srikanth.fitnesstrackerbe.domain.weekplan.PlannedWorkout;
import com.srikanth.fitnesstrackerbe.domain.weekplan.PlannedWorkout.WorkoutExercise;
import com.srikanth.fitnesstrackerbe.repository.weekplan.WeeklyPlanRepository;

import jakarta.persistence.EntityNotFoundException;

import com.srikanth.fitnesstrackerbe.repository.weekplan.DayPlanRepository;
import com.srikanth.fitnesstrackerbe.repository.weekplan.PlannedWorkoutRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WeeklyPlanService {

    private final WeeklyPlanRepository weeklyPlanRepository;
    private final DayPlanRepository dayPlanRepository;
    private final PlannedWorkoutRepository plannedWorkoutRepository;

    @Autowired
    public WeeklyPlanService(WeeklyPlanRepository weeklyPlanRepository,
                             DayPlanRepository dayPlanRepository,
                             PlannedWorkoutRepository plannedWorkoutRepository) {
        this.weeklyPlanRepository = weeklyPlanRepository;
        this.dayPlanRepository = dayPlanRepository;
        this.plannedWorkoutRepository = plannedWorkoutRepository;
    }
    
    @Transactional
    public WeeklyPlan processIncomingDataToDomain(List<DayPlan> incomingDayPlans) {
        WeeklyPlan weeklyPlan = new WeeklyPlan();
        for (DayPlan dayPlan : incomingDayPlans) {
            if (dayPlan.getId() != null && dayPlan.getId() == 0) {
                dayPlan.setId(null); 
            }
            dayPlan.setWeeklyPlan(weeklyPlan); 
            for (PlannedWorkout workout : dayPlan.getWorkouts()) {
                if (workout.getId() != null && workout.getId() == 0) {
                    workout.setId(null); 
                }
                workout.setDayPlan(dayPlan); 
            }
            weeklyPlan.getDayPlans().add(dayPlan);
        }
        return weeklyPlan;
    }



    @Transactional
    public WeeklyPlan saveWeeklyPlan(WeeklyPlan weeklyPlan) {
        // Saving WeeklyPlan will also save DayPlans and PlannedWorkouts
        return weeklyPlanRepository.save(weeklyPlan);
    }

    @Transactional
    public WeeklyPlan returnWeekPlan() {
        List<WeeklyPlan> weeklyPlans = weeklyPlanRepository.findAll();
        if (weeklyPlans.isEmpty()) {
            return new WeeklyPlan();
        }
        WeeklyPlan thisWeekPlan = weeklyPlans.get(0);

        thisWeekPlan.getDayPlans().size(); 

        System.out.println("Returned week plan is: " + thisWeekPlan);
        return thisWeekPlan;
    }


	public WeeklyPlan deleteWeeklyPlan() {
		// TODO Auto-generated method stub
		weeklyPlanRepository.deleteAll();
		return returnWeekPlan();
	}

	public void updateWeeklyPlan(WeeklyPlan weeklyPlan) {
		// TODO Auto-generated method stub
		
	}
}
