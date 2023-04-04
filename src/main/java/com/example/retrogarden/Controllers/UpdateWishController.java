package com.example.retrogarden.Controllers;

import com.example.retrogarden.Database.JDBC;
import com.example.retrogarden.Models.Wish;
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

public class UpdateWishController {

    @FXML private TextField updateWishNameField;

    @FXML private TextArea updateWishNoteField;

    int wishID;


    //BACK BUTTON PUSHED//
    public void backBtnPushed(ActionEvent event) throws IOException {
        //SWITCH SCENE//
        Parent parent = FXMLLoader.load(getClass().getResource("/com/example/retrogarden/wish-list-view.fxml"));
        Scene scene = new Scene(parent);
        //THIS LINE GETS THE STAGE INFORMATION//
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    //GET SELECTED ITEM
    public void getSelectedWish(Wish wish) {
        wishID = wish.getWishlistID();
        updateWishNameField.setText(wish.getWishListName());
        updateWishNoteField.setText(wish.getWishListNote());
    }

    //UPDATE WISH BUTTON PUSHED//
    public void updateWishBtnPushed(ActionEvent event) throws SQLException, IOException {
        if(updateWishNameField.getText().equals("") || updateWishNoteField.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("One or more fields are empty!");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Your wish has been updated!");
            alert.showAndWait();

            String sql = "UPDATE wishlist SET wishlistName = ?, wishlistNote = ? WHERE wishlistID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, updateWishNameField.getText());
            ps.setString(2, updateWishNoteField.getText());
            ps.setInt(3, wishID);
            ps.executeUpdate();

            //SWITCH SCENE//
            Parent parent = FXMLLoader.load(getClass().getResource("/com/example/retrogarden/wish-list-view.fxml"));
            Scene scene = new Scene(parent);
            //THIS LINE GETS THE STAGE INFORMATION//
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();



        }
    }

}
