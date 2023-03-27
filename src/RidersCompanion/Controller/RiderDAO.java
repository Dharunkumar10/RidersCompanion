package RidersCompanion.Controller;
import RidersCompanion.Controller.DBConnection;

import java.sql.*;
public class RiderDAO {
    public int logincheck(String name,String pass) throws SQLException {
        Connection con = DBConnection.getConnection();
        Statement st = con.createStatement();

        String query = "select rider_id from rider_details where name = '%s' and password = '%s'".formatted(name, pass);
        ResultSet res = st.executeQuery(query);
        int val=0;
        while(res.next())
            val = res.getInt("rider_id");
        return val;
    }

    public int registerTheUser(String name,String email,String phno,String licno,String bikeno,String pass) throws SQLException
    {

        int rider_id=0;
        Connection con=DBConnection.getConnection();
        Statement st=con.createStatement();

        String ss="select count(*) from rider_details";
        ResultSet r=st.executeQuery(ss);

        while(r.next())
        {
            rider_id=r.getInt("count(*)");
        }

        rider_id=rider_id+1;


        String s= "insert into rider_details values(%d,'%s','%s','%s','%s','%s','%s')".formatted(rider_id, name, email, phno, licno, bikeno, pass);
        st.executeUpdate(s);
        System.out.println("\n ------->  Registered Successfully  <--------- \n");
        return rider_id;
    }
    public boolean joinRide(int rideId,int riderId) throws SQLException
    {
        Connection con = DBConnection.getConnection();
        Statement st = con.createStatement();

        String s = "    select r_id from ride_participants where rider_id = %d".formatted(riderId);
        ResultSet r = st.executeQuery(s);
        boolean b = false;
        int result = 0;
        while(r.next())
        {
            result = r.getInt("r_id");
            if(result == rideId)
                b=true;
        }

        if(!b)
        {
            int j_id=0;
            String c = "select count(*) from ride_participants";

            ResultSet res=st.executeQuery(c);

            while(res.next())
            {
                j_id=res.getInt("count(*)");
            }

            j_id=j_id+1;
            String query = "insert into ride_participants values(%d,%d,%d)".formatted(riderId, rideId,j_id);
            st.executeUpdate(query);

            String query1 = "select membersadded from rides where r_id = %d".formatted(rideId);
            ResultSet rs = st.executeQuery(query1);
            int addedMembers = 0;
            while(rs.next())
            {
                addedMembers = rs.getInt("membersadded");
            }
            addedMembers = addedMembers+1;

            String query2 = "update rides set membersadded = %d where r_id = %d".formatted(addedMembers, rideId);
            st.executeUpdate(query2);

            return true;
        }

        return false;
    }

    public static void showRideJoinedStatus(int rider_id) throws SQLException {
        Connection con = DBConnection.getConnection();
        Statement st = con.createStatement();
        String str = "select r_id from ride_participants where rider_id = %d".formatted(rider_id);
        ResultSet res = st.executeQuery(str);
        int id = 0;
        while(res.next())
        {
            id = res.getInt("r_id");
            try
            {
                rideStats(id);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }

    }

    public static void rideStats(int r_id) throws SQLException {
        Connection con = DBConnection.getConnection();
        String query = "select * from rides where r_id =%d".formatted(r_id);
        Statement stmt = con.createStatement();
        String d = "select Date(sysdate())";
        String date = "";
        ResultSet t = stmt.executeQuery(d);
        while(t.next())
        {
            date = t.getString("Date(sysdate())");
        }
        ResultSet res = stmt.executeQuery(query);
        while(res.next())
        {
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
            System.out.println(">>>>>>>>>>>>>>>  "+"Ride Id : "+id+"  <<<<<<<<<<<<<<<<");
            System.out.println();
            System.out.println("Ride Name : "+name);
            System.out.println("Starting place of the ride : "+startplace);
            System.out.println("Destination of the ride : "+destination);
            System.out.println("When the ride starts ? "+startdate);
            System.out.println("When the ride ends ? "+enddate);
            System.out.println();
            System.out.println("Route....\n"+desc);
            System.out.println();
            System.out.println("limit   : "+memberlimit);



//            System.out.println("Today date : "+date);
//            System.out.println(startdate+" to "+enddate);


            int h = date.compareTo(startdate);
            int k =date.compareTo(enddate);




            if(h==0)
            {
                System.out.println("------> RIDE STARTED TODAY !!");
            }
            else if(h<0)
            {
                System.out.println("------> RIDE YET TO START !!");
            }
            else if(h>0 && k<0)
            {
                System.out.println("-------> RIDE IN PROGRESS !!");
            }

            else if(k==0)
            {
                System.out.println("------> LAST DAY OF THE RIDE !!");

            }
            else if(k>0){
                System.out.println("------> RIDE ENDED !!");

            }

        }
    }
}
