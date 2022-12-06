import java.math.BigInteger;
import java.security.MessageDigest;

public class CheckingTrial {
    public static void main(String arg[]){
        String a= "dad612d5d434d4dfbc3a42cc1969c014 1 2";
        int range = a.length();
        int first = a.indexOf(' ',31);
        String Hash = a.substring(0,first);
        int Number1 = Integer.parseInt(a.substring(first+1,first+2));
        int Number2 = Integer.parseInt(a.substring(first+3,a.length()));
        System.out.println("Hash : "+Hash+" Number : "+Number1+" , "+Number2);
        byte[] m = "Hello".getBytes();
        String s = new String(m);
        for(int i = 0 ; i < 10 ; i+=3){
            System.out.println(s);
        }

        String as = "ADDDZ";
        String dasd= getHashValue(as);
        System.out.println(dasd);
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
}
