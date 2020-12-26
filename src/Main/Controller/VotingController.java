package Main.Controller;

import Main.Model.Ballot;
import Main.Model.Candidate;
import Main.Model.Main;
import Main.Model.Vote;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

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
        Alert a;
        String Cand_Id= candidateids.get(C_Drop.getSelectionModel().getSelectedIndex());
        String Ballo_Id=Y_Id.getText();
        Ballot ball = (Ballot) allBallots.get(Ballo_Id);
        if (ball==null)
        {
            a=new Alert(Alert.AlertType.WARNING);
            a.setContentText("Please Enter Valid Id");
            a.show();
        }
        else
        {
            Vote vot=new Vote(Ballo_Id,Cand_Id);
            allVotes.put(Ballo_Id,vot);
            try
            {
                con.createStatement().execute("insert into votes(ballot_id,candidate_id) values('"+vot.getBallot_ID()+"','"+vot.getCandidate_Id()+"')");
                Y_Id.setText("");
                a=new Alert(Alert.AlertType.INFORMATION);
                a.setContentText("Successfully Added !");
                a.show();
            }
            catch(Exception ex)
            {
                if(ex.getMessage().equals("Duplicate entry '01' for key 'PRIMARY'"))
                {
                    a=new Alert(Alert.AlertType.ERROR);
                    a.setContentText("You Have Alredy Voted !");
                    a.show();
                }
                else
                {
                    ex.printStackTrace();
                }
            }
        }
    }

}
