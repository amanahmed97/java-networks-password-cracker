package com.example.backend;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.math.BigInteger;
import java.net.*;
import java.security.MessageDigest;

@RestController
public class HasherController {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@GetMapping("/crack")
	public Dictionary cracker(@RequestParam(value = "hash", defaultValue = "password") String hash,
	@RequestParam(value = "useworkers", defaultValue = "2") int useWorkers) throws IOException{
		
		String HOSTNAME = "localhost";
		int PORT = 2961;
		String password;
		String line;

		 // Initialize message variables
		 String phase="r";
		 String rType = "hash";
		 int message_size=10;
		 String sp=" ";
		 String newLine ="\n";
		 int count=1;
		 int probes=10;
		 String mp_message;
		 String payload;
		 String ctp_init;		 
 
		 String clientInput;
		 String serverResponse;
 
		 System.out.println("Client Starting");
 
		 BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
 		
		 String host = "localhost";		
		 int port = 1111;
		 System.out.println("\nConnecting to server : "+host+" : "+port);
 
 
		 // Begin connection with Server
		 Socket clientSocket = new Socket(host, port);
 
		 // TCP Connection
		 DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
		 BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
 
		 // CSP
		 count = 2;		 
 
		 String csp_init = phase + sp + rType + sp + hash + sp + useWorkers + newLine;
		 System.out.println("\nCSP : " + csp_init);
		 outToServer.writeBytes(csp_init);
		 serverResponse = inFromServer.readLine();
		 System.out.println("Server Response : " + serverResponse + "\n");
 
		 if (!serverResponse.equals("200 OK: Ready")) clientSocket.close(); 
 
		 System.out.println("Connection Close\n\n\n");
		 clientSocket.close();


		Dictionary response = new Hashtable();
		response.put("hash", hash);
		response.put("password", serverResponse);

		return response;
	}


}
