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
 
		 String csp_init = phase + sp + rType + sp + hash + sp + count + newLine;
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


	@GetMapping("/crackv0")
	public Dictionary crackerV0(@RequestParam(value = "hash", defaultValue = "password") String hash) throws IOException{				
		
		String HOSTNAME = "localhost";
		int PORT = 9090;
		String password;
		String line;

		// Connect to server		
// 			Socket clientSocket = new Socket(HOSTNAME, PORT);

// 			InputStreamReader isr = new InputStreamReader(clientSocket.getInputStream());
// 			BufferedReader in = new BufferedReader(isr);
// 			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

//             {

//                 System.out.println("Connected to " + HOSTNAME + " on port " + PORT);
//                 System.out.println("Choose if you want to connect this user for ");
// //            String Mode = in.readLine();
//                 String data = "Making First Connection";
//                 String SelectMode;
//                 System.out.println("Sending to server:\n" + data);
// //                out.println(data);
// //
//                 String line;
// ////first phase
// //                while ((line = in.readLine()) != null) { //Making tcp Connection
// //                    System.out.println("TCP Connection Made : Client received: " + line);
// //                    break;
// //                }
// //                System.out.println("TCP Connection Successful now waiting For Checking Password."); // When the server replies the first time we move to First Phase for sending the message
// ////HASH REQUEST
//                 //second phase
//                 password = "ABCDE";
//                 // String Hash = getHashValue(password);
//                 out.println(hash);

//                 while (!(line = in.readLine()).equals(password)) { //Making tcp Connection


//                 }
//                 System.out.println("Password for the Following Hash is : " + line);


//         }        

		try (

			Socket clientSocket = new Socket(HOSTNAME, PORT);

			InputStreamReader isr = new InputStreamReader(clientSocket.getInputStream());
			BufferedReader in = new BufferedReader(isr);
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

		) {


		System.out.println("Connected to " + HOSTNAME + " on port " + PORT);
		System.out.println("Choose if you want to connect this user for ");
	//            String Mode = in.readLine();
		String data = "Making First Connection";
		String SelectMode;
		System.out.println("Sending to server:\n" + data);
	//                out.println(data);
	//
		// String line;	

		// password = "ABBBE";
		int numberOfClients =2;
		// String Hash = getHashValue(password);
		String newHash = hash +" "+ numberOfClients;
		out.println(newHash);
		line = in.readLine();
		while(line == null){

		}

		// while(line != null){
		// 	while(!line.equals(password) ){

		// 	}
		// 	if(line.equals(password)){
		// 		break ;
		// 	}

		// }

			System.out.println("Password for the Following Hash is : " + line);

		}


		Dictionary response = new Hashtable();
		response.put("hash", hash);
		response.put("password", line);

		return response;
	}

}
