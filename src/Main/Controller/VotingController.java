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
        a[0] =new Alert(Alert.AlertType.INFORMATION);
        a[0].setContentText("Are You Sure ?");
        ButtonType ok = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.NO);
        a[0].getButtonTypes().setAll(ok, no);
        a[0].showAndWait().ifPresent(type ->
                {
                    if (type == ok)
                    {
                        Vote vot=new Vote(Ballot_Id,Cand_Id);
                        allVotes.put(Ballot_Id,vot);
                        a[1] =new Alert(AlertType.INFORMATION);
                        a[1].setContentText("Successfully Added !");
                        a[1].show();
                    }
                    else if (type == no)
                    {

                    }
                }
            );

    }

}
