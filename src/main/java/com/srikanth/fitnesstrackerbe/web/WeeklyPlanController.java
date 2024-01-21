package com.srikanth.fitnesstrackerbe.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.srikanth.fitnesstrackerbe.domain.weekplan.DayPlan;
import com.srikanth.fitnesstrackerbe.domain.weekplan.WeeklyPlan;
import com.srikanth.fitnesstrackerbe.service.weekplan.WeeklyPlanService;

import java.util.List;

@RestController
public class WeeklyPlanController {

	private final WeeklyPlanService weeklyPlanService;

	@Autowired
	public WeeklyPlanController(WeeklyPlanService weeklyPlanService) {
		this.weeklyPlanService = weeklyPlanService;
	}

	@GetMapping("/weeklyplan")
	public WeeklyPlan getWeeklyPlan() {
		return weeklyPlanService.returnWeekPlan(null);
	}

	@PostMapping("/weeklyplan")
	public WeeklyPlan createWeeklyPlan(@RequestBody List<DayPlan> dayPlans) {
		WeeklyPlan weeklyPlan = weeklyPlanService.processIncomingDataToDomain(dayPlans);
		WeeklyPlan savedWeekPlan = weeklyPlanService.saveWeeklyPlan(weeklyPlan);
		return weeklyPlanService.returnWeekPlan(savedWeekPlan.getId());
	}

	@PutMapping("/weeklyplan")
	public WeeklyPlan putWeeklyPlan(@RequestBody List<DayPlan> dayPlans) {
		WeeklyPlan weeklyPlan = weeklyPlanService.processIncomingDataToDomain(dayPlans);
		weeklyPlanService.updateWeeklyPlan(weeklyPlan);
		return weeklyPlanService.returnWeekPlan(null);
	}

	@DeleteMapping("/weeklyplan")
	public WeeklyPlan deleteWeeklyPlan() {
		weeklyPlanService.deleteWeeklyPlan();
		return new WeeklyPlan();
	}
}
