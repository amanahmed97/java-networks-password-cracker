import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        System.out.println("Hello world!");
        Scanner input = new Scanner(System.in);
        while(true) {
            System.out.println("Enter the password , you want the hash for :");
            String Password = input.nextLine();
            while (!Password.matches("[a-zA-Z]{5}+")) {
                System.out.println("Enter only strings for the following password .");
                Password = input.nextLine();

            }
            String HashGenerated = getHashValue(Password);

            System.out.println("Generating the Password from the Hash :" );

            char[] AllCharactersNeeded = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
    char [] PasswordFromHash ={'-', '-','-', '-','-'} ;
        for (int i = 1 ; i < AllCharactersNeeded.length ; i+=3) {
            PasswordFromHash[0] = AllCharactersNeeded[i];

            for(int j = 0 ; j < AllCharactersNeeded.length;j++){
                PasswordFromHash[1] = AllCharactersNeeded[j];

                for(int k = 0 ; k < AllCharactersNeeded.length;k++){
                    PasswordFromHash[2] = AllCharactersNeeded[k];

                    for(int l = 0 ; l < AllCharactersNeeded.length;l++){
                        PasswordFromHash[3]= AllCharactersNeeded[k];

                        for(int m = 0 ; m < AllCharactersNeeded.length;m++){
                            PasswordFromHash[4] = AllCharactersNeeded[m];

                            String PasswordForHash = toString(PasswordFromHash);
                            System.out.println(PasswordForHash);
                            String HashGeneratednew =  getHashValue(PasswordForHash);

                            if(HashGeneratednew.equals(HashGenerated)){
                                System.out.println("We found the password you were looking for : ");
                                System.out.println("Password Generated from Hash is "+PasswordForHash);
                                System.exit(0);
                            }

                        }

                    }                }
            }

            }
            System.out.println(AllCharactersNeeded.length);


        }
    }

    public static String getHashValue(String password) {
        String HashGenerated = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(password.getBytes());
            BigInteger bigInt = new BigInteger(1, messageDigest);
            HashGenerated = bigInt.toString(16);
            if (HashGenerated.length() < 32) {
                HashGenerated = '0' + HashGenerated;
            }
            System.out.println("Hash : " +HashGenerated);
        } catch (Exception e) {
            System.out.println("Exception :" + e);
        }
        return HashGenerated;
    }

    public static String toString(char[] a){
        String string = new String(a);
        return string ;
    }
    }