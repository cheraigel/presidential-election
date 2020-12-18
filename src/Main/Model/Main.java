package Main.Model;

import com.mysql.cj.xdevapi.Table;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.HashMap;

public class Main extends Application {

    public static Connection con;
    public static HashMap<String,Candidate> allCandidates=new HashMap<>();
    public static ObservableList<Candidate> candidates = FXCollections.observableArrayList();



    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../View/login.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 600, 475));
        primaryStage.show();
    }

    public static void main(String[] args)
    {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con= DriverManager.getConnection("jdbc:mysql://localhost/election?useSSL=false","root","");
            System.out.println("Connecting To The Local DataBase ...");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        launch(args);
    }

    public static void candidate_add()
    {
        try
        {
            ResultSet r = con.createStatement().executeQuery("select * from candidates");
            while (r.next())
            {
                String Candidate_ID = r.getString("candidate_id");
                String Candidate_Name=r.getString("candidate_name");
                Candidate can=new Candidate(Candidate_ID,Candidate_Name);
                allCandidates.put(Candidate_ID,can);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void table_refresh(TableView<Candidate> TV)
    {
        TV.setItems(candidates);
    }
    {
        /*for (HashMap.Entry<String,Candidate> set : allCandidates.entrySet())
        {
            Candidate can=set.getValue();
            candidates.add(new TableLoad(can.getCandidate_Id(),can.getCandidate_Name()));
            //tbData.setItems(member);
        }*/
    }

    public static Candidate candidate_search(TextField C_ID)
    {
        Candidate can = (Candidate)allCandidates.get(C_ID.getText());
        return can;
    }
}



/*
References

https://www.geeksforgeeks.org/javafx-alert-with-examples/
https://crunchify.com/how-to-iterate-through-java-list-4-way-to-iterate-through-loop/
https://stackoverflow.com/questions/20463325/how-to-return-multiple-rows-from-result-set-in-java
https://www.javatpoint.com/arraylist-vs-hashmap-in-java#:~:text=The%20difference%20between%20ArrayList%20and,implementation%2C%20function%2C%20and%20usage.
https://stackoverflow.com/questions/17526608/how-to-find-an-object-in-an-arraylist-by-property
https://thispointer.com/java-how-to-update-the-value-of-an-existing-key-in-hashmap-put-vs-replace/
https://www.geeksforgeeks.org/hashmap-remove-method-in-java/#:~:text=HashMap.,particular%20key%20in%20the%20Map.&text=Parameters%3A%20The%20method%20takes%20one,be%20removed%20from%20the%20Map.


 */