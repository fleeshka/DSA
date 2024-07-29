import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);

        while(true){
            int k = scan.nextInt();

            System.out.println((k*k)-(2*k)+7);
            int res = ((k*k)-2*k+7) % 13;
            System.out.println(res);
        }
    }
}