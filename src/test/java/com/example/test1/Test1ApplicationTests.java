package com.devteria.identityservice;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import jakarta.xml.bind.DatatypeConverter;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class IdentityServiceApplicationTests {

	@Test
	void hash() throws NoSuchAlgorithmException {
		String password = "123456";



		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(14);

		log.info("BCrypt round 1: {}", passwordEncoder.encode(password));
		log.info("BCrypt round 2: {}", passwordEncoder.encode(password));
	}
}