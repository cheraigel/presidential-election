package Main.Controller;

import Main.Model.Candidate;
import Main.Model.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class AdminController extends Main
{
    @FXML
    private TextField C_ID,C_Name;

    private String Candidate_ID,Candidate_Name;

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
        Candidate can=candidate_search(C_ID);
        C_Name.setText(can.getCandidate_Name());
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
