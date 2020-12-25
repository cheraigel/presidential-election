package Main.Controller;

import Main.Model.Candidate;
import Main.Model.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.HashMap;

public class VotingController extends Main
{
    @FXML
    private TextField U_Id;

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
        int select_index= C_Drop.getSelectionModel().getSelectedIndex();

        System.out.println(select_index);
    }

}
