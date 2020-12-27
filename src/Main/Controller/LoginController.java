package Main.Controller;

import Main.Model.Ballot;
import Main.Model.Candidate;
import Main.Model.Main;
import Main.Model.Vote;
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
import java.util.HashMap;

public class LoginController extends Main
{
    @FXML
    private TextField T_Uname,T_PW,V_Id;

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
                    candidate_add();
                    Stage admin=new Stage();
                    Stage login=new Stage();
                    Parent root1 = FXMLLoader.load(getClass().getResource("../View/admin.fxml"));
                    admin.setTitle("Admin Window");
                    admin.setScene(new Scene(root1, 1200, 475));
                    admin.show();
                    login=(Stage) ((Node)e.getSource()).getScene().getWindow();
                    login.close();

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
    public void vote_start()
    {
        Alert a;
        if(voting_state==0)
        {
            a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("You Cannot Vote Now Please Ask For Admins To Start Voting !");
            a.show();
        }
        else if(voting_state==2)
        {
            a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("The Voting Has Been Ended !");
            a.show();
        }
        else
        {
            String Ballo_Id = V_Id.getText();
            Ballot_Id=Ballo_Id;
            Ballot ball = (Ballot) allBallots.get(Ballo_Id);
            Vote vot = (Vote) allVotes.get(Ballo_Id);
            if (ball == null)
            {
                a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("Please Enter Valid Id");
                a.show();
            }
            else
            {
                try
                {
                    String tmp = vot.getBallot_ID();
                    a = new Alert(Alert.AlertType.ERROR);
                    a.setContentText("You Have Already Voted !");
                    a.show();
                }
                catch (Exception ex)
                {
                    try
                    {
                        Stage voting = new Stage();
                        Stage login = new Stage();
                        Parent root3 = FXMLLoader.load(getClass().getResource("../View/voting.fxml"));
                        voting.setTitle("Voting Window");
                        voting.setScene(new Scene(root3, 800, 475));
                        V_Id.setText("");
                        voting.show();
                    }
                    catch (Exception ex1)
                    {
                        ex1.printStackTrace();
                    }
                }
            }
        }
    }
}
