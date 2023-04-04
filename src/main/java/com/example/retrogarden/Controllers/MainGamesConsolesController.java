package com.example.retrogarden.Controllers;

import com.example.retrogarden.Database.JDBC;
import com.example.retrogarden.Models.Console;
import com.example.retrogarden.Models.Game;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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

import javax.xml.transform.Result;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainGamesConsolesController implements Initializable {
    //CONSOLE TABLE VIEW//
    @FXML private TableView<Console> consoleTableView;

    //CONSOLE TABLE COLUMNS//
    @FXML private TableColumn<Console, String> nameOfConsoleCol;
    @FXML private TableColumn<Console, String> consoleConditionCol;
    @FXML private TableColumn<Console, Date> consoleReleaseYearCol;
    @FXML private TableColumn<Console, Double> consolePriceCol;


    //GAME TABLE COLUMNS//
    @FXML private TableView<Game> gameTableView;
    @FXML private TableColumn<Game, String> nameOfGameCol;
    @FXML private TableColumn<Game, String> gameConditionCol;
    @FXML private TableColumn<Game, Date> gameReleaseDateCol;
    @FXML private TableColumn<Game, Double> gamePriceCol;


    //HOLD CONSOLE LIST FOR SPECIFIC USER//
    ObservableList<Console> consoleObservableList;

    //HOLD CONSOLE LIST FOR SPECIFIC USER//
    ObservableList<Game> gameObservableList;

    //SEARCH TEXTFIELD//
    @FXML private TextField consoleSearchField;
    @FXML private TextField gameSearchField;

    //SIGN OUT BUTTON PUSHED//
    public void signOutBtnPushed(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are you sure you want to sign out?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK) {
            //SWITCH SCENE//
            Parent parent = FXMLLoader.load(getClass().getResource("/com/example/retrogarden/user-login-view.fxml"));
            Scene scene = new Scene(parent);
            //THIS LINE GETS THE STAGE INFORMATION//
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        }
    }

    //ADD CONSOLE BUTTON PUSHED//
    public void addConsoleBtnPushed(ActionEvent event) throws IOException {
        //SWITCH SCENE//
        Parent parent = FXMLLoader.load(getClass().getResource("/com/example/retrogarden/add-console-view.fxml"));
        Scene scene = new Scene(parent);
        //THIS LINE GETS THE STAGE INFORMATION//
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    //UPDATE CONSOLE BUTTON PUSHED//
    public void updateConsoleBtnPushed(ActionEvent event) throws IOException {
        Console selectedConsole = consoleTableView.getSelectionModel().getSelectedItem();
        if (selectedConsole == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select a Console to Update");
            alert.showAndWait();
        } else {
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(UpdateConsoleController.class.getResource("/com/example/retrogarden/update-console-view.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            UpdateConsoleController controller = loader.getController();
            controller.getSelectedConsole(consoleTableView.getSelectionModel().getSelectedItem());
            stage.show();
        }
    }

    //DELETE CONSOLE BUTTON PUSHED//
    public void deleteConsoleBtnPushed(ActionEvent event) throws SQLException, IOException {
        Console console = consoleTableView.getSelectionModel().getSelectedItem();

        if (console == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select a Console to Delete");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Are you sure you want to delete this console?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                int consoleID = console.getConsoleID();
                String sql = "DELETE FROM console WHERE consoleID = ?";
                PreparedStatement ps = JDBC.connection.prepareStatement(sql);
                ps.setString(1, String.valueOf(consoleID));
                ps.executeUpdate();
                Parent parent = FXMLLoader.load(getClass().getResource("/com/example/retrogarden/main-games-consoles-view.fxml"));
                Scene scene = new Scene(parent);
                //THIS LINE GETS THE STAGE INFORMATION//
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();

                Alert timedAlert = new Alert(Alert.AlertType.INFORMATION);
                timedAlert.setContentText("Console has been deleted!");
                timedAlert.showAndWait();
            }
        }
    }

    //CONSOLE SEARCH FIELD//
    @FXML public void consoleSearch(){

        }


    //ADD GAME BUTTON PUSHED//
    public void addGameBtnPushed(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/com/example/retrogarden/add-game-view.fxml"));
        Scene scene = new Scene(parent);
        //THIS LINE GETS THE STAGE INFORMATION//
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }


    //DELETE GAME BUTTON PUSHED//
    public void deleteGameBtnPushed(ActionEvent event) throws SQLException, IOException {
        Game game = gameTableView.getSelectionModel().getSelectedItem();

        if (game == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select a Game to Delete");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Are you sure you want to delete this game?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                int gameID = game.getGameID();
                String sql = "DELETE FROM game WHERE gameID = ?";
                PreparedStatement ps = JDBC.connection.prepareStatement(sql);
                ps.setString(1, String.valueOf(gameID));
                ps.executeUpdate();

                //REMOVE GAME FROM TABLEVIEW//
                gameObservableList.remove(game);

                Alert timedAlert = new Alert(Alert.AlertType.INFORMATION);
                timedAlert.setContentText("Game has been deleted!");
                timedAlert.showAndWait();
            }
        }
    }

    //UPDATE GAME BUTTON PUSHED//
    public void updateGameBtnPushed(ActionEvent event) throws IOException {
        Game selectedGame = gameTableView.getSelectionModel().getSelectedItem();
        if (selectedGame == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select a Game to Update");
            alert.showAndWait();
        } else {
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(UpdateGameController.class.getResource("/com/example/retrogarden/update-game-view.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            UpdateGameController controller = loader.getController();
            controller.getSelectedGame(gameTableView.getSelectionModel().getSelectedItem());
            stage.show();
        }
    }

    //VIEW VALUE BUTTON PUSHED//
    public void valueViewBtnPushed(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/com/example/retrogarden/value-view.fxml"));
        Scene scene = new Scene(parent);
        //THIS LINE GETS THE STAGE INFORMATION//
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    //WISHLIST BUTTON PUSHED//
    public void wishListBtnPushed(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/com/example/retrogarden/wish-list-view.fxml"));
        Scene scene = new Scene(parent);
        //THIS LINE GETS THE STAGE INFORMATION//
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }



    @FXML public void searchConsolesEnter(){


    }




























































    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //SET CELLS FOR CONSOLE TABLE//
        nameOfConsoleCol.setCellValueFactory(new PropertyValueFactory<>("consoleName"));
        consoleConditionCol.setCellValueFactory(new PropertyValueFactory<>("consoleCondition"));
        consoleReleaseYearCol.setCellValueFactory(new PropertyValueFactory<>("consoleReleaseYear"));
        consolePriceCol.setCellValueFactory(new PropertyValueFactory<>("consolePrice"));

        //SET CELLS FOR GAME TABLE//
        nameOfGameCol.setCellValueFactory(new PropertyValueFactory<>("gameName"));
        gameConditionCol.setCellValueFactory(new PropertyValueFactory<>("gameCondition"));
        gameReleaseDateCol.setCellValueFactory(new PropertyValueFactory<>("gameReleaseYear"));
        gamePriceCol.setCellValueFactory(new PropertyValueFactory<>("gamePrice"));

        //CONSOLE LIST//
        consoleObservableList = FXCollections.observableArrayList();
        loadDataFromConsoleDB();

        //GAME LIST//
        gameObservableList = FXCollections.observableArrayList();
        loadDataFromGameDB();

        //SEARCH FOR CONSOLE//
        FilteredList<Console> filteredList = new FilteredList<>(consoleObservableList, b -> true);
        consoleSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(console -> {
                if(newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if(console.getConsoleName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (console.getConsoleCondition().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else {
                    return false;
                }
            });
        });

        SortedList<Console> sortedData = new SortedList<>(filteredList);
        sortedData.comparatorProperty().bind(consoleTableView.comparatorProperty());
        consoleTableView.setItems(sortedData);


        //SEARCH FOR GAME//
        FilteredList<Game> filteredList2 = new FilteredList<>(gameObservableList, b -> true);
        gameSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList2.setPredicate(game -> {
                if(newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if(game.getGameName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (game.getGameCondition().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else {
                    return false;
                }
            });
        });

        SortedList<Game> sortedData2 = new SortedList<>(filteredList2);
        sortedData2.comparatorProperty().bind(gameTableView.comparatorProperty());
        gameTableView.setItems(sortedData2);
    }

    //LOAD DATA FROM CONSOLE DATABASE//
    private void loadDataFromConsoleDB() {
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement("SELECT * FROM console WHERE consoleUserID = ?");
            //NEED TO GET THE USER ID OF THE CURRENT USER SIGNED IN//
            ps.setInt(1, LoginController.userID);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                consoleObservableList.add(new Console(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDate(4).toLocalDate(), rs.getDouble(5), rs.getInt(6)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        consoleTableView.setItems(consoleObservableList);
    }

    private void loadDataFromGameDB() {
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement("SELECT * FROM game WHERE gameUserId = ?");
            ps.setInt(1, LoginController.userID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                gameObservableList.add(new Game(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDate(4).toLocalDate(), rs.getDouble(5), rs.getInt(6)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        gameTableView.setItems(gameObservableList);
    }
}
