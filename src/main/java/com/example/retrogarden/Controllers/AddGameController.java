package com.example.retrogarden.Controllers;

import com.example.retrogarden.Database.JDBC;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class AddGameController {

    @FXML private TextField addGameName;
    @FXML private TextField addGameCondition;
    @FXML private DatePicker addGameReleaseDate;
    @FXML private TextField addGamePrice;





    //ADD GAME BUTTON PUSHED//
    public void addGameBtnPushed(ActionEvent event) throws SQLException, IOException {
        final String regExp = "[0-9]+([,.][0-9]{1,2})?";
        if(addGameName.getText() == ""|| addGameCondition.getText() == ""|| addGamePrice.getText() == ""|| addGameReleaseDate.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("One or more fields are empty!");
            alert.showAndWait();
        } else if(!addGamePrice.getText().matches(regExp)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Game Price must be an integer value!");
            alert.showAndWait();
        } else {
            //FORMAT DATE TO CORRECT FORMAT//
            LocalDate gameDate = addGameReleaseDate.getValue();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd");
            //STORE IN VARIABLE//
            LocalDate formattedGameDate = LocalDate.parse(gameDate.format(formatter));

            String sql = "INSERT INTO game (gameName, gameCondition, gameReleaseYear, gamePrice, gameUserID) VALUES (?,?,?,?,?)";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, addGameName.getText());
            ps.setString(2, addGameCondition.getText());
            ps.setString(3, formattedGameDate.toString());
            ps.setDouble(4, Double.parseDouble(addGamePrice.getText()));
            ps.setInt(5, LoginController.userID);
            ps.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Game has been added to your Retrogarden!");
            alert.showAndWait();

            Parent parent = FXMLLoader.load(getClass().getResource("/com/example/retrogarden/main-games-consoles-view.fxml"));
            Scene scene = new Scene(parent);
            //THIS LINE GETS THE STAGE INFORMATION//
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();

        }





    }

    //BACK BUTTON PUSHED//
    public void backBtnPushed(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/com/example/retrogarden/main-games-consoles-view.fxml"));
        Scene scene = new Scene(parent);
        //THIS LINE GETS THE STAGE INFORMATION//
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }














}
