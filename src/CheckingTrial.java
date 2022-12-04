public class CheckingTrial {
    public static void main(String arg[]){
        String a= "dad612d5d434d4dfbc3a42cc1969c014 1 2";
        int range = a.length();
        int first = a.indexOf(' ',31);
        String Hash = a.substring(0,first);
        int Number1 = Integer.parseInt(a.substring(first+1,first+2));
        int Number2 = Integer.parseInt(a.substring(first+3,a.length()));
        System.out.println("Hash : "+Hash+" Number : "+Number1+" , "+Number2);
        for(int i = 0 ; i < 10 ; i+=3){
            System.out.println("Hellow");
        }
    }
}
