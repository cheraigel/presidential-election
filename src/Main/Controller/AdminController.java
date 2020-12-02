package Main.Controller;

import Main.Model.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.sql.SQLException;

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

        try
        {
            con.createStatement().execute("insert into candidates('"+Candidate_ID+"','"+Candidate_Name+"')");
            a.setContentText("Successfully Added !");
            a.show();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
