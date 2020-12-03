package Main.Model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main extends Application {

    public static Connection con;
    public HashMap<String,Candidate> allCandidates=new HashMap<>();

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
}



/*
References

https://www.geeksforgeeks.org/javafx-alert-with-examples/
https://crunchify.com/how-to-iterate-through-java-list-4-way-to-iterate-through-loop/
https://stackoverflow.com/questions/20463325/how-to-return-multiple-rows-from-result-set-in-java
https://www.javatpoint.com/arraylist-vs-hashmap-in-java#:~:text=The%20difference%20between%20ArrayList%20and,implementation%2C%20function%2C%20and%20usage.

 */