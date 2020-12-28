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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Scanner;

public class AdminController extends Main
{
    //variables for FXML TextFields
    @FXML
    private TextField C_ID,C_Name,C_P_Id,C_P_Name,C_Address,C_TelNo,C_Age,C_NIC;
    //variables for table view and its components
    @FXML
    private TableView<Candidate> T_View;

    @FXML
    private TableColumn<Candidate,String> T_Id,T_Name,T_NIC,T_PId,T_PName,T_Address,T_TelNo;

    @FXML
    private TableColumn<Candidate, Integer> T_Age;

    private Alert a;

    //this function is for import ballots file to the system when button clicked
    @FXML
    public void ballot_import(ActionEvent e)throws IOException
    {
        //checks the voting status
        if(voting_state==1)
        {
            a=new Alert(AlertType.ERROR);
            a.setContentText("You Cannot Import Ballots During Voting !");
            a.show();
        }
        //if not started voting the ballot file will imported
        else
        {
            //setting file chooser for get text file
            FileChooser fc = new FileChooser();
            Stage filechooser=new Stage();
            Button select = new Button("Select File");
            select.setOnAction(f -> {
                File Ballotpaper = fc.showOpenDialog(filechooser);
            });
            Node node = (Node) e.getSource();
            //allows only .txt files to import
            fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
            File selectedFile =fc.showOpenDialog(node.getScene().getWindow());
            if (selectedFile != null)
            {
                if (selectedFile.exists())
                {
                    //if file selected the data will read line by line and put in hashmap
                    try
                    {
                        File Ballot_File = new File(selectedFile.getPath());
                        Scanner Ballot_Reader = new Scanner(Ballot_File);
                        while (Ballot_Reader.hasNextLine())
                        {
                            //split line by '|' character and stored in local string array
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
                        a = new Alert(AlertType.ERROR);
                        a.setContentText(ex.getMessage());
                        a.show();
                    }
                }
            }
        }
    }
    //this function will start voting when start vote button is clicked
    @FXML
    public void start_vote(ActionEvent e)
    {
        //calling functions to get ballot count and candidate count
        can_counter();
        ball_counter();
        //checks there are any candidates and ballots
        if((candidate_count!=0)&&(ballot_count!=0))
        {
            //if candidates and voters available, voting will start
            voting_state=1;
            a=new Alert(Alert.AlertType.INFORMATION);
            a.setContentText("Voting Has Been Started !");
            a.show();
        }
        //for 0 candidate count with ballots
        else if ((candidate_count==0)&&(ballot_count!=0))
        {
            a=new Alert(Alert.AlertType.ERROR);
            a.setContentText("Cannot Start Voting With 0 Candidates !");
            a.show();
        }
        //for 0 ballots with candidates
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
    //function for getting candidate vales from TextFields and put in hashmap and database
    @FXML
    public void candidateadd()
    {
        //checks if the voting has started
        if(voting_state==1)
        {
            a=new Alert(AlertType.ERROR);
            a.setContentText("You Cannot Add Candidates While Voting !");
            a.show();
        }
        //allows to add data to database only when voting has not started
        else
        {
            //validation for age field is integer or not
            int error=text_parse();
            if(error==1)
            {
                a = new Alert(AlertType.ERROR);
                a.setContentText("Please Enter A Number For Age");
                a.show();
            }
            // if age is integer the data will added
            else
            {
                a = new Alert(AlertType.INFORMATION);
                //create candidate objects
                Candidate can = new Candidate(CId,CName,CNIC,CPId,CPName,CAddress,CTelNo,CAge);
                //insert candidate data into mysql database
                try
                {
                    //run sql query to insert
                    con.createStatement().execute("insert into candidates(candidate_id,candidate_name,nic,party_id,party_name,address,tel_no,age)values ('" + CId + "','" + CName + "','" + CNIC + "','" + CPId + "','" + CPName + "','" + CAddress + "','" + CTelNo + "','" + CAge + "')");
                    a.setContentText("Successfully Added !");
                    a.show();
                    //put data in hashmap
                    allCandidates.put(CId, can);
                    //clear observale list
                    candidates.clear();
                    //add data in hashmap into observable list
                    for (HashMap.Entry<String, Candidate> set : allCandidates.entrySet())
                    {
                        can = set.getValue();
                        candidates.add(can);
                    }
                    //set observable list to tableview
                    T_View.setItems(candidates);
                    //clear TextFields after adding data
                    text_clear();
                }
                catch (Exception ex)
                {
                    a = new Alert(AlertType.ERROR);
                    a.setContentText(ex.getMessage());
                    a.show();
                }
            }
        }
    }
    //function for search for record in hashmap when search button clicked
    @FXML
    public void candidatesearch()
    {
        try
        {
            //getting object from hashmap
            Candidate can = (Candidate)allCandidates.get(C_ID.getText());
            //set values to TextFields by object
            C_Name.setText(can.getName());
            C_NIC.setText(can.getNIC());
            C_P_Id.setText(can.getParty_Id());
            C_P_Name.setText(can.getParty_Name());
            C_Address.setText(can.getAddress());
            C_TelNo.setText(can.getTelNo());
            C_Age.setText(String.valueOf(can.getAge()));
        }
        //catch when no result found
        catch(NullPointerException ex)
        {
            Alert a=new Alert(AlertType.WARNING);
            a.setContentText("Couldn't Find Any Records !");
            a.show();
        }

    }
    //function to clear TextFields
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
    //function to parse data from TextFields and assign into variables
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
        //checks age integer validation
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
    //function for edit candidate details in hashmap and database
    @FXML
    public void candidateedit()
    {
        //checks if the voting is stated or not
        if(voting_state==1)
        {
            a=new Alert(AlertType.ERROR);
            a.setContentText("You Cannot Edit Candidates While Voting !");
            a.show();
        }
        //allows to update data only when voting has not started
        else
        {
            //age type validation
            int error=text_parse();
            if(error==1)
            {
                a = new Alert(AlertType.ERROR);
                a.setContentText("Please Enter A Number For Age");
                a.show();
            }
            //allows to updates values
            else
            {
                a = new Alert(Alert.AlertType.INFORMATION);
                try
                {
                    //sql query
                    con.createStatement().execute("update candidates set candidate_name='" + CName + "',nic='" + CNIC + "',party_id='" + CPId + "',party_name='" + CPName + "',address='" + CAddress + "',tel_no='" + CTelNo + "',age='" + CAge + "' where candidate_id='" + CId + "'");
                    a.setContentText("Successfully Updated !");
                    a.show();
                    Candidate can = new Candidate(CId,CName,CNIC,CPId,CPName,CAddress,CTelNo,CAge);
                    //update in hashmap
                    allCandidates.put(CId, can);
                    //update in observable list
                    candidates.clear();
                    for (HashMap.Entry<String, Candidate> set : allCandidates.entrySet())
                    {
                        can = set.getValue();
                        candidates.add(can);
                    }
                    T_View.setItems(candidates);
                    //clear TextFields
                    text_clear();
                }
                catch (Exception ex)
                {
                    a = new Alert(AlertType.ERROR);
                    a.setContentText(ex.getMessage());
                    a.show();
                }
            }
        }
    }
    //function for delete candidates
    @FXML
    public void candidatedelete()
    {
        //checks whether tho voting has started or not
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
            //run sql query to delete record
            try
            {
                con.createStatement().execute("delete from candidates where candidate_id='" + CId + "'");
                //delete object from hashmap
                allCandidates.remove(CId);
                text_clear();
                candidates.clear();
                //delete object from observable list
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
                a = new Alert(AlertType.ERROR);
                a.setContentText(ex.getMessage());
                a.show();
            }
        }
    }
    //function for logout admin
    @FXML
    public void logout(ActionEvent e)
    {
        //re open login window
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
            a = new Alert(AlertType.ERROR);
            a.setContentText(ex.getMessage());
            a.show();
        }
    }
    //function to end voting
    @FXML
    public void end_vote()
    {
        Alert a;
        //checks is the voting has started
        if(voting_state==0)
        {
            a=new Alert(Alert.AlertType.WARNING);
            a.setContentText("Please Start Voting First !");
            a.show();
        }
        else
        {
            //prints graph of votes for each candidate
            for (HashMap.Entry<String, Candidate> set1 : allCandidates.entrySet())
            {
                Candidate can = set1.getValue();
                System.out.print(can.getName()+"\t : \t");
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
                System.out.print(can_count+" | ");
                for(int i=0;i<can_count;i++)
                {
                    System.out.print("*");
                }
                System.out.println();
            }

            /*for(int i=0;i<max;i++)
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
            }*/
            //end voting
            voting_state=2;
            a=new Alert(Alert.AlertType.INFORMATION);
            a.setContentText("Voting Has Been Ended !");
            a.show();
        }
    }
    //function to get candidate count
    public void can_counter()
    {
        candidate_count=0;
        for (HashMap.Entry<String,Candidate> set : allCandidates.entrySet())
        {
            candidate_count++;
        }
    }
    //function for get voters count
    public void ball_counter()
    {
        ballot_count=0;
        for (HashMap.Entry<String,Ballot> set : allBallots.entrySet())
        {
            ballot_count++;
        }
    }
    //function for getting candidates from database and store in hashmap
    public static void candidate_add()
    {
        Alert a;
        //run sql query
        try
        {
            ResultSet r = con.createStatement().executeQuery("select * from candidates");
            while (r.next())
            {
                //create objects and put in hashmap
                Candidate can=new Candidate(r.getString("candidate_id"),r.getString("candidate_name"),r.getString("nic"),r.getString("party_id"),r.getString("party_name"),r.getString("address"),r.getString("tel_no"),r.getInt("age"));
                allCandidates.put(r.getString("candidate_id"),can);
            }
        }
        catch(Exception ex)
        {
            a = new Alert(AlertType.ERROR);
            a.setContentText(ex.getMessage());
            a.show();
        }
    }
    //initialize method
    @FXML
    public void initialize()
    {
        //calling method to retrieve candidate data from database and put in hashmap
        candidate_add();
        candidates.clear();
        //setting data for table view columns
        T_Id.setCellValueFactory(new PropertyValueFactory<Candidate,String>("Id"));
        T_Name.setCellValueFactory(new PropertyValueFactory<Candidate,String>("Name"));
        T_NIC.setCellValueFactory(new PropertyValueFactory<Candidate,String>("NIC"));
        T_PId.setCellValueFactory(new PropertyValueFactory<Candidate,String>("Party_Id"));
        T_PName.setCellValueFactory(new PropertyValueFactory<Candidate,String>("Party_Name"));
        T_Address.setCellValueFactory(new PropertyValueFactory<Candidate,String>("Address"));
        T_TelNo.setCellValueFactory(new PropertyValueFactory<Candidate,String>("TelNo"));
        T_Age.setCellValueFactory(new PropertyValueFactory<Candidate, Integer>("Age"));
        //add data into observable list from hashmap
        for (HashMap.Entry<String,Candidate> set : allCandidates.entrySet())
        {
            Candidate can=set.getValue();
            candidates.add(can);
            //System.out.println(can.getTelNo());
        }
        //set observable list to table view datasource
        T_View.setItems(candidates);
    }
}
