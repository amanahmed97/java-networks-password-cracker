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
	public Dictionary cracker(@RequestParam(value = "hash", defaultValue = "password") String hash) throws IOException{				
		
		String HOSTNAME = "localhost";
		int PORT = 9090;
		String password;

		// Connect to server		
			Socket clientSocket = new Socket(HOSTNAME, PORT);

			InputStreamReader isr = new InputStreamReader(clientSocket.getInputStream());
			BufferedReader in = new BufferedReader(isr);
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            {

                System.out.println("Connected to " + HOSTNAME + " on port " + PORT);
                System.out.println("Choose if you want to connect this user for ");
//            String Mode = in.readLine();
                String data = "Making First Connection";
                String SelectMode;
                System.out.println("Sending to server:\n" + data);
//                out.println(data);
//
                String line;
////first phase
//                while ((line = in.readLine()) != null) { //Making tcp Connection
//                    System.out.println("TCP Connection Made : Client received: " + line);
//                    break;
//                }
//                System.out.println("TCP Connection Successful now waiting For Checking Password."); // When the server replies the first time we move to First Phase for sending the message
////HASH REQUEST
                //second phase
                password = "ABCDE";
                // String Hash = getHashValue(password);
                out.println(hash);

                while (!(line = in.readLine()).equals(password)) { //Making tcp Connection


                }
                System.out.println("Password for the Following Hash is : " + line);


        }        


		Dictionary response = new Hashtable();
		response.put("hash", hash);
		response.put("password", password);

		return response;
	}
}
