package com.srikanth.fitnesstrackerbe.initializer;

import java.util.Collections;
import java.util.Optional;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.srikanth.fitnesstrackerbe.repository.AuthorityRepository;
import com.srikanth.fitnesstrackerbe.repository.UserRepository;
import com.srikanth.fitnesstrackerbe.domain.Authority;
import com.srikanth.fitnesstrackerbe.domain.User;


@Component
public class DataInitializer implements CommandLineRunner {
	@Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthorityRepository authorityRepository;

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
    }
}
