
import java.util.Scanner;
public class Main {
    public static void main(String[] args){

       Scanner in =new Scanner(System.in);
        System.out.println("Please enter K value :");
        int k=in.nextInt();
        System.out.println("Please enter M value :");
        int m=in.nextInt();
        Structure struc=new Structure(m,k);
        struc.addFromFile();
        struc.checkFromFile();

    }
}
