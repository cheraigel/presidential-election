package Main.Controller;

import Main.Model.Main;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class VotingController extends Main
{
    @FXML
    private TextField U_Id;
    @FXML
    private ChoiceBox C_Drop;

    @FXML
    public void initialize()
    {
        C_Drop = new ChoiceBox(candidates);
    }

}
