# CS655-GENI-Mini-Project
## Password Cracker

Team Members:
1) Aman Ahmed - amana@bu.edu - U01171262
2) Nirbhay Malhotra - nmalhotr@bu.edu - U42321017
3) Rhythm Somaiya - rhythm@bu.edu - U84158310

Setup Instructions:
1. Reserve resources on GENI
Use the Rspec "rspec.xml" to reserve the resources on GENI.

2. SSH into the server and all the worker nodes in order to open the sockets in each of the nodes. We do this using GENI and the SSH Keys that are provided by GENI. And you can initiate the process for sending the hash by running the API client locally and connecting it to the server on GENI since the server is allocated a public routable IP.

3. We run the server code after the ssh on GENI by using the command -
javac Server.java
java Server
For running the server on the backend infinitely we can use the following command :
nohup java Server &
- Now using the built web interface, the user enters the 5-character password (which they want to crack) which further gets converted into a MD5 hash and it passes the API request to the server. The user also has the option to assign the number of workers they want to find the password for that particular hash.
- The server code then opens a socket and begins listening.

4. For running the API Client locally, we can use the command – 
javac Client.java
java Client

5. For running the Worker node on GENI by using the command – 
javac Worker.java
java Worker

For running the server on the backend infinitely , we can use the following command :
nohup java Worker &
The program runs until the password is either found by one of the workers and gets reported by the server. The cracked password is then shown in the web interface to the user.

There are two ways to reach the server hosted on GENI - one using the terminal and the other way is to use Java Spring or Restful API.

NOTE: 
We only have 4 worker nodes. In order to relate the experiment to the graph displayed in the report, use the first character from D to Z. This will indicate that if a hash related to a password that has first character as D, this will be found much earlier than with the one worker node.
