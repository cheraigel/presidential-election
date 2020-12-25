package Main.Controller;

import Main.Model.Ballot;
import Main.Model.Candidate;
import Main.Model.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

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

    @FXML
    public void ballot_import()throws IOException
    {
        try
        {
            File Ballot_File = new File("Ballots.txt");
            Scanner Ballot_Reader = new Scanner(Ballot_File);
            while (Ballot_Reader.hasNextLine())
            {
                String[] ballot = Ballot_Reader.nextLine().split("\\|");
                //System.out.println(ballot[0]+" k "+ballot[1]);
                Ballot bal=new Ballot(ballot[0],ballot[1]);
                allBallots.put(ballot[0],bal);
                try
                {
                    con.createStatement().execute("insert into ballots(ballot_id,ballot_name)values ('"+bal.getBallot_Id()+"','"+bal.getBallot_Name()+"')");
                }
                catch (SQLIntegrityConstraintViolationException ex)
                {
                    try
                    {
                        con.createStatement().execute("update ballots set ballot_name='"+bal.getBallot_Name()+"' where ballot_id='"+bal.getBallot_Id()+"'");
                    }
                    catch(Exception ex3)
                    {
                        ex3.printStackTrace();
                    }
                }
                catch (SQLException ex2)
                {
                    ex2.printStackTrace();
                }
            }
            Ballot_Reader.close();
        }
        catch(FileNotFoundException ex)
        {
            System.out.println("An error occurred.");
            ex.printStackTrace();
        }
    }

    @FXML
    public void tbutton()
    {
        T_Id.setCellValueFactory(new PropertyValueFactory<Candidate,String>("Candidate_Id"));
        T_Name.setCellValueFactory(new PropertyValueFactory<Candidate,String>("Candidate_Name"));
        for (HashMap.Entry<String,Candidate> set : allCandidates.entrySet())
        {
            Candidate can=set.getValue();
            candidates.add(can);
        }

        T_View.setItems(candidates);
    }
    @FXML
    public void candidateadd()
    {
        Alert a=new Alert(Alert.AlertType.INFORMATION);

        Candidate_ID=C_ID.getText();
        Candidate_Name=C_Name.getText();

        Candidate can=new Candidate(Candidate_ID,Candidate_Name);

        try
        {
            con.createStatement().execute("insert into candidates(candidate_id,candidate_name)values ('"+can.getCandidate_Id()+"','"+can.getCandidate_Name()+"')");
            a.setContentText("Successfully Added !");
            a.show();
            C_ID.setText("");
            C_Name.setText("");
            allCandidates.put(Candidate_ID,can);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
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
        Alert a=new Alert(Alert.AlertType.INFORMATION);
        Candidate_ID=C_ID.getText();
        Candidate_Name=C_Name.getText();
        try
        {
            con.createStatement().execute("update candidates set candidate_name='"+Candidate_Name+"' where candidate_id='"+Candidate_ID+"'");
            a.setContentText("Successfully Updated !");
            a.show();
            Candidate can=new Candidate(Candidate_ID,Candidate_Name);
            C_ID.setText("");
            C_Name.setText("");
            allCandidates.put(Candidate_ID,can);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void candidatedelete()
    {
        Alert a=new Alert(Alert.AlertType.WARNING);
        try
        {
            con.createStatement().execute("delete from candidates where candidate_id='"+C_ID.getText()+"'");
            allCandidates.remove(C_ID.getText());
            C_ID.setText("");
            C_Name.setText("");
            }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
