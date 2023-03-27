package RidersCompanion.Controller;
import RidersCompanion.Controller.DBConnection;
import java.sql.*;
public class RideDAO {
    public static void displayRides() throws SQLException {
        Connection con = DBConnection.getConnection();
        String query = "select * from rides";
        Statement stmt = con.createStatement();
        ResultSet res = stmt.executeQuery(query);
        while (res.next()) {
            int id = res.getInt("r_id");
            String name = res.getString("r_name");
            String startplace = res.getString("st_place");
            String destination = res.getString("destination");
            String startdate = res.getString("st_date");
            String enddate = res.getString("end_date");
            String desc = res.getString("descrip");
            String memberlimit = res.getString("memberslimit");

            System.out.println();
            System.out.println("-------------------------------------------------------------");
            System.out.println();
            System.out.println(">>>>>>>>>>>>>>>  " + "Ride Id : " + id + "  <<<<<<<<<<<<<<<<");
            System.out.println();
            System.out.println("Ride Name : " + name);
            System.out.println("Starting place of the ride : " + startplace);
            System.out.println("Destination of the ride : " + destination);
            System.out.println("When the ride starts ? " + startdate);
            System.out.println("When the ride ends ? " + enddate);
            System.out.println();
            System.out.println("Route....\n" + desc);
            System.out.println();
            System.out.println("limit   : " + memberlimit);

        }
    }

    public void create(String rName, String sPlace, String eplace, Date sdate, Date edate, String descrip, int m_limit, int rider_id) throws SQLException {
        Connection con = DBConnection.getConnection();
        Statement st = con.createStatement();
        String s = "select count(*) from rides";
        ResultSet r = st.executeQuery(s);
        int n = 0;
        while (r.next()) {
            n = r.getInt("count(*)");
        }

        n = n + 1;


        String query = "insert into rides values(%d,'%s','%s','%s','%s','%s','%s',%d,%d,%d)".formatted(n, rName, sPlace, eplace, sdate, edate, descrip, m_limit, 0, rider_id);
        st.executeUpdate(query);
        System.out.println("\n>>>>>>>>  Your ride is created successfully  <<<<<<<<\n");
    }

    public static boolean isAvailable(int r_id) throws SQLException {
        int n = 0;
        int n1 = 0;
        Connection con = DBConnection.getConnection();
        Statement st = con.createStatement();
        String s = "select membersadded,memberslimit from rides where r_id = %d".formatted(r_id);
        ResultSet res = st.executeQuery(s);
        while (res.next()) {
            n = res.getInt("membersadded");
            n1 = res.getInt("memberslimit");
        }


//        if(n<n1)
//            return true;
//        return false;

        return n < n1;


    }

    public static void showRideCreatedStatus(int riderid) throws SQLException {
        Connection con = DBConnection.getConnection();
        Statement st = con.createStatement();
        String s = "select * from rides where creator_id = %d".formatted(riderid);
        ResultSet res = st.executeQuery(s);
        while (res.next()) {
            int id = res.getInt("r_id");
            String name = res.getString("r_name");
            String startplace = res.getString("st_place");
            String destination = res.getString("destination");
            String startdate = res.getString("st_date");
            String enddate = res.getString("end_date");
            String desc = res.getString("descrip");
            String memberlimit = res.getString("memberslimit");

            System.out.println();
            System.out.println("-------------------------------------------------------------");
            System.out.println();
            System.out.println(">>>>>>>>>>>>>>>  " + "Ride Id : " + id + "  <<<<<<<<<<<<<<<<");
            System.out.println();
            System.out.println("Ride Name : " + name);
            System.out.println("Starting place of the ride : " + startplace);
            System.out.println("Destination of the ride : " + destination);
            System.out.println("When the ride starts ? " + startdate);
            System.out.println("When the ride ends ? " + enddate);
            System.out.println();
            System.out.println("Route....\n" + desc);
            System.out.println();
            System.out.println("limit   : " + memberlimit);

            System.out.println("---------->  Riders joined to this ride  <--------------");

            showMembersJoined(riderid);

        }
    }

    public static void showMembersJoined(int riderid) {
        try {
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();
            String s = "select name,email,phno from rider_details where rider_id in (select rider_id from ride_participants where r_id in (select r_id from rides where creator_id =%d))".formatted(riderid);
            ResultSet rs = st.executeQuery(s);
            if (!rs.next())
                System.out.println("No members joined in your ride");
            else {
                while (rs.next()) {
                    int n = 1;
                    String name = rs.getString("name");
                    String email = rs.getString("email");
                    String phno = rs.getString("phno");
                    System.out.println();
                    System.out.println(n + ".Name : " + name + "  ||  email : " + email + "  ||  phone : " + phno);
                    n++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void searchTheRide(String s, String ep) {
        Connection con = DBConnection.getConnection();
        try {
            Statement st = con.createStatement();
            String q;
            if (!s.equals("0")) {
                q = "select * from rides where st_place like '%" + s + "%'";
            } else {
                q = "select * from rides where destination like '%" + ep + "%'";
            }

            ResultSet res = st.executeQuery(q);

            while (res.next()) {
                int id = res.getInt("r_id");
                String name = res.getString("r_name");
                String startplace = res.getString("st_place");
                String destination = res.getString("destination");
                String startdate = res.getString("st_date");
                String enddate = res.getString("end_date");
                String desc = res.getString("descrip");
                String memberlimit = res.getString("memberslimit");

                System.out.println();
                System.out.println("-------------------------------------------------------------");
                System.out.println();
                System.out.println(">>>>>>>>>>>>>>>  " + "Ride Id : " + id + "  <<<<<<<<<<<<<<<<");
                System.out.println();
                System.out.println("Ride Name : " + name);
                System.out.println("Starting place of the ride : " + startplace);
                System.out.println("Destination of the ride : " + destination);
                System.out.println("When the ride starts ? " + startdate);
                System.out.println("When the ride ends ? " + enddate);
                System.out.println();
                System.out.println("Route....\n" + desc);
                System.out.println();
                System.out.println("limit   : " + memberlimit);

            }


        } catch (SQLException e) {
            System.out.println("search error" + e);
        }

    }

    public static void showRiders(int ride_id) throws SQLException {
        Connection con = DBConnection.getConnection();
        Statement st = con.createStatement();
        String sq = "select name,email,phno from rider_details where rider_id in (select rider_id from ride_participants where r_id = %d)".formatted(ride_id);
        ResultSet set = st.executeQuery(sq);
        if (!set.next())
            System.out.println("No members joined in your ride");
        else {
            int n = 1;
            while (set.next()) {

                String name = set.getString("name");
                String email = set.getString("email");
                String phno = set.getString("phno");
                System.out.println();
                System.out.println((n++) + ".Name : " + name + "  ||  email : " + email + "  ||  phone : " + phno);

            }
        }


    }
}






