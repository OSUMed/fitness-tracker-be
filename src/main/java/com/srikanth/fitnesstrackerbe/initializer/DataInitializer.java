package com.srikanth.fitnesstrackerbe.initializer;

import java.util.Collections;
import java.util.Optional;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.srikanth.fitnesstrackerbe.repository.AuthorityRepository;
import com.srikanth.fitnesstrackerbe.repository.UserRepository;
import com.srikanth.fitnesstrackerbe.repository.details.CardioExerciseDetailsRepository;
import com.srikanth.fitnesstrackerbe.repository.details.StrengthExerciseDetailsRepository;
import com.srikanth.fitnesstrackerbe.repository.details.StretchExerciseDetailsRepository;
import com.srikanth.fitnesstrackerbe.domain.Authority;
import com.srikanth.fitnesstrackerbe.domain.User;
import com.srikanth.fitnesstrackerbe.domain.details.CardioExerciseDetails;
import com.srikanth.fitnesstrackerbe.domain.details.StrengthExerciseDetails;
import com.srikanth.fitnesstrackerbe.domain.details.StretchExerciseDetails;
import com.srikanth.fitnesstrackerbe.domain.details.ExerciseDetails;

@Component
public class DataInitializer implements CommandLineRunner {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AuthorityRepository authorityRepository;
	@Autowired
	private StrengthExerciseDetailsRepository strengthRepository;
	@Autowired
	private StretchExerciseDetailsRepository stretchRepository;
	@Autowired
	private CardioExerciseDetailsRepository cardioRepository;

	@Override
	public void run(String... args) throws Exception {
		Optional<Authority> userRoleOpt = authorityRepository.findByName("ROLE_USER");
		Authority roleUser = userRoleOpt.orElseGet(() -> authorityRepository.save(new Authority("ROLE_USER")));

		Optional<Authority> adminRoleOpt = authorityRepository.findByName("ROLE_ADMIN");
		Authority roleAdmin = adminRoleOpt.orElseGet(() -> authorityRepository.save(new Authority("ROLE_ADMIN")));

		Optional<User> adminUserOpt = userRepository.findByUsername("admin");
		if (!adminUserOpt.isPresent()) {
			User adminUser = new User("admin", "encodedPassword");
			adminUser.setAuthorities(new HashSet<>(Collections.singletonList(roleAdmin)));
			userRepository.save(adminUser);
		}
		Optional<User> guestUserOpt = userRepository.findByUsername("guest");
		if (!guestUserOpt.isPresent()) {
			User adminUser = new User("guest", "guestlogin");
			adminUser.setAuthorities(new HashSet<>(Collections.singletonList(roleUser)));
			userRepository.save(adminUser);
		}
		addStrengthWorkouts();
		addCardioWorkouts();
	}

	private void addStrengthWorkouts() {
		strengthRepository.save(new StrengthExerciseDetails("Strength", "Bench Press",
				"https://example.com/strength/benchpress", "Flat bench barbell press, 3 sets of 10 reps", "Chest"));
		strengthRepository.save(new StrengthExerciseDetails("Strength", "Deadlift",
				"https://example.com/strength/deadlift", "Traditional barbell deadlift, 4 sets of 6 reps", "Back"));
	}

	private void addCardioWorkouts() {
		cardioRepository.save(new CardioExerciseDetails("Cardio", "Running", "https://example.com/cardio/running",
				"Outdoor running at a steady pace", 30, 5));
		cardioRepository.save(new CardioExerciseDetails("Cardio", "Cycling", "https://example.com/cardio/cycling",
				"Stationary bike cycling", 45, 15));
	}


}
