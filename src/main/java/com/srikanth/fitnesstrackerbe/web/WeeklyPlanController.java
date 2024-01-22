package com.srikanth.fitnesstrackerbe.web;

import java.util.List;

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

import com.srikanth.fitnesstrackerbe.domain.weekplan.DayPlan;
import com.srikanth.fitnesstrackerbe.domain.weekplan.WeeklyPlan;
import com.srikanth.fitnesstrackerbe.service.weekplan.WeeklyPlanService;


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
	public ResponseEntity<List<DayPlan>> getWeeklyPlan() {
		System.out.println("getWeeklyPlan: ");
		return ResponseEntity.ok(weeklyPlanService.returnWeekPlan());
	}

	@PostMapping("/")
	public ResponseEntity<List<DayPlan>> createWeeklyPlan(@RequestBody List<DayPlan> dayPlans) {
		System.out.println("createWeeklyPlan: " + dayPlans);
		WeeklyPlan savedWeekPlan = weeklyPlanService.processPostIncomingDataToDomainBackup(dayPlans);
		System.out.println("createWeeklyPlan weeklyPlan: " + savedWeekPlan);
		return ResponseEntity.ok(weeklyPlanService.returnWeekPlan());
	}

	@SuppressWarnings("unchecked")
	@PutMapping("/")
	public ResponseEntity<List<DayPlan>> putWeeklyPlan(@RequestBody List<DayPlan> dayPlans) {
		System.out.println("putWeeklyPlan: " + dayPlans);
		weeklyPlanService.processUpdateIncomingDataToDomain(dayPlans);
		return ResponseEntity.ok(weeklyPlanService.returnWeekPlan());
	}

	@DeleteMapping("/")
	public ResponseEntity<List<DayPlan>> deleteWeeklyPlan() {
		System.out.println("deleteWeeklyPlan: ");
		weeklyPlanService.deleteWeeklyPlan();
		return ResponseEntity.ok(new WeeklyPlan().getDayPlans());
	}
}
