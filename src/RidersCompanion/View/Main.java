package RidersCompanion.View;
import RidersCompanion.Model.Ride;
import RidersCompanion.Model.Rider;
import RidersCompanion.Controller.RideDAO;
import RidersCompanion.Controller.RiderDAO;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Scanner;

public class Main {
   static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        System.out.println();
        System.out.println("===============================\nWELCOME TO RIDERS COMPANION\n===============================");
        run();
    }

    private static void run() {
        System.out.println();
        System.out.println("Login or Register");
        System.out.println("1. SignIN");
        System.out.println("2. SignUp");
        System.out.println("3. Exit");
        int n =sc.nextInt();
        sc.nextLine();
        if (n == 1) {
            SignIn();
        } else if (n == 2) {
            SignUp();
        } else if (n == 3) {
            System.out.println("Good Bye...");
        }
    }

    public static void SignUp() {
        int rider_id;
        Rider rider = new Rider();
        RiderDAO dao = new RiderDAO();


        System.out.println("Enter your name");
        String name = sc.nextLine();

        System.out.println("Enter your password");
        String p = sc.nextLine();
        String pass = sha(p);
        System.out.println(pass);

        try {
           rider_id = dao.logincheck(name,pass);
           if(rider_id!=0)
           {
               rider_id=0;
               System.out.println("->...UserName and password already available...<-");
               run();
           }

        } catch (SQLException e) {
            System.out.println("loginCheckError"+e);
        }

         rider.setName(name);
         rider.setPass(pass);
        System.out.println("Enter your email id");
        rider.setEMail(sc.nextLine());
        System.out.println("Enter your phone number(10 digits");
        rider.setPhno(sc.nextLine());
        System.out.println("Enter your Licence No");
        rider.setLicNo(sc.nextLine());
        System.out.println("Enter your BikeNo");
        rider.setBikeNo(sc.nextLine());

        try {
            rider_id = dao.registerTheUser(rider.getName(), rider.getEMail(), rider.getphno(), rider.getLicNo(), rider.getBikeNo(), rider.getpass());
            if(rider_id!=0)
                createOrJoin(rider_id);
        } catch (SQLException e) {
            System.out.println("registration error"+e);
        }

    }

    public static void SignIn() {

        int rider_id;

        System.out.println("Enter your name");
        String name =sc.nextLine();
        System.out.println("Enter your password");
        String p = sc.nextLine();
        String pass = sha(p);
        System.out.println(pass);
        RiderDAO dao = new RiderDAO();
        try {
            rider_id = dao.logincheck(name,pass);
           if(rider_id==0)
           {
               System.out.println("... Worng userName or password ...");
               run();
           }
           else
           {
              createOrJoin(rider_id);
           }

        } catch (SQLException e) {
            System.out.println("loginCheckError"+e);
        }

    }

    public static void createOrJoin(int rider_id) {

        Rider rider = new Rider();
        Ride ride = new Ride();
        Scanner sc = new Scanner(System.in);
       // boolean b = true;
            System.out.println();
            System.out.println("===============  RIDER MENU ==================");
            System.out.println("1. Create Ride");
            System.out.println("2. Join Ride");
            System.out.println("3. Created Ride Status");
            System.out.println("4. Joined Ride Status");
            System.out.println("5. Logout");
            System.out.println("6. Exit");

            String sn = sc.nextLine();
            int n = Integer.parseInt(sn);

            if (n == 1)
            {

                rider.createRide(rider_id);

                createOrJoin(rider_id);
            }
            else if (n == 2)
            {
                searchRide(rider_id);

                System.out.println();
                System.out.println("Enter RideID you want to join :");
                int ride_id = sc.nextInt();sc.nextLine();

               if( rider.joinRide(ride_id,rider_id)) {
                   createOrJoin(rider_id);
               }
               else
               {
                   createOrJoin(rider_id);
               }
            }
            else if (n == 3)
            {
                Ride.createdRideStatus(rider_id);createOrJoin(rider_id);
            }
            else if (n == 4)
            {
                Ride.joinedRideStatus(rider_id);createOrJoin(rider_id);
            }
            else if (n == 6)
            {
                System.out.println("Good Bye");
            } else if (n == 5) {

                System.out.println("Logged out Successfully");
                run();
            }
    }

    public static void searchRide(int rider_id) {
        System.out.println(">>>>>>>>>>  SEARCH RIDE  <<<<<<<<<<<");
        System.out.println("1. Display All rides");
        System.out.println("2. search by start place");
        System.out.println("3. search by end place");
        System.out.println("4. exit");

        String s = sc.nextLine();
        int n = Integer.parseInt(s);

            if(n==1)
            {
                try {
                    RideDAO.displayRides();
                } catch (SQLException e) {
                    System.out.println("Display Error" + e);
                }
            }
            if(n==2)
            {
                System.out.println("Enter the stating place :");
                String st  = sc.nextLine();
                RideDAO.searchTheRide(st,"0");
            }
            if(n==3)
            {
                System.out.println("Enter the ending place :");
                String ep = sc.nextLine();
                RideDAO.searchTheRide("0",ep);
            }
            if(n==4)
            {
                createOrJoin(rider_id);
            }
    }

    public static byte[] getSHA(String input) throws NoSuchAlgorithmException
    {
        // Static getInstance method is called with hashing SHA
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    public static String toHexString(byte[] hash)
    {
        // Convert byte array into signum representation
        BigInteger number = new BigInteger(1, hash);

        // Convert message digest into hex value
        StringBuilder hexString = new StringBuilder(number.toString(16));

        // Pad with leading zeros
        while (hexString.length() < 64)
        {
            hexString.insert(0, '0');
        }

        return hexString.toString();
    }

    // Driver code
    public static String sha(String pass)
    {
        try
        {
            //System.out.println("HashCode Generated by SHA-256");
            return toHexString(getSHA(pass));
        }
        catch (Exception e) {
            System.out.println("Error in encryption");
            e.printStackTrace();
        }
        return "1";
    }
}