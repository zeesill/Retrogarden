package com.example.retrogarden.Controllers;

import com.example.retrogarden.Database.JDBC;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ForgotPasswordController {

    @FXML private TextField forgotUsername;
    @FXML private TextArea forgotPasswordHint;


    public void backBtnPushed(ActionEvent event) throws IOException {
        //SWITCH SCENE//
        Parent parent = FXMLLoader.load(getClass().getResource("/com/example/retrogarden/user-login-view.fxml"));
        Scene scene = new Scene(parent);
        //THIS LINE GETS THE STAGE INFORMATION//
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    public void getHintBtnPushed(ActionEvent event) throws SQLException {
        String sql = "SELECT userPasswordHint FROM user WHERE userName = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, forgotUsername.getText());
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String passwordHint = rs.getString("userPasswordHint");
            forgotPasswordHint.setVisible(true);
            forgotPasswordHint.setText(passwordHint);
        }

    }
}
