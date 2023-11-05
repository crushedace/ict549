package com.example.twoupremake;
import javafx.animation.Animation;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class GameController {


    @FXML
    public static ImageView coin1;
    @FXML
    public static ImageView coin2;
    @FXML
    private Label scoreLabel;
    @FXML
    private ListView Highscore;
    @FXML
    private Button SubmitScore;

    private int point = 0;

    // Define RotateTransition for coin1 and coin2
    public RotateTransition coin1Rotation;
    public RotateTransition coin2Rotation;
    public GameController() {
        // Initialize the RotateTransitions in the constructor
        coin1Rotation = createRotateTransition(coin1);
        coin2Rotation = createRotateTransition(coin2);
    }

    // Create a RotateTransition for an ImageView
    private RotateTransition createRotateTransition(ImageView imageView) {
        RotateTransition rotation = new RotateTransition(Duration.seconds(1), imageView);
        rotation.setByAngle(360); // Rotate by 360 degrees
        rotation.setCycleCount(Animation.INDEFINITE); // Infinite rotation
        return rotation;
    }

    public void TailsButtonOnAction(ActionEvent event) {
        System.out.println("tails");
        coin1Rotation.play();
        coin2Rotation.play(); // Start the coin rotation animations

        if (Math.random() < 0.5) {
            // Delay result display and stopping animations by 2 seconds
            PauseTransition delay = new PauseTransition(Duration.seconds(2));
            delay.setOnFinished(e -> {
                System.out.println("Tails/Tails");
                point += 2;
                scoreLabel.setText("Score: " + point);
                ImageView myImage = new ImageView("file:/C:/java%20projects/2upRemake/src/main/java/com/example/twoupremake/Gold_21.png");
                coin1.setImage(myImage.getImage());
                coin2.setImage(myImage.getImage());
                coin1Rotation.stop();
                coin2Rotation.stop(); // Stop the coin rotation animations
            });
            delay.play();
        } else {
            // Delay result display and stopping animations by 2 seconds
            PauseTransition delay = new PauseTransition(Duration.seconds(2));
            delay.setOnFinished(e -> {
                System.out.println("HEADS/HEADS");
                point -= 3;
                scoreLabel.setText("Score: " + point);
                Image myImage = new Image("file:/C:/java%20projects/2upRemake/src/main/java/com/example/twoupremake/Gold_1.png");
                coin1.setImage(myImage);
                coin2.setImage(myImage);
                coin1Rotation.stop();
                coin2Rotation.stop(); // Stop the coin rotation animations
            });
            delay.play();
        }
    }

    public void HeadsButtonOnAction(ActionEvent event) {
        System.out.println("heads");
        coin1Rotation.play();
        coin2Rotation.play(); // Start the coin rotation animations

        if (Math.random() < 0.5) {
            // Delay result display and stopping animations by 2 seconds
            PauseTransition delay = new PauseTransition(Duration.seconds(2));
            delay.setOnFinished(e -> {
                System.out.println("HEADS/HEADS");
                point += 2;
                scoreLabel.setText("Score: " + point);
                Image myImage = new Image("file:/C:/java%20projects/2upRemake/src/main/java/com/example/twoupremake/Gold_1.png");
                coin1.setImage(myImage);
                coin2.setImage(myImage);
                coin1Rotation.stop();
                coin2Rotation.stop(); // Stop the coin rotation animations
            });
            delay.play();
        } else {
            // Delay result display and stopping animations by 2 seconds
            PauseTransition delay = new PauseTransition(Duration.seconds(2));
            delay.setOnFinished(e -> {
                System.out.println("Tails/Tails");
                point -= 1;
                scoreLabel.setText("Score: " + point);
                Image myImage = new Image("file:/C:/java%20projects/2upRemake/src/main/java/com/example/twoupremake/Gold_21.png");

                coin1.setImage(myImage);
                coin2.setImage(myImage);
                coin1Rotation.stop();
                coin2Rotation.stop(); // Stop the coin rotation animations
            });
            delay.play();
        }
    }
    public void SubmitScore(ActionEvent event) {
        System.out.println("Your score is " + point);
        String uniqueIdentifier = "Username";
        String username = Username(uniqueIdentifier);

        String updateHighscoreSQL = "UPDATE useraccounts SET Highscore = ? WHERE Username = ?";

        try {
            Connection connection = DatabaseConnection.getConnection();

            if (connection != null) {
                PreparedStatement preparedStatement = connection.prepareStatement(updateHighscoreSQL);
                preparedStatement.setInt(1, point);
                preparedStatement.setString(2, username);
                int rowsUpdated = preparedStatement.executeUpdate();

                if (rowsUpdated > 0) {
                    System.out.println("Highscore updated successfully.");
                } else {
                    System.out.println("Highscore update failed. User not found.");
                }
                preparedStatement.close();
                connection.close();
            } else {
                System.err.println("Failed to obtain a database connection.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Highscore update failed: " + e.getMessage());
        }
    }

    private String Username(String uniqueIdentifier) {

        return uniqueIdentifier;
    }


    public void HighScore(ListView.EditEvent editEvent) {
    }
}



