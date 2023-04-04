package com.example.retrogarden.Models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.Date;

public class Console {
    private int consoleID;
    private String consoleName;
    private String consoleCondition;
    private LocalDate consoleReleaseYear;
    private double consolePrice;
    private int consoleUserID;





    public Console(int consoleID, String consoleName, String consoleCondition, LocalDate consoleReleaseYear, double consolePrice, int consoleUserID) {
        this.consoleID = consoleID;
        this.consoleName = consoleName;
        this.consoleCondition = consoleCondition;
        this.consoleReleaseYear = consoleReleaseYear;
        this.consolePrice = consolePrice;
        this.consoleUserID = consoleUserID;
    }



    public int getConsoleID() {
        return consoleID;
    }

    public void setConsoleID(int consoleID) {
        this.consoleID = consoleID;
    }

    public String getConsoleName() {
        return consoleName;
    }

    public void setConsoleName(String consoleName) {
        this.consoleName = consoleName;
    }

    public String getConsoleCondition() {
        return consoleCondition;
    }

    public void setConsoleCondition(String consoleCondition) {
        this.consoleCondition = consoleCondition;
    }

    public LocalDate getConsoleReleaseYear() {
        return consoleReleaseYear;
    }

    public void setConsoleReleaseYear(LocalDate consoleReleaseYear) {
        this.consoleReleaseYear = consoleReleaseYear;
    }

    public double getConsolePrice() {
        return consolePrice;
    }

    public void setConsolePrice(double consolePrice) {
        this.consolePrice = consolePrice;
    }

    public int getConsoleUserID() {
        return consoleUserID;
    }

    public void setConsoleUserID(int consoleUserID) {
        this.consoleUserID = consoleUserID;
    }
}
