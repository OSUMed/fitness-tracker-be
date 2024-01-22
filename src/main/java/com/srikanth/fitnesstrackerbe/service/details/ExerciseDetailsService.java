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

	public ExerciseDetails postExerciseDetail(List<ExerciseDetails> exerciseDetail) {
		// TODO Auto-generated method stub
		return null;
	}

	public ExerciseDetails putExerciseDetail(ExerciseDetails exerciseDetail) {
		// TODO Auto-generated method stub
		return null;
	}

	public ExerciseDetails deleteExerciseDetail(String exerciseDetailId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<ExerciseDetails> filterExerciseDetail(String exerciseDetailsType) {
		try {
			return exerciseDetailsRepository.findByType(exerciseDetailsType);
		} catch (Exception e) {
			throw new RuntimeException("Failed to filter exercise details", e);
		}
	}

}
