package com.example.retrogarden.Controllers;

import com.example.retrogarden.Database.JDBC;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController {
    //LOGIN TEXTFIELDS//
    @FXML private TextField usernameLogin;
    @FXML private PasswordField passwordLogin;

    //GET USER ID//
    static int userID = 0;





    //CREATE ACCOUNT BUTTON//
    public void createAccountBtnPushed(ActionEvent event) throws IOException {
        //SWITCH SCENE//
        Parent parent = FXMLLoader.load(getClass().getResource("/com/example/retrogarden/create-account-view.fxml"));
        Scene scene = new Scene(parent);
        //THIS LINE GETS THE STAGE INFORMATION//
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    //FORGOT PASSWORD LINK//
    public void forgotPasswordLinkPushed(ActionEvent event) throws IOException {
        //SWITCH SCENE//
        Parent parent = FXMLLoader.load(getClass().getResource("/com/example/retrogarden/forgot-password-view.fxml"));
        Scene scene = new Scene(parent);
        //THIS LINE GETS THE STAGE INFORMATION//
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    //SIGN IN BUTTON PUSHED//
    public void signInBtnPushed(ActionEvent event) throws SQLException, IOException {
        String sql = "SELECT * FROM user WHERE userName = ? AND userPassword = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, usernameLogin.getText());
        ps.setString(2, passwordLogin.getText());
        ResultSet rs = ps.executeQuery();
        if(usernameLogin.getText() == "" || passwordLogin.getText() == "") {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("One or more fields are empty");
            alert.showAndWait();
        } else if (!rs.next()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Invalid Username or Password");
            alert.showAndWait();
        } else {
            //GET USERNAME TO PARSE USERID FROM//
            String username = usernameLogin.getText();
            getUserId(username);
            //SET SCENE//
            Parent parent = FXMLLoader.load(getClass().getResource("/com/example/retrogarden/main-games-consoles-view.fxml"));
            Scene scene = new Scene(parent);
            //THIS LINE GETS THE STAGE INFORMATION//
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        }
    }


    //GET USER ID FROM USERNAME//
    public void getUserId(String username) throws SQLException {
        String sql = "SELECT userID FROM user WHERE userName = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, usernameLogin.getText());
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            userID = rs.getInt(1);
        }
    }


}
