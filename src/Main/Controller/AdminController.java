package Main.Controller;

import Main.Model.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Scanner;

public class AdminController extends Main
{
    @FXML
    private TextField C_ID;

    @FXML
    private TextField C_Name;

    @FXML
    private TableView<Candidate> T_View;

    @FXML
    private TableColumn<Candidate,String> T_Id;

    @FXML
    private TableColumn<Candidate, String> T_Name;


    private String Candidate_ID,Candidate_Name;

    private Alert a;

    @FXML
    public void ballot_import()throws IOException
    {
        if(voting_state==1)
        {
            a=new Alert(AlertType.ERROR);
            a.setContentText("You Cannot Import Ballots During Voting !");
            a.show();
        }
        else
        {
            try
            {
                File Ballot_File = new File("Ballots.txt");
                Scanner Ballot_Reader = new Scanner(Ballot_File);
                while (Ballot_Reader.hasNextLine())
                {
                    String[] ballot = Ballot_Reader.nextLine().split("\\|");
                    //System.out.println(ballot[0]+" k "+ballot[1]);
                    Ballot bal = new Ballot(ballot[0], ballot[1]);
                    allBallots.put(ballot[0], bal);
                }
                a = new Alert(AlertType.INFORMATION);
                a.setContentText("Successfully Imported !");
                a.show();
                Ballot_Reader.close();
            }
            catch (FileNotFoundException ex)
            {
                System.out.println("An error occurred.");
                ex.printStackTrace();
            }
        }
    }

    @FXML
    public void start_vote(ActionEvent e)
    {
        can_counter();
        ball_counter();
        if((candidate_count!=0)&&(ballot_count!=0))
        {
            voting_state=1;
            a=new Alert(Alert.AlertType.INFORMATION);
            a.setContentText("Voting Has Been Started !");
            a.show();
        }
        else if ((candidate_count==0)&&(ballot_count!=0))
        {
            a=new Alert(Alert.AlertType.ERROR);
            a.setContentText("Cannot Start Voting With 0 Candidates !");
            a.show();
        }
        else if ((ballot_count==0)&&(candidate_count!=0))
        {
            a=new Alert(Alert.AlertType.ERROR);
            a.setContentText("Cannot Start Voting With 0 Ballots !");
            a.show();
        }
        else
        {
            a=new Alert(Alert.AlertType.ERROR);
            a.setContentText("Cannot Start Voting With 0 Candidates And 0 Ballots !");
            a.show();
        }
    }

