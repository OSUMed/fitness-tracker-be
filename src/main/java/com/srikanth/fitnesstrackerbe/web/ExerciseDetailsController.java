package com.srikanth.fitnesstrackerbe.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.srikanth.fitnesstrackerbe.domain.details.ExerciseDetails;
import com.srikanth.fitnesstrackerbe.service.ExerciseDetailsService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/details")
public class ExerciseDetailsController {

	private final ExerciseDetailsService exerciseDetailsService;

	@Autowired
	public ExerciseDetailsController(ExerciseDetailsService exerciseDetailsService) {
		this.exerciseDetailsService = exerciseDetailsService;
	}

	@GetMapping("/test")
	public ResponseEntity<String> testWeeklyPlan() {
		System.out.println("getWeeklyPlan: ");
		return ResponseEntity.ok("Exercise Details Test endpoint from eclipse!");
	}

	@GetMapping("/findAll")
	public ResponseEntity<List<ExerciseDetails>> getExerciseDetails() {
		try {

			System.out.println("getExerciseDetails: ");
			return ResponseEntity.ok(exerciseDetailsService.returnAllDetails());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@GetMapping("/filter")
	public ResponseEntity<List<ExerciseDetails>> getFilteredExerciseDetails(String exerciseDetailsType) {
		try {
			System.out.println("getFilteredExerciseDetails: " + exerciseDetailsType);
			List<ExerciseDetails> filteredExerciseDetail = exerciseDetailsService
					.filterExerciseDetail(exerciseDetailsType);
			return ResponseEntity.ok(filteredExerciseDetail);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@PostMapping("/")
	public ResponseEntity<List<ExerciseDetails>> createExerciseDetails(@RequestBody List<ExerciseDetails> dayPlans) {
		try {
			System.out.println("createExerciseDetails: " + dayPlans);
			ExerciseDetails savedExerciseDetail = exerciseDetailsService.postExerciseDetail(dayPlans);
			System.out.println("createExerciseDetails weeklyPlan: " + savedExerciseDetail);
			return ResponseEntity.ok(exerciseDetailsService.returnAllDetails());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@PutMapping("/")
	public ResponseEntity<List<ExerciseDetails>> putExerciseDetails(@RequestBody ExerciseDetails exerciseDetail,
			String id) {
		try {
			System.out.println("putExerciseDetails: " + exerciseDetail);
			ExerciseDetails savedExerciseDetail = exerciseDetailsService.putExerciseDetail(exerciseDetail);
			System.out.println("createExerciseDetails weeklyPlan: " + savedExerciseDetail);
			return ResponseEntity.ok(exerciseDetailsService.returnAllDetails());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@DeleteMapping("/")
	public ResponseEntity<Void> deleteExerciseDetails(String exerciseDetailId) {
		try {
			System.out.println("deleteExerciseDetails: ");
			exerciseDetailsService.deleteExerciseDetail(exerciseDetailId);
			return ResponseEntity.ok().build();

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
}
