package com.example.retrogarden.Models;

public class Wish {


    private int wishlistID;
    private String wishListName;
    private String wishListNote;
    private int wishListUserID;


    public Wish(String wishListName, String wishListNote, int wishListUserID) {
        this.wishListName = wishListName;
        this.wishListNote = wishListNote;
        this.wishListUserID = wishListUserID;
    }

    public Wish(int wishlistID, String wishListName, String wishListNote, int wishListUserID) {
        this.wishlistID = wishlistID;
        this.wishListName = wishListName;
        this.wishListNote = wishListNote;
        this.wishListUserID = wishListUserID;
    }

    public int getWishlistID() {
        return wishlistID;
    }

    public void setWishlistID(int wishlistID) {
        this.wishlistID = wishlistID;
    }

    public String getWishListName() {
        return wishListName;
    }

    public void setWishListName(String wishListName) {
        this.wishListName = wishListName;
    }

    public String getWishListNote() {
        return wishListNote;
    }

    public void setWishListNote(String wishListNote) {
        this.wishListNote = wishListNote;
    }

    public int getWishListUserID() {
        return wishListUserID;
    }

    public void setWishListUserID(int wishListUserID) {
        this.wishListUserID = wishListUserID;
    }
}
