package Main.Controller;

import Main.Model.Ballot;
import Main.Model.Candidate;
import Main.Model.Main;
import Main.Model.Vote;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.util.HashMap;

public class VotingController extends Main
{

    @FXML
    private TableView<Candidate> Can_Table;

    @FXML
    private TableColumn<Candidate ,String> C_Id;

    @FXML
    private TableColumn<Candidate ,String> C_Name;

    @FXML
    private ChoiceBox C_Drop;

    @FXML
    public void initialize()
    {
        C_Id.setCellValueFactory(new PropertyValueFactory<Candidate,String>("Candidate_Id"));
        C_Name.setCellValueFactory(new PropertyValueFactory<Candidate,String>("Candidate_Name"));
        candidatenames.clear();
        candidates.clear();
        for (HashMap.Entry<String, Candidate> set : allCandidates.entrySet())
        {
            Candidate can=set.getValue();
            candidatenames.add(can.getName());
            candidateids.add(can.getId());
            candidates.add(can);
        }
        C_Drop.setItems(candidatenames);
        Can_Table.setItems(candidates);
    }

    @FXML
    public void vote(ActionEvent e)
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
                        try
                        {
                            Stage voting=new Stage();
                            voting=(Stage) ((Node)e.getSource()).getScene().getWindow();
                            voting.close();
                        }
                        catch(Exception ex)
                        {
                            ex.printStackTrace();
                        }
                    }
                    else if (type == no)
                    {

                    }
                }
            );

    }

}
