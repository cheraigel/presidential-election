package Main.Model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.HashMap;

public class Main extends Application {

    public static Connection con;
    public static HashMap<String,Candidate> allCandidates=new HashMap<>();

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

    public void candidate_add()
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

 */