
import java.util.Scanner;

public class signIn {

    public static void main(String[] args) {

        int password = 1234;
        int attempt = 0;
        int input;

        Scanner sc = new Scanner(System.in);

        do {
            System.out.print("Enter your password: ");
            input = sc.nextInt();
            attempt++;

            if (input != password) {
                System.out.println("Incorrect password. Try again.");
            }
        } while (input != password);

        System.out.println("Access granted. Welcome! Attempts: " + attempt);
    }
}
