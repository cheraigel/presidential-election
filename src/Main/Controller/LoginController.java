package Main.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class LoginController
{
    @FXML
    private TextField T_Uname,T_PW;
    private String Uname,PW;

    public void login()
    {
            Uname=T_Uname.getText();
            PW=T_PW.getText();
            try
            {

            }
            catch (Exception e)
            {

            }



    }
}
