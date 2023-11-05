package com.example.twoupremake;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.sql.*;


public class loginController {
    @FXML
    private Button cancelButton;
    @FXML Label loginMessageLabel;
    @FXML TextField usernameTextField;
    @FXML PasswordField passwordPasswordField;
    @FXML Button SignupButton ;

    public void cancelButtonOnAction(ActionEvent e){
        Stage stage =(Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
    public void loginButtonOnAction(ActionEvent actionEvent) {
        if (usernameTextField.getText().isBlank() ==false && passwordPasswordField.getText().isBlank()==false ){
            //loginMessageLabel.setText("you logged in but not really");
            validateLogin();

        } else {
            loginMessageLabel.setText("please enter correct user name and password");
        }

    }
    public void setSignupButtonAction(ActionEvent actionEvent){
        if (usernameTextField.getText().isBlank() ==false && passwordPasswordField.getText().isBlank()==false ){
            try {
                Connection connection = DatabaseConnection.getConnection();
                if (connection != null) {
                    // Create a prepared statement
                    String insertUserSQL = "INSERT INTO useraccounts (Username, Password,Highscore) VALUES (?, ?,?)";
                    ;
                    PreparedStatement preparedStatement = connection.prepareStatement(insertUserSQL);

                    // Set the username and password as parameters
                    String Username = usernameTextField.getText();
                    preparedStatement.setString(1, Username);
                    String Password = passwordPasswordField.getText() ;
                    preparedStatement.setString(2, Password);
                    int Highscore = 0;
                    preparedStatement.setInt(3, Highscore);

                    // Execute the insert query
                    int rowsInserted = preparedStatement.executeUpdate();
                    if (rowsInserted > 0) {
                        System.out.println("User inserted successfully.");
                    } else {
                        System.err.println("User insertion failed.");
                    }

                    // Close the prepared statement and the connection
                    preparedStatement.close();
                    connection.close();
                } else {
                    System.err.println("Failed to obtain a database connection.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("User insertion error: " + e.getMessage());
            }
        }
    }
    public void validateLogin(){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String verifyLogin ="select count(1) from useraccounts where Username = '"+usernameTextField.getText()+"' and Password = '"+passwordPasswordField.getText()+"'";
        try{
            Statement statement =connectDB.createStatement();
            ResultSet queryResult= statement.executeQuery(verifyLogin);
            while(queryResult.next()){
                if(queryResult.getInt(1)==1){
                   // loginMessageLabel.setText("welcome");
                    createGameStage();
                }else{
                    loginMessageLabel.setText("invalid user credentials, try again");
                }
            }
        }catch (Exception e){
            e.printStackTrace();}
    }

    private void createGameStage() {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("GameScreen.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) loginMessageLabel.getScene().getWindow();
            Scene scene;
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

}