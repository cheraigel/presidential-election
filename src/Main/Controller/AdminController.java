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
    private TextField C_ID,C_Name,C_P_Id,C_P_Name,C_Address,C_TelNo,C_Age,C_NIC;

    @FXML
    private TableView<Candidate> T_View;

    @FXML
    private TableColumn<Candidate,String> T_Id,T_Name,T_NIC,T_PId,T_PName,T_Address,T_TelNo;

    @FXML
    private TableColumn<Candidate, Integer> T_Age;

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
            int error=text_parse();
            if(error==1)
            {
                a = new Alert(AlertType.ERROR);
                a.setContentText("Please Enter A Number For Age");
                a.show();
            }
            else
            {
                a = new Alert(AlertType.INFORMATION);
                Candidate can = new Candidate(CId,CName,CNIC,CPId,CPName,CAddress,CTelNo,CAge);

                try
                {
                    con.createStatement().execute("insert into candidates(candidate_id,candidate_name,nic,party_id,party_name,address,tel_no,age)values ('" + CId + "','" + CName + "','" + CNIC + "','" + CPId + "','" + CPName + "','" + CAddress + "','" + CTelNo + "','" + CAge + "')");
                    a.setContentText("Successfully Added !");
                    a.show();
                    allCandidates.put(CId, can);
                    candidates.clear();
                    for (HashMap.Entry<String, Candidate> set : allCandidates.entrySet())
                    {
                        can = set.getValue();
                        candidates.add(can);
                    }
                    T_View.setItems(candidates);
                    text_clear();
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        }
    }

    public void candidatesearch()
    {
        try
        {
            Candidate can=candidate_search(C_ID);
            C_Name.setText(can.getName());
            C_NIC.setText(can.getNIC());
            C_P_Id.setText(can.getParty_Id());
            C_P_Name.setText(can.getParty_Name());
            C_Address.setText(can.getAddress());
            C_TelNo.setText(can.getTelNo());
            C_Age.setText(String.valueOf(can.getAge()));
        }
        catch(NullPointerException ex)
        {
            Alert a=new Alert(AlertType.WARNING);
            a.setContentText("Couldn't Find Any Records !");
            a.show();
        }

    }

    public void text_clear()
    {
        C_ID.setText("");
        C_Name.setText("");
        C_NIC.setText("");
        C_P_Id.setText("");
        C_P_Name.setText("");
        C_Address.setText("");
        C_TelNo.setText("");
        C_Age.setText("");
    }
    public int text_parse()
    {
        int error=0;
        CId=C_ID.getText();
        CName=C_Name.getText();
        CNIC=C_NIC.getText();
        CPId=C_P_Id.getText();
        CPName=C_P_Name.getText();
        CAddress=C_Address.getText();
        CTelNo=C_TelNo.getText();
        try
        {
            CAge = Integer.parseInt(C_Age.getText());
        }
        catch(Exception ex)
        {
            error=1;
        }
        return error;
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
            int error=text_parse();
            if(error==1)
            {
                a = new Alert(AlertType.ERROR);
                a.setContentText("Please Enter A Number For Age");
                a.show();
            }
            else
            {
                a = new Alert(Alert.AlertType.INFORMATION);
                try
                {
                    con.createStatement().execute("update candidates set candidate_name='" + CName + "',nic='" + CNIC + "',party_id='" + CPId + "',party_name='" + CPName + "',address='" + CAddress + "',tel_no='" + CTelNo + "',age='" + CAge + "' where candidate_id='" + CId + "'");
                    a.setContentText("Successfully Updated !");
                    a.show();
                    Candidate can = new Candidate(CId,CName,CNIC,CPId,CPName,CAddress,CTelNo,CAge);
                    allCandidates.put(CId, can);
                    candidates.clear();
                    for (HashMap.Entry<String, Candidate> set : allCandidates.entrySet())
                    {
                        can = set.getValue();
                        candidates.add(can);
                    }
                    T_View.setItems(candidates);
                    text_clear();
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
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
            CId=C_ID.getText();
            try
            {
                con.createStatement().execute("delete from candidates where candidate_id='" + CId + "'");
                allCandidates.remove(CId);
                text_clear();
                candidates.clear();
                for (HashMap.Entry<String, Candidate> set : allCandidates.entrySet())
                {
                    Candidate can = set.getValue();
                    candidates.add(can);
                }
                T_View.setItems(candidates);
                a=new Alert(AlertType.INFORMATION);
                a.setContentText("Successfully Deleted !");
                a.show();
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
            login.setScene(new Scene(root2, 960, 600));
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
                    if(vot.getCandidate_Id().equals(can.getId()))
                    {
                        can_count++;
                    }
                }
                CandidateCount ccount=new CandidateCount(can.getId(),can_count);
                allCounts.put(can.getId(),ccount);
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
                System.out.print(can.getName()+" \t\t\t");
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
        T_Id.setCellValueFactory(new PropertyValueFactory<Candidate,String>("Id"));
        T_Name.setCellValueFactory(new PropertyValueFactory<Candidate,String>("Name"));
        T_NIC.setCellValueFactory(new PropertyValueFactory<Candidate,String>("NIC"));
        T_PId.setCellValueFactory(new PropertyValueFactory<Candidate,String>("Party_Id"));
        T_PName.setCellValueFactory(new PropertyValueFactory<Candidate,String>("Party_Name"));
        T_Address.setCellValueFactory(new PropertyValueFactory<Candidate,String>("Address"));
        T_TelNo.setCellValueFactory(new PropertyValueFactory<Candidate,String>("TelNo"));
        T_Age.setCellValueFactory(new PropertyValueFactory<Candidate, Integer>("Age"));
        for (HashMap.Entry<String,Candidate> set : allCandidates.entrySet())
        {
            Candidate can=set.getValue();
            candidates.add(can);
            //System.out.println(can.getTelNo());
        }
        T_View.setItems(candidates);
    }
}
