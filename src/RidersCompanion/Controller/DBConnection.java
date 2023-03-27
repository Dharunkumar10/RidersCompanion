package RidersCompanion.Controller;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    private static final String url="jdbc:mysql://localhost:3306/RidersCompanion";
    private static final String uname="root";
    private static final String pass="root";

    public static Connection getConnection()
    {
        Connection con = null;
        try {
            con = DriverManager.getConnection(url,uname,pass);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return con;
    }
}
