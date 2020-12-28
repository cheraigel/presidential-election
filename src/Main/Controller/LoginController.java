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
    //variables for FXML Textfields
    @FXML
    private TextField T_Uname,T_PW,V_Id;

    //variables for store user name and password
    private String Uname="",PW="";
    //Alert
    private Alert a;

    //this method will call when login button clicked
    @FXML
    public void login(ActionEvent e)
    {
        //initialized alert for error messages
        a = new Alert(Alert.AlertType.ERROR);
        //parsing values from TextFields and store in variables
        Uname=T_Uname.getText();
        PW=T_PW.getText();
        try
        {
            //check the entered username and passwords are existing in the database
            ResultSet r=con.createStatement().executeQuery("select * from accounts where username='"+Uname+"' and password='"+PW+"'");
            if(r.next())
            {
                //if username and password correct then admin window will open
                Stage admin=new Stage();
                Stage login=new Stage();
                Parent root1 = FXMLLoader.load(getClass().getResource("../View/admin.fxml"));
                admin.setTitle("Admin Window");
                admin.setScene(new Scene(root1, 960, 600));
                admin.show();
                //login window will close after admin window opened
                login=(Stage) ((Node)e.getSource()).getScene().getWindow();
                login.close();
            }
            //alert if username or password doesnt exist in the database
            else
            {
                a.setContentText("Invalid Username Or Password !");
                a.show();
            }
        }
        //catch exceptions while running sql query and show error message in alert
        catch (Exception ex)
        {
            a.setContentText(ex.getMessage());
            a.show();
        }
    }
    //This method is for open voting window to vote when clicked start vote button
    @FXML
    public void vote_start()
    {
        Alert a;
        //checks if the vote has been started or not or ended
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
        //if voting is been started
        else
        {
            //parse value of TextField to local variable
            String Ballo_Id = V_Id.getText();
            //parse local variable value into global variable
            Ballot_Id=Ballo_Id;
            //getting objects from hashmap to verify hashmap is empty or not
            Ballot ball = (Ballot) allBallots.get(Ballo_Id);
            Vote vot = (Vote) allVotes.get(Ballo_Id);
            //checks the ballot is exists in the hashmap
            if (ball == null)
            {
                a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("Please Enter Valid Id");
                a.show();
            }
            else
            {
                //check whether the voter is voted or not
                try
                {
                    String tmp = vot.getBallot_ID();
                    a = new Alert(Alert.AlertType.ERROR);
                    a.setContentText("You Have Already Voted !");
                    a.show();
                }
                catch (Exception ex)
                {
                    //if not voted, voting window will open
                    try
                    {
                        Stage voting = new Stage();
                        Stage login = new Stage();
                        Parent root3 = FXMLLoader.load(getClass().getResource("../View/voting.fxml"));
                        voting.setTitle("Voting Window");
                        voting.setScene(new Scene(root3, 960, 600));
                        V_Id.setText("");
                        voting.show();
                    }
                    catch (Exception ex1)
                    {
                        a = new Alert(Alert.AlertType.ERROR);
                        a.setContentText(ex.getMessage());
                        a.show();
                    }
                }
            }
        }
    }
}
