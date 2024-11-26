package com.example.crm_left_service;

import com.example.crm_left_service.entity.User;
import com.example.crm_left_service.entity.UserRole;
import com.example.crm_left_service.repository.UserRepository;
import com.example.crm_left_service.repository.UserRoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class CrmLeftServiceApplication implements CommandLineRunner {

	private final UserRepository userRepository;
	private final UserRoleRepository userRoleRepository;
	private final PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(CrmLeftServiceApplication.class, args);
	}

	public CrmLeftServiceApplication(UserRepository userRepository, UserRoleRepository userRoleRepository,PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.userRoleRepository = userRoleRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void run(String... args) {
		if (userRoleRepository.findByName("ADMIN").isEmpty()) {
			UserRole userRole = new UserRole();
			userRole.setName("ADMIN");
			userRoleRepository.save(userRole);

			final String pass = passwordEncoder.encode("admin=x+x=2x");
			var admin = User.builder()
					.email("admin@gmail.com")
					.password(pass)
					.userRole(userRole).build();
			if (userRepository.findByEmail("admin@gmail.com").isEmpty()) userRepository.save(admin);
		}
	}

}
