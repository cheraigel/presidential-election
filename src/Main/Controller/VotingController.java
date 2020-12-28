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
    //variables for FXML TableView
    @FXML
    private TableView<Candidate> Can_Table;

    @FXML
    private TableColumn<Candidate ,String> C_Id,C_Name,C_PId,C_PName;
    //variable for candidate choice box
    @FXML
    private ChoiceBox C_Drop;
    //initialize method
    @FXML
    public void initialize()
    {
        //setting values to table view columns
        C_Id.setCellValueFactory(new PropertyValueFactory<Candidate,String>("Id"));
        C_Name.setCellValueFactory(new PropertyValueFactory<Candidate,String>("Name"));
        C_PId.setCellValueFactory(new PropertyValueFactory<Candidate,String>("Party_Id"));
        C_PName.setCellValueFactory(new PropertyValueFactory<Candidate,String>("Party_Name"));
        candidatenames.clear();
        candidates.clear();
        //adding data to observable list from hashmap
        for (HashMap.Entry<String, Candidate> set : allCandidates.entrySet())
        {
            Candidate can=set.getValue();
            candidatenames.add(can.getName());
            candidateids.add(can.getId());
            candidates.add(can);
        }
        //setting datasource for choice box
        C_Drop.setItems(candidatenames);
        //setting datasource for table view
        Can_Table.setItems(candidates);
    }
    //function for vote when vote button is clicked
    @FXML
    public void vote(ActionEvent e)
    {
        final Alert[] a = new Alert[3];
        //gets selected index from choice box
        String Cand_Id= candidateids.get(C_Drop.getSelectionModel().getSelectedIndex());
        //show confirmation dialog
        a[0] =new Alert(Alert.AlertType.INFORMATION);
        a[0].setContentText("Are You Sure ?");
        ButtonType ok = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.NO);
        a[0].getButtonTypes().setAll(ok, no);
        a[0].showAndWait().ifPresent(type ->
                {
                    if (type == ok)
                    {
                        //if ok vote will stored in a hashmap
                        Vote vot=new Vote(Ballot_Id,Cand_Id);
                        allVotes.put(Ballot_Id,vot);
                        a[1] =new Alert(AlertType.INFORMATION);
                        a[1].setContentText("Successfully voted !");
                        a[1].show();
                        try
                        {
                            //closes voting window
                            Stage voting=new Stage();
                            voting=(Stage) ((Node)e.getSource()).getScene().getWindow();
                            voting.close();
                        }
                        catch(Exception ex)
                        {
                            a[2] = new Alert(AlertType.ERROR);
                            a[2].setContentText(ex.getMessage());
                            a[2].show();
                        }
                    }
                    else if (type == no)
                    {

                    }
                }
            );

    }

}