    @FXML
    public void candidateadd()
    {
        if(voting_state==1)
        {
            a=new Alert(AlertType.ERROR);
            a.setContentText("You Cannot Add Candidates While Voting !");
            a.show();
        }
        else
        {
            a = new Alert(Alert.AlertType.INFORMATION);

            Candidate_ID = C_ID.getText();
            Candidate_Name = C_Name.getText();

            Candidate can = new Candidate(Candidate_ID, Candidate_Name);

            try
            {
                con.createStatement().execute("insert into candidates(candidate_id,candidate_name)values ('" + can.getCandidate_Id() + "','" + can.getCandidate_Name() + "')");
                a.setContentText("Successfully Added !");
                a.show();
                C_ID.setText("");
                C_Name.setText("");
                allCandidates.put(Candidate_ID, can);
                candidates.clear();
                for (HashMap.Entry<String, Candidate> set : allCandidates.entrySet())
                {
                    can = set.getValue();
                    candidates.add(can);
                }
                T_View.setItems(candidates);
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }

    public void candidatesearch()
    {
        try
        {
            Candidate can=candidate_search(C_ID);
            C_Name.setText(can.getCandidate_Name());
        }
        catch(NullPointerException ex)
        {
            Alert a=new Alert(Alert.AlertType.WARNING);
            a.setContentText("Couldn't Find Any Records !");
            a.show();
        }

    }

    public void candidateedit()
    {
        if(voting_state==1)
        {
            a=new Alert(AlertType.ERROR);
            a.setContentText("You Cannot Edit Candidates While Voting !");
            a.show();
        }
        else
        {
            a = new Alert(Alert.AlertType.INFORMATION);
            Candidate_ID = C_ID.getText();
            Candidate_Name = C_Name.getText();
            try
            {
                con.createStatement().execute("update candidates set candidate_name='" + Candidate_Name + "' where candidate_id='" + Candidate_ID + "'");
                a.setContentText("Successfully Updated !");
                a.show();
                Candidate can = new Candidate(Candidate_ID, Candidate_Name);
                C_ID.setText("");
                C_Name.setText("");
                allCandidates.put(Candidate_ID, can);
                candidates.clear();
                for (HashMap.Entry<String, Candidate> set : allCandidates.entrySet())
                {
                    can = set.getValue();
                    candidates.add(can);
                }
                T_View.setItems(candidates);
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }

    public void candidatedelete()
    {
        if(voting_state==1)
        {
            a=new Alert(AlertType.ERROR);
            a.setContentText("You Cannot Delete Candidates While Voting !");
            a.show();
        }
        else
        {
            a = new Alert(Alert.AlertType.WARNING);
            try
            {
                con.createStatement().execute("delete from candidates where candidate_id='" + C_ID.getText() + "'");
                allCandidates.remove(C_ID.getText());
                C_ID.setText("");
                C_Name.setText("");
                candidates.clear();
                for (HashMap.Entry<String, Candidate> set : allCandidates.entrySet())
                {
                    Candidate can = set.getValue();
                    candidates.add(can);
                }
                T_View.setItems(candidates);
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }

    @FXML
    public void logout(ActionEvent e)
    {
        try
        {
            Stage login=new Stage();
            Stage admin=new Stage();
            Parent root2 = FXMLLoader.load(getClass().getResource("../View/login.fxml"));
            login.setTitle("Login");
            login.setScene(new Scene(root2, 780, 475));
            login.show();
            admin=(Stage) ((Node)e.getSource()).getScene().getWindow();
            admin.close();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @FXML
    public void end_vote()
    {
        Alert a;
        if(voting_state==0)
        {
            a=new Alert(Alert.AlertType.WARNING);
            a.setContentText("Please Start Voting First !");
            a.show();
        }
        else
        {
            for (HashMap.Entry<String, Candidate> set1 : allCandidates.entrySet())
            {
                Candidate can = set1.getValue();
                //System.out.print(can.getCandidate_Name()+"\t : \t");
                int can_count=0;
                for (HashMap.Entry<String, Vote> set2 : allVotes.entrySet())
                {
                    Vote vot = set2.getValue();
                    if(vot.getCandidate_Id().equals(can.getCandidate_Id()))
                    {
                        can_count++;
                    }
                }
                CandidateCount ccount=new CandidateCount(can.getCandidate_Id(),can_count);
                allCounts.put(can.getCandidate_Id(),ccount);
                if(can_count>max)
                {
                    max=can_count;
                }
                /*System.out.print(can_count+" | ");
                for(int i=0;i<can_count;i++)
                {
                    System.out.print("*");
                }
                System.out.println();*/
            }

            for(int i=0;i<max;i++)
            {
                System.out.println();
                for (HashMap.Entry<String, CandidateCount> set : allCounts.entrySet())
                {
                    CandidateCount ccount = set.getValue();
                    int count=ccount.getCandidate_Count();
                    if(count<max)
                    {
                        System.out.print(" \t\t\t");
                    }
                    else
                    {
                        System.out.print("-\t\t\t");
                    }
                }
            }
            System.out.println();
            for (HashMap.Entry<String, Candidate> set : allCandidates.entrySet())
            {
                Candidate can = set.getValue();
                System.out.print(can.getCandidate_Name()+" \t\t\t");
            }
            voting_state=2;
            a=new Alert(Alert.AlertType.INFORMATION);
            a.setContentText("Voting Has Been Ended !");
            a.show();
        }
    }

    @FXML
    public void initialize()
    {
        candidates.clear();
        T_Id.setCellValueFactory(new PropertyValueFactory<Candidate,String>("Candidate_Id"));
        T_Name.setCellValueFactory(new PropertyValueFactory<Candidate,String>("Candidate_Name"));
        for (HashMap.Entry<String,Candidate> set : allCandidates.entrySet())
        {
            Candidate can=set.getValue();
            candidates.add(can);
        }
        T_View.setItems(candidates);
    }
}
