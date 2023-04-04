package com.example.retrogarden.Controllers;

import com.example.retrogarden.Database.JDBC;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ValueController implements Initializable {

    @FXML private RadioButton allRadio;
    @FXML private RadioButton consolesRadio;
    @FXML private RadioButton gamesRadio;
    @FXML private Label valueLabel;

    public void backBtnPushed(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/com/example/retrogarden/main-games-consoles-view.fxml"));
        Scene scene = new Scene(parent);
        //THIS LINE GETS THE STAGE INFORMATION//
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }


    //RADIO BUTTON//
    public void radioBtnPushed(ActionEvent event) throws SQLException {

        double gameSum = 0;
        String sql = "";
        if(allRadio.isSelected()) {
            double sumOfGames = getSumOfGames();
            double sumOfConsoles = getSumOfConsoles();
            double sumOfAll = sumOfGames + sumOfConsoles;
            valueLabel.setText("Your Retrogarden is worth $" + sumOfAll + "!");

        } else if (gamesRadio.isSelected()) {
            sql = "SELECT SUM(gamePrice) FROM retrogardendb.game WHERE gameUserID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, LoginController.userID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                gameSum = rs.getDouble(1);
            }
            valueLabel.setText("Your games are worth $" + gameSum + "!");
        } else if (consolesRadio.isSelected()) {
            sql = "SELECT SUM(consolePrice) FROM retrogardendb.console WHERE consoleUserID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, LoginController.userID);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                gameSum = rs.getDouble(1);
            }
            valueLabel.setText("Your consoles are worth $" + gameSum + "!");
        }
    }

    //GET SUM OF BOTH GAMES AND CONSOLES FOR USER//

    public double getSumOfGames() throws SQLException {
        double sum = 0;
        String sql = "SELECT SUM(gamePrice) FROM retrogardendb.game WHERE gameUserID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setDouble(1, LoginController.userID);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            sum += rs.getDouble(1);
        }
        return sum;
    }
    public double getSumOfConsoles() throws SQLException {
        double sum = 0;
        String sql = "SELECT SUM(consolePrice) FROM retrogardendb.console WHERE consoleUserID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setDouble(1, LoginController.userID);
        ResultSet rs = ps.executeQuery();
        if(rs.next()) {
            sum = rs.getDouble(1);
        }
        return sum;
    }







































    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ToggleGroup radioBtns = new ToggleGroup();
        allRadio.setToggleGroup(radioBtns);
        gamesRadio.setToggleGroup(radioBtns);
        consolesRadio.setToggleGroup(radioBtns);
    }
}
