package com.example.retrogarden.Controllers;

import com.example.retrogarden.Database.JDBC;
import com.example.retrogarden.Models.Console;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UpdateConsoleController implements Initializable {


    @FXML private Console selectedConsoleFromTable;
    @FXML private TextField updateConsoleName;
    @FXML private TextField updateConsoleCondition;
    @FXML private DatePicker updateConsoleReleaseDate;
    @FXML private TextField updateConsolePrice;

    int selectedConsoleId;

    //GET SELECTED ITEM FROM MAIN//
    public void getSelectedConsole(Console console) {
        selectedConsoleId = console.getConsoleID();
        selectedConsoleFromTable = console;
        updateConsoleName.setText(console.getConsoleName());
        updateConsoleCondition.setText(console.getConsoleCondition());
        updateConsoleReleaseDate.setValue(console.getConsoleReleaseYear());
        updateConsolePrice.setText(String.valueOf(console.getConsolePrice()));
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

    //UPDATE CONSOLE BUTTON PUSHED//
    public void updateConsoleBtnPushed(ActionEvent event) throws SQLException, IOException {
        final String regExp = "[0-9]+([,.][0-9]{1,2})?";

        if(updateConsoleName.getText() == "" || updateConsoleCondition.getText() == "" || updateConsoleReleaseDate.getValue() == null || updateConsolePrice.getText() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("One or more fields are empty!");
            alert.showAndWait();
        } else if(!updateConsolePrice.getText().matches(regExp)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Console Price must be an integer value!");
            alert.showAndWait();
        } else {
            String sql = "UPDATE console SET consoleName = ?, consoleCondition = ?, consoleReleaseYear = ?, consolePrice = ? WHERE consoleID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, updateConsoleName.getText());
            ps.setString(2, updateConsoleCondition.getText());
            ps.setString(3, updateConsoleReleaseDate.getValue().toString());
            ps.setDouble(4, Double.parseDouble(updateConsolePrice.getText()));
            ps.setInt(5, selectedConsoleId);
            ps.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Console has been updated in your Retrogarden!");
            alert.showAndWait();

            Parent parent = FXMLLoader.load(getClass().getResource("/com/example/retrogarden/main-games-consoles-view.fxml"));
            Scene scene = new Scene(parent);
            //THIS LINE GETS THE STAGE INFORMATION//
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();

        }

    }


































    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
