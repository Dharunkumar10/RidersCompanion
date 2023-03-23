import java.util.Scanner;

public class Main {
   static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {


        System.out.println("WELCOME TO RIDERS COMPANION\n===============================\n");
        run();
    }

    private static void login() {
        System.out.println("1. SignIN");
        System.out.println("2. SignUp");
        System.out.println("3. Exit");
        int n =sc.nextInt();

        switch (n)
        {

            case 1:
                SignIn();break;
            case 2:
                SignUp();break;
            case 3:
                System.out.println("Good Bye...");
                return;
        }
    }
}