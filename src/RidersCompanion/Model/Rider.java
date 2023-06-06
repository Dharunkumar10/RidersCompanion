package RidersCompanion.Model;
import RidersCompanion.Controller.RideDAO;
import RidersCompanion.Controller.RiderDAO;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Scanner;
public class Rider {
    int r_id;
    private String Name;
    private String EMail;
    private String pass;
    private String phno;
    private String licNo;
    private String bikeNo;


    public int getId()
    {return this.r_id;}
    public String getName()
    {return this.Name;}
    public String getpass()
    {return this.pass;}
    public String getEMail()
    {return this.EMail;}
    public String getphno()
    {return this.phno; }
    public String getLicNo()
    {return this.licNo;}
    public String getBikeNo()
    {return this.bikeNo;}

    public void setId(int id)
    {
        this.r_id=id;
    }
    public void setName(String name)
    {
        this.Name=name;
    }
    public void setPass(String pass)
    {
        this.pass=pass;
    }
    public void setEMail(String eMail)
    {
        this.EMail=eMail;
    }
    public void setPhno(String phno)
    {
        this.phno=phno;
    }
    public void setLicNo(String licNo)
    {
        this.licNo=licNo;
    }
    public void setBikeNo(String bikeNo)
    {
        this.bikeNo=bikeNo;
    }

  public void createRide(int rider_id)
  {
      Ride ride = new Ride();

      Scanner sc = new Scanner(System.in);
      System.out.print("Enter name of the ride :");
      ride.setRidename(sc.nextLine());

      System.out.print("Enter starting place of your ride :");
      ride.setStartplace(sc.nextLine());

      System.out.println("Enter Destination of your ride :");
      ride.setEndplace(sc.nextLine());

      System.out.println("Enter ride starting date in formate : dd-mm-yyyy (without space)");
      String sdate = sc.next();


          SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
          try {
              ride.setStartdate(dateFormat.parse(sdate));

          } catch (ParseException e) {
              System.out.println("date error"+e);
          }



      System.out.println("Enter ride ending date in formate : dd-mm-yyyy (without space)");
      String edate = sc.next();



          SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd-MM-yyyy");
          try {
              ride.setEnddate(dateFormat1.parse(edate));
          } catch (ParseException e) {
              System.out.println("date error"+e);
          }


      System.out.println("Enter the member limit of your ride :");
      ride.setMembersLimit(sc.nextInt());
      sc.nextLine();
      System.out.println("Enter the route of the ride :");
      ride.setDescription(sc.nextLine());


      RideDAO dao = new RideDAO();
      try {

          java.sql.Date startdate = new java.sql.Date(ride.getStartdate().getTime());
          java.sql.Date enddate = new java.sql.Date(ride.getEnddate().getTime());

          dao.create(ride.getRidename(),ride.getStartplace(),ride.getEndplace(), startdate, enddate ,ride.getDescription(), ride.getMembersLimit(), rider_id);

      } catch (SQLException e) {
          System.out.println("Create Error"+e);
      }
  }
  public boolean joinRide(int ride_id,int rider_id)
  {

      RiderDAO dao = new RiderDAO();
      try {
          if(RideDAO.isAvailable(ride_id)) {
              try {
                  if (dao.joinRide(ride_id,rider_id)) {
                      System.out.println("You where joined to the ride successfully");
                      return true;
                  } else {
                      System.out.println("!! You where already joined the ride");
                      return false;
                  }
              } catch (SQLException e) {
                  System.out.println("Join Error" + e);
              }
          }
          else
          {
              System.out.println("Sorry ! the count of members in this rides"
                      + " reached limit !!"
                      + " please find any other ride\n");

                 return false;

          }
      } catch (SQLException e) {
          System.out.println("Available Exception"+e);
      }
      return true;
  }

//    public static boolean isValid(final String date) {
//
//        boolean valid = false;
//
//        try {
//
//            // ResolverStyle.STRICT for 30, 31 days checking, and also leap year.
//            LocalDate.parse(date,
//                    DateTimeFormatter.ofPattern("d-M-uuuu")
//                            .withResolverStyle(ResolverStyle.STRICT)
//            );
//
//            valid = true;
//
//        } catch (DateTimeParseException e) {
//            e.printStackTrace();
//        }
//
//        return valid;
//    }


}
