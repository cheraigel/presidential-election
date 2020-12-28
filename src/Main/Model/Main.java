package Main.Model;

import com.mysql.cj.xdevapi.Table;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.HashMap;

public class Main extends Application {

    public static Connection con;   //for mysql db connection
    public static HashMap<String,Candidate> allCandidates=new HashMap<>();  //for store candidates
    public static HashMap<String,Ballot> allBallots=new HashMap<>();    //for store ballots
    public static HashMap<String,Vote> allVotes=new HashMap<>();    //for store votes
    public static HashMap<String,CandidateCount> allCounts=new HashMap<>(); //for store results for each candidate
    public static ObservableList<Candidate> candidates = FXCollections.observableArrayList();   //observable list for view candidates on table view
    public static ObservableList<String> candidatenames = FXCollections.observableArrayList();  //observable list to store candidate names individually
    public static ObservableList<String> candidateids = FXCollections.observableArrayList();    //observable list to store candidate ids individually
    //integers for keep voting status, ballot count,candidate count and for maximum number of votes
    public static int voting_state=0,candidate_count=0,ballot_count=0,max=-1;
    //String for store ballot ids
    public static String Ballot_Id;
    //local variables to store attributes of candidates temporally
    public String CId,CName,CNIC,CPId,CPName,CAddress,CTelNo;
    public int CAge;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("../View/login.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 960, 600));
        primaryStage.show();
    }

    public static void main(String[] args)
    {
        //this is for create mysql database connection in the start of application
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
https://stackoverflow.com/questions/38142554/blank-row-in-tableview-javafx-app
https://www.w3schools.com/java/java_files_read.asp
https://stackoverflow.com/questions/34785417/javafx-fxml-controller-constructor-vs-initialize-method
https://www.geeksforgeeks.org/javafx-choicebox/#:~:text=ChoiceBox%20is%20a%20part%20of,selected%20item%20unless%20otherwise%20selected.
https://tagmycode.com/snippet/5207/yes-no-cancel-dialog-in-javafx#.X-bJDh5R2Uk
https://stackoverflow.com/questions/25491732/how-do-i-open-the-javafx-filechooser-from-a-controller-class/25491787
http://tutorials.jenkov.com/javafx/filechooser.html
https://www.programcreek.com/java-api-examples/?api=javafx.stage.FileChooser
 */