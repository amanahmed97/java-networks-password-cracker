package com.example.backend;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HasherController {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@GetMapping("/crack")
	public Dictionary greeting(@RequestParam(value = "hash", defaultValue = "password") String hash) {
		
		// return new Greeting(counter.incrementAndGet(), String.format(template, hash));
		// String name1 = "greeting";
		// return name1;

		Dictionary response = new Hashtable();
		response.put("hash", hash);
		response.put("password", "password-cracked");
		return response;
	}
}
