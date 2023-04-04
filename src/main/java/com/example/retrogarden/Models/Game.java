package com.example.retrogarden.Models;

import java.time.LocalDate;
import java.util.Date;

public class Game {
    private int gameID;
    private String gameName;
    private String gameCondition;
    private LocalDate gameReleaseYear;
    private Double gamePrice;
    private int gameUserID;

    public Game(int gameID, String gameName, String gameCondition, LocalDate gameReleaseYear, Double gamePrice, int gameUserID) {
        this.gameID = gameID;
        this.gameName = gameName;
        this.gameCondition = gameCondition;
        this.gameReleaseYear = gameReleaseYear;
        this.gamePrice = gamePrice;
        this.gameUserID = gameUserID;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public String getGameCondition() {
        return gameCondition;
    }

    public void setGameCondition(String gameCondition) {
        this.gameCondition = gameCondition;
    }

    public LocalDate getGameReleaseYear() {
        return gameReleaseYear;
    }

    public void setGameReleaseYear(LocalDate gameReleaseYear) {
        this.gameReleaseYear = gameReleaseYear;
    }

    public Double getGamePrice() {
        return gamePrice;
    }

    public void setGamePrice(Double gamePrice) {
        this.gamePrice = gamePrice;
    }


    public int getGameUserID() {
        return gameUserID;
    }

    public void setGameUserID(int gameUserID) {
        this.gameUserID = gameUserID;
    }
}
