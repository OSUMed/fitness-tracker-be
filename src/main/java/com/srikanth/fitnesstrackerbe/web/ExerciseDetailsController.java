package com.srikanth.fitnesstrackerbe.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.srikanth.fitnesstrackerbe.domain.details.ExerciseDetails;
import com.srikanth.fitnesstrackerbe.service.details.ExerciseDetailsService;

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
	public ResponseEntity<List<ExerciseDetails>> createExerciseDetails(@RequestBody Map<String, Object> requestBody) {
	    try {
	        System.out.println("postExerciseDetails requestBody: " + requestBody);
	        
	        @SuppressWarnings("unchecked")
	        Map<String, Object> newExerciseMap = (Map<String, Object>) requestBody.get("newExercise");
	        ObjectMapper objectMapper = new ObjectMapper();
	        ExerciseDetails exerciseDetail = objectMapper.convertValue(newExerciseMap, ExerciseDetails.class);

	        String exerciseType = (String) requestBody.get("exerciseType");
	        exerciseDetail.setType(exerciseType);  // Set explicitly because Jackson bug

	        ExerciseDetails savedExerciseDetail = exerciseDetailsService.postExerciseDetail(exerciseDetail);
	        System.out.println("postExerciseDetails result: " + savedExerciseDetail);

	        return ResponseEntity.ok(exerciseDetailsService.returnAllDetails());
	    } catch (Exception e) {
	        e.printStackTrace();  
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	    }
	}


	@PutMapping("/{exerciseDetailId}")
	public ResponseEntity<List<ExerciseDetails>> putExerciseDetails(
	        @PathVariable Long exerciseDetailId,
	        @RequestBody Map<String, Object> requestBody) {
	    try {
	        String exerciseType = (String) requestBody.get("type");

	        // Request Body has the mapping:
	        ObjectMapper objectMapper = new ObjectMapper();
	        ExerciseDetails exerciseDetail = objectMapper.convertValue(requestBody, ExerciseDetails.class);
	        exerciseDetail.setType(exerciseType);	// work around for jackson bug
	        ExerciseDetails savedExerciseDetail = exerciseDetailsService.putExerciseDetail(exerciseDetail, exerciseDetailId);
	        System.out.println("putExerciseDetails result: " + savedExerciseDetail);

	        return ResponseEntity.ok(exerciseDetailsService.returnAllDetails());
	    } catch (Exception e) {
	        // Log the exception for debugging
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	    }
	}


	@DeleteMapping("/{exerciseDetailId}")
	public ResponseEntity<List<ExerciseDetails>> deleteExerciseDetails(@PathVariable Long exerciseDetailId) {
		try {
			System.out.println("deleteExerciseDetails: ");
			exerciseDetailsService.deleteExerciseDetail(exerciseDetailId);
			return ResponseEntity.ok(exerciseDetailsService.returnAllDetails());

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
}
