package RidersCompanion.Model;
import RidersCompanion.Controller.RideDAO;
import RidersCompanion.Controller.RiderDAO;

import java.sql.SQLException;
import java.util.Date;

public class Ride {
    private String ridename;
    private String startplace;
    private String endplace;
    public Date startdate;
    public Date enddate;
    private String description;
    private int membersLimit;
    private int membersAdded;

    public int getMembersAdded()
    {
        return membersAdded;
    }

    public void setMembersAdded(int count)
    {
        this.membersAdded = count;
    }
    public String getRidename() {
        return ridename;
    }

    public void setRidename(String ridename) {
        this.ridename = ridename;
    }

    public String getStartplace() {
        return startplace;
    }

    public void setStartplace(String startplace) {
        this.startplace = startplace;
    }

    public String getEndplace() {
        return endplace;
    }

    public void setEndplace(String endplace) {
        this.endplace = endplace;
    }

    public int getMembersLimit() {
        return membersLimit;
    }

    public void setMembersLimit(int membersLimit) {
        this.membersLimit = membersLimit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }
    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }


    public static void createdRideStatus(int rider_id) {
        try {
            RideDAO.showRideCreatedStatus(rider_id);
        } catch (SQLException e) {
            System.out.println("Created ride status error"+e);
        }
    }
    public static void joinedRideStatus(int rider_id)
    {
        try {
            RiderDAO.showRideJoinedStatus(rider_id);
        } catch (SQLException e) {
            System.out.println("Joined ride status error"+e);
        }
    }



}
