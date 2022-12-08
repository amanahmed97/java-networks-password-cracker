# CS655-GENI-Mini-Project
## Password Cracker

Team Members:
1) Aman Ahmed - amana@bu.edu - U01171262
2) Nirbhay Malhotra - nmalhotr@bu.edu - 
3) Rhythm Somaiya - rhythm@bu.edu - U84158310

Setup Instructions:
1. Reserve resources on GENI
Use the Rspec "ServerWorker_request_rspec.xml" to reserve the resources on GENI.

2. SSH into the server and all the worker nodes in order to open the sockets in each of the nodes. We do this using GENI and the SSH Keys that are provided by GENI.

3. We run the server code first by using the command -
*add command*

- Now using the built web interface, the user enters the 5-character password (which they want to crack) which further gets converted into a MD5 hash and it passes the API request to the server. The user also has the option to assign the number of workers they want to find the password for that particular hash.

- The server code then opens a socket and begins listening.

4. We now run the worker code by using the following command on each of the nodes -
*add command*

5. The program runs until the password is either found by one of the workers and gets reported by the server. The cracked password is then shown in the web interface to the user.


