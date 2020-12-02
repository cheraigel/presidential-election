package Main.Controller;

import Main.Model.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.sql.ResultSet;

public class LoginController extends Main
{
    @FXML
    private TextField T_Uname,T_PW;

    private String Uname="",PW="";

    public void login()
    {

            Uname=T_Uname.getText();
            PW=T_PW.getText();
            try
            {
                ResultSet r=con.createStatement().executeQuery("select * from accounts where username='"+Uname+"' and password='"+PW+"'");
                if(r.next())
                {
                    System.out.println("Correct !");
                }
            }
            catch (Exception e)
            {

            }



    }
}
