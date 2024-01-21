package com.srikanth.fitnesstrackerbe.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.srikanth.fitnesstrackerbe.dao.workout.TodaysWorkoutDTO;
import com.srikanth.fitnesstrackerbe.domain.weekplan.DayPlan;
import com.srikanth.fitnesstrackerbe.domain.weekplan.WeeklyPlan;
import com.srikanth.fitnesstrackerbe.service.weekplan.WeeklyPlanService;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/weekplan")
public class WeeklyPlanController {

	private final WeeklyPlanService weeklyPlanService;

	@Autowired
	public WeeklyPlanController(WeeklyPlanService weeklyPlanService) {
		this.weeklyPlanService = weeklyPlanService;
	}

	@GetMapping("/test")
	public ResponseEntity<String> testWeeklyPlan() {
		System.out.println("getWeeklyPlan: ");
		return ResponseEntity.ok("Weekly Plan Test endpoint from eclipse!");
	}
	@GetMapping("/")
	public ResponseEntity<WeeklyPlan> getWeeklyPlan() {
		System.out.println("getWeeklyPlan: ");
		return ResponseEntity.ok(weeklyPlanService.returnWeekPlan());
	}

	@PostMapping("/")
	public ResponseEntity<WeeklyPlan> createWeeklyPlan(@RequestBody List<DayPlan> dayPlans) {
		System.out.println("createWeeklyPlan: " + dayPlans);
		WeeklyPlan weeklyPlan = weeklyPlanService.processIncomingDataToDomain(dayPlans);
		System.out.println("createWeeklyPlan weeklyPlan: " + weeklyPlan);
		WeeklyPlan savedWeekPlan = weeklyPlanService.saveWeeklyPlan(weeklyPlan);
		System.out.println("createWeeklyPlan weeklyPlan: " + savedWeekPlan);
		return ResponseEntity.ok(weeklyPlanService.returnWeekPlan());
	}

	@PutMapping("/")
	public ResponseEntity<WeeklyPlan> putWeeklyPlan(@RequestBody List<DayPlan> dayPlans) {
		System.out.println("putWeeklyPlan: " + dayPlans);
		WeeklyPlan weeklyPlan = weeklyPlanService.processIncomingDataToDomain(dayPlans);
		weeklyPlanService.updateWeeklyPlan(weeklyPlan);
		return ResponseEntity.ok(weeklyPlanService.returnWeekPlan());
	}

	@DeleteMapping("/")
	public ResponseEntity<WeeklyPlan> deleteWeeklyPlan() {
		System.out.println("deleteWeeklyPlan: ");
		WeeklyPlan thisWeekPlan = weeklyPlanService.deleteWeeklyPlan();
		System.out.println("deleteWeeklyPlan returned week: " + thisWeekPlan);
		return ResponseEntity.ok(thisWeekPlan);
	}
}
