package Main.Controller;

import Main.Model.Candidate;
import Main.Model.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.sql.ResultSet;

public class LoginController extends Main
{
    @FXML
    private TextField T_Uname,T_PW;

    private String Uname="",PW="";

    public void login(ActionEvent e)
    {
            Alert a = new Alert(Alert.AlertType.ERROR);
            Uname=T_Uname.getText();
            PW=T_PW.getText();
            try
            {
                ResultSet r=con.createStatement().executeQuery("select * from accounts where username='"+Uname+"' and password='"+PW+"'");
                if(r.next())
                {
                    Stage admin=new Stage();
                    Stage login=new Stage();
                    Parent root1 = FXMLLoader.load(getClass().getResource("../View/admin.fxml"));
                    admin.setTitle("Admin Window");
                    admin.setScene(new Scene(root1, 1200, 475));
                    admin.show();
                    login=(Stage) ((Node)e.getSource()).getScene().getWindow();
                    login.close();
                    candidate_add();
                }
                else
                {
                    a.setContentText("Invalid Username Or Password !");
                    a.show();
                }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
    }

    public void candidate_add()
    {
        try
        {
            ResultSet r = con.createStatement().executeQuery("select * from candidates");
            while (r.next())
            {
                String Candidate_ID = r.getString("candidate_id");
                String Candidate_Name=r.getString("candidate_name");
                Candidate can=new Candidate(Candidate_ID,Candidate_Name);
                allCandidates.put(Candidate_ID,can);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
