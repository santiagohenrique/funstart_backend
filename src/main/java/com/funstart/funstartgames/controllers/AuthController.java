package com.funstart.funstartgames.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.funstart.funstartgames.dtos.LoginRequestDTO;
import com.funstart.funstartgames.dtos.LoginResponseDTO;
import com.funstart.funstartgames.dtos.RegisterRequestDTO;
import com.funstart.funstartgames.infra.security.TokenService;
import com.funstart.funstartgames.models.User;
import com.funstart.funstartgames.repositories.UserRepository;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação")
public class AuthController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private TokenService tokenService;

	@PostMapping("/login")
	public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request) {
		User user = userRepository.findByEmail(request.email())
				.orElseThrow(() -> new RuntimeException("User not found"));
		if (passwordEncoder.matches(request.password(), user.getPassword())) {
			String token = tokenService.generateToken(user);
			return ResponseEntity.ok().body(new LoginResponseDTO(user.getName(), token));
		} else {
			return ResponseEntity.badRequest().build();
		}
	}

	@PostMapping("/register")
	public ResponseEntity<Object> register(@RequestBody @Valid RegisterRequestDTO request) {
		Optional<User> user = userRepository.findByEmail(request.email());
		if (user.isEmpty()) {
			User newUser = new User();
			newUser.setEmail(request.email());
			newUser.setName(request.name());
			newUser.setPassword(passwordEncoder.encode(request.password()));
			userRepository.save(newUser);
			String token = tokenService.generateToken(newUser);
			return ResponseEntity.ok().body(new LoginResponseDTO(newUser.getName(), token));
		} else {
			return ResponseEntity.badRequest().build();
		}
	}
}
