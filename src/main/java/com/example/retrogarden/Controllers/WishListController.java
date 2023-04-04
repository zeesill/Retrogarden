package com.example.retrogarden.Controllers;

import com.example.retrogarden.Database.JDBC;
import com.example.retrogarden.Models.Wish;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class WishListController implements Initializable {

    @FXML private TableView<Wish> wishTableView;
    @FXML private TableColumn<Wish, String> wishNameCol;
    @FXML private TableColumn<Wish, String> wishNoteCol;

    ObservableList<Wish> wishObservableList;

    Wish wish;


    //DELETE WISH BUTTON PUSHED//
    public void deleteWishBtnPushed(ActionEvent event) throws SQLException {

        wish = wishTableView.getSelectionModel().getSelectedItem();

        if(wishTableView.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Must select a wish to delete");
            alert.showAndWait();
        } else {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Are you sure you want to delete this wish?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                String sql = "DELETE FROM wishlist WHERE wishlistID = ?";
                PreparedStatement ps = JDBC.connection.prepareStatement(sql);
                ps.setInt(1, wish.getWishlistID());
                ps.executeUpdate();
                wishObservableList.remove(wishTableView.getSelectionModel().getSelectedItem());
            }
        }
    }



    //BACK BUTTON PUSHED//
    public void backBtnPushed(ActionEvent event) throws IOException {
        //SWITCH SCENE//
        Parent parent = FXMLLoader.load(getClass().getResource("/com/example/retrogarden/main-games-consoles-view.fxml"));
        Scene scene = new Scene(parent);
        //THIS LINE GETS THE STAGE INFORMATION//
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Label placeHolder = new Label("Add a wish!");
        wishTableView.setPlaceholder(placeHolder);

        wishNameCol.setCellValueFactory(new PropertyValueFactory<>("wishListName"));
        wishNoteCol.setCellValueFactory(new PropertyValueFactory<>("wishListNote"));
        wishObservableList = FXCollections.observableArrayList();
        loadDataFromWishDb();
    }

    private void loadDataFromWishDb() {
        try {
            String sql = "SELECT * FROM wishlist WHERE wishListUserID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, LoginController.userID);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                wishObservableList.add(new Wish(rs.getInt(1),rs.getString("wishlistName"), rs.getString("wishlistNote"), rs.getInt("wishlistUserID")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        wishTableView.setItems(wishObservableList);
    }

    //ADD WISH BUTTON PUSHED//
    public void addWishBtnPushed(ActionEvent event) throws IOException {
        //SWITCH SCENE//
        Parent parent = FXMLLoader.load(getClass().getResource("/com/example/retrogarden/add-wish-view.fxml"));
        Scene scene = new Scene(parent);
        //THIS LINE GETS THE STAGE INFORMATION//
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    //UPDATE WISH BUTTON PUSHED//
    public void updateWishBtnPushed(ActionEvent event) throws IOException {

        Wish selectedItem = wishTableView.getSelectionModel().getSelectedItem();

        if (selectedItem == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select a wish you... wish... to update!");
            alert.showAndWait();
        } else {
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(UpdateWishController.class.getResource("/com/example/retrogarden/update-wish-view.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            UpdateWishController controller = loader.getController();
            controller.getSelectedWish(selectedItem);
            stage.show();
        }
    }


}
