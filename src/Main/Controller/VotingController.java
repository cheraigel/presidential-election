package Main.Controller;

import Main.Model.Ballot;
import Main.Model.Candidate;
import Main.Model.Main;
import Main.Model.Vote;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

import java.util.HashMap;

public class VotingController extends Main
{
    @FXML
    private TextField Y_Id;

    @FXML
    private ChoiceBox C_Drop;

    @FXML
    public void initialize()
    {
        candidatenames.clear();
        for (HashMap.Entry<String, Candidate> set : allCandidates.entrySet())
        {
            Candidate can=set.getValue();
            candidatenames.add(can.getCandidate_Name());
            candidateids.add(can.getCandidate_Id());
        }
        C_Drop.setItems(candidatenames);
    }

    @FXML
    public void vote()
    {
        final Alert[] a = new Alert[3];
        String Cand_Id= candidateids.get(C_Drop.getSelectionModel().getSelectedIndex());
        String Ballo_Id=Y_Id.getText();
        Ballot ball = (Ballot) allBallots.get(Ballo_Id);
        if (ball==null)
        {
            a[0] =new Alert(Alert.AlertType.WARNING);
            a[0].setContentText("Please Enter Valid Id");
            a[0].show();
        }
        else
        {
            a[0] =new Alert(Alert.AlertType.INFORMATION);
            a[0].setContentText("Are You Sure ?");
            ButtonType ok = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.NO);
            a[0].getButtonTypes().setAll(ok, no);
            a[0].showAndWait().ifPresent(type ->
                    {
                        if (type == ok)
                        {
                            Vote vot=new Vote(Ballo_Id,Cand_Id);
                            allVotes.put(Ballo_Id,vot);
                            try
                            {
                                con.createStatement().execute("insert into votes(ballot_id,candidate_id) values('"+vot.getBallot_ID()+"','"+vot.getCandidate_Id()+"')");
                                Y_Id.setText("");

                                a[1] =new Alert(AlertType.INFORMATION);
                                a[1].setContentText("Successfully Added !");
                                a[1].show();
                            }
                            catch(Exception ex)
                            {
                                if(ex.getMessage().equals("Duplicate entry '"+Ballo_Id+"' for key 'PRIMARY'"))
                                {

                                    Alert.AlertType alertAlertType;
                                    a[2]= new Alert(AlertType.ERROR);
                                    a[2].setContentText("You Have Alredy Voted !");
                                    a[2].show();
                                }
                                else
                                {
                                    ex.printStackTrace();
                                }
                            }
                        }
                        else if (type == no)
                        {
                            
                        }
                    }
            );

        }
    }

}
