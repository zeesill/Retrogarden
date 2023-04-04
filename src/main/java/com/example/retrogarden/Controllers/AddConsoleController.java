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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class AddConsoleController {
    //TEXTFIELD VIEWS//
    @FXML private TextField addConsoleName;
    @FXML private TextField addConsoleCondition;
    @FXML private DatePicker addConsoleReleaseDate;
    @FXML private TextField addConsolePrice;


    //BACK BUTTON PUSHED//
    public void backBtnPushed(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/com/example/retrogarden/main-games-consoles-view.fxml"));
        Scene scene = new Scene(parent);
        //THIS LINE GETS THE STAGE INFORMATION//
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }


    //ADD CONSOLE BUTTON PUSHED//
    public void addConsoleBtnPushed(ActionEvent event) throws SQLException, IOException {
        final String regExp = "[0-9]+([,.][0-9]{1,2})?";
        if(addConsoleName.getText() == ""|| addConsoleCondition.getText() == ""|| addConsolePrice.getText() == ""|| addConsoleReleaseDate.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("One or more fields are empty!");
            alert.showAndWait();
        } else if(!addConsolePrice.getText().matches(regExp)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Console Price must be an integer value!");
            alert.showAndWait();
        } else {
            //FORMAT DATE TO CORRECT FORMAT//
            LocalDate consoleDate = addConsoleReleaseDate.getValue();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd");
            //STORE IN VARIABLE//
            LocalDate formattedConsoleDate = LocalDate.parse(consoleDate.format(formatter));

            //INSERT INTO DATABASE//
            String sql = "INSERT INTO console (consoleName, consoleCondition, consoleReleaseYear, consolePrice, consoleUserId) VALUES (?,?,?,?,?)";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, addConsoleName.getText());
            ps.setString(2, addConsoleCondition.getText());
            ps.setString(3, formattedConsoleDate.toString());
            ps.setDouble(4, Double.parseDouble(addConsolePrice.getText()));
            ps.setInt(5, LoginController.userID);
            ps.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Console has been added to your Retrogarden!");
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
