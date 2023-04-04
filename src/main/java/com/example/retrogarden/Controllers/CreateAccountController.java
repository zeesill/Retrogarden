package com.example.retrogarden.Controllers;

import com.example.retrogarden.Database.JDBC;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CreateAccountController {
    @FXML private TextField newUsername;
    @FXML private TextField newPassword;
    @FXML private TextField newConfirmPassword;
    @FXML private TextArea newPasswordHint;









    //BACK BUTTON PUSHED//
    public void backBtnPushed(ActionEvent event) throws IOException {
        //SWITCH SCENE//
        Parent parent = FXMLLoader.load(getClass().getResource("/com/example/retrogarden/user-login-view.fxml"));
        Scene scene = new Scene(parent);
        //THIS LINE GETS THE STAGE INFORMATION//
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    //CREATE ACCOUNT BUTTON PUSHED//
    public void createAccountBtnPushed(ActionEvent event) throws SQLException, IOException {
        //EMPTY TEXT FIELDS?//
        if (newUsername.getText() == "" || newPassword.getText() == "" || newConfirmPassword.getText() == "" || newPasswordHint.getText() == "") {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("One or more fields are empty");
            alert.showAndWait();
        //DOES BOTH PASSWORD AND CONFIRM PASSWORD MATCH?//
        } else if (!newPassword.getText().equals(newConfirmPassword.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Password and Confirm Password are both different");
            alert.showAndWait();
        //DOES USERNAME ALREADY EXIST IN THE DATABASE?//
        } else if (newUsername.getText() != null) {
            String createUsername = newUsername.getText();
            String createPassword = newPassword.getText();
            String createPasswordHint = newPasswordHint.getText();
            String sql = "SELECT * FROM user WHERE userName = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, createUsername);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("This username already exists");
                alert.showAndWait();
            //UPDATE THE DATABASE//
            } else {
                String sql2 = "INSERT INTO user (userName, userPassword, userPasswordHint) VALUES (?,?,?)";
                PreparedStatement ps2 = JDBC.connection.prepareStatement(sql2);
                ps2.setString(1, createUsername);
                ps2.setString(2, createPassword);
                ps2.setString(3, createPasswordHint);
                ps2.executeUpdate();

                //ALERT THAT IT HAS BEEN CREATED//
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Your account has been created!");
                alert.showAndWait();

                //SWITCH SCENE//
                Parent parent = FXMLLoader.load(getClass().getResource("/com/example/retrogarden/user-login-view.fxml"));
                Scene scene = new Scene(parent);
                //THIS LINE GETS THE STAGE INFORMATION//
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
            }
        }
    }
}
