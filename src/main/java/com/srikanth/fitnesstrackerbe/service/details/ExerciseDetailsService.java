package com.srikanth.fitnesstrackerbe.service.details;

import java.util.List;

import com.srikanth.fitnesstrackerbe.domain.details.ExerciseDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.srikanth.fitnesstrackerbe.domain.details.ExerciseDetails;
import com.srikanth.fitnesstrackerbe.repository.details.ExerciseDetailsRepository;

@Service
public class ExerciseDetailsService {

	@Autowired
	private ExerciseDetailsRepository exerciseDetailsRepository;

	public List<ExerciseDetails> returnAllDetails() {
		return exerciseDetailsRepository.findAll();
	}

	public ExerciseDetails postExerciseDetail(ExerciseDetails exerciseDetail) {
		try {
			return exerciseDetailsRepository.save(exerciseDetail);
		} catch (Exception e) {
			throw new RuntimeException("Failed to save exercise detail", e);
		}
	}

	public ExerciseDetails putExerciseDetail(ExerciseDetails exerciseDetail, Long exerciseDetailId) {
		try {
			if (exerciseDetail.getId() != null && exerciseDetailsRepository.existsById(exerciseDetailId)) {
			
				return exerciseDetailsRepository.save(exerciseDetail);
			} else {
				throw new RuntimeException("Exercise detail not found with id: " + exerciseDetail.getId());
			}
		} catch (Exception e) {
			throw new RuntimeException("Failed to update exercise detail", e);
		}
	}

	public void deleteExerciseDetail(Long exerciseDetailId) {
		try {
			if (exerciseDetailsRepository.existsById(exerciseDetailId)) {
				exerciseDetailsRepository.deleteById(exerciseDetailId);
			} else {
				throw new RuntimeException("Exercise detail not found with id: " + exerciseDetailId);
			}
		} catch (NumberFormatException e) {
			throw new RuntimeException("Invalid exercise detail ID: " + exerciseDetailId, e);
		} catch (Exception e) {
			throw new RuntimeException("Failed to delete exercise detail", e);
		}
	}

	public List<ExerciseDetails> filterExerciseDetail(String exerciseDetailsType) {
		try {
			return exerciseDetailsRepository.findByType(exerciseDetailsType);
		} catch (Exception e) {
			throw new RuntimeException("Failed to filter exercise details", e);
		}
	}

}
