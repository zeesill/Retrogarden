package com.example.retrogarden.Controllers;

import com.example.retrogarden.Database.JDBC;
import com.example.retrogarden.Models.Console;
import com.example.retrogarden.Models.Game;
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

public class UpdateGameController {

    @FXML private Game selectedGameFromTable;
    @FXML private TextField updateGameName;
    @FXML private TextField updateGameCondition;
    @FXML private DatePicker updateGameReleaseDate;
    @FXML private TextField updateGamePrice;

    int selectedGameId;



    public void getSelectedGame(Game game) {
        selectedGameId = game.getGameID();
        selectedGameFromTable = game;
        updateGameName.setText(game.getGameName());
        updateGameCondition.setText(game.getGameCondition());
        updateGameReleaseDate.setValue(game.getGameReleaseYear());
        updateGamePrice.setText(String.valueOf(game.getGamePrice()));
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

    //UPDATE GAME BUTTON PUSHED//
    public void updateGameBtnPushed(ActionEvent event) throws SQLException, IOException {
        final String regExp = "[0-9]+([,.][0-9]{1,2})?";

        if(updateGameName.getText() == "" || updateGameCondition.getText() == "" || updateGameReleaseDate.getValue() == null || updateGamePrice.getText() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("One or more fields are empty!");
            alert.showAndWait();
        } else if(!updateGamePrice.getText().matches(regExp)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Game Price must be an integer value!");
            alert.showAndWait();
        } else {
            String sql = "UPDATE game SET gameName = ?, gameCondition = ?, gameReleaseYear = ?, gamePrice = ? WHERE gameID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, updateGameName.getText());
            ps.setString(2, updateGameCondition.getText());
            ps.setString(3, updateGameReleaseDate.getValue().toString());
            ps.setDouble(4, Double.parseDouble(updateGamePrice.getText()));
            ps.setInt(5, selectedGameId);
            ps.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Game has been updated in your Retrogarden!");
            alert.showAndWait();

            Parent parent = FXMLLoader.load(getClass().getResource("/com/example/retrogarden/main-games-consoles-view.fxml"));
            Scene scene = new Scene(parent);
            //THIS LINE GETS THE STAGE INFORMATION//
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();

        }
    }

}
