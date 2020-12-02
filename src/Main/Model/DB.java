package Main.Model;
import javafx.scene.control.Alert;

import java.sql.*;

public class DB
{
    private Connection con;

    public void DBase()
    {
        //Alert msgalert = new Alert(Alert.AlertType.ERROR);

        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost/election?useSSL=false","root","");
            System.out.println("Connecting To The Local DataBase ...");
        }
        catch(Exception e)
        {
            //msgalert.setContentText(e.getMessage());
            //msgalert.show();
            e.printStackTrace();
        }
    }
    public Connection getCon()
    {
        return con;
    }

    public void setCon(Connection con)
    {
        this.con = con;
    }
}
