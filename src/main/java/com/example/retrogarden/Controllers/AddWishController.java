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
import java.sql.SQLException;

public class AddWishController {


    @FXML private TextField wishNameField;
    @FXML private TextArea wishNoteField;


    //ADD WISH BUTTON PUSHED//
    public void addWishBtnPushed(ActionEvent event) throws SQLException, IOException {
        String wishName = wishNameField.getText();
        String wishNote = wishNoteField.getText();

        if(wishName.equals("") || wishNote.equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("One or more fields are empty!");
            alert.showAndWait();
        } else {
            String sql = "INSERT INTO wishlist (wishlistName, wishlistNote, wishlistUserID) VALUES (?,?,?)";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, wishName);
            ps.setString(2, wishNote);
            ps.setInt(3, LoginController.userID);
            ps.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Your wish is my command!");
            alert.showAndWait();

            Parent parent = FXMLLoader.load(getClass().getResource("/com/example/retrogarden/wish-list-view.fxml"));
            Scene scene = new Scene(parent);
            //THIS LINE GETS THE STAGE INFORMATION//
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        }
    }

    //BACK BUTTON PUSHED//
    public void backBtnPushed(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/com/example/retrogarden/wish-list-view.fxml"));
        Scene scene = new Scene(parent);
        //THIS LINE GETS THE STAGE INFORMATION//
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }


}
