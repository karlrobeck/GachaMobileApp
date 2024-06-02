package com.example.gachamobileapp

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class Database(context:Context,factory:SQLiteDatabase.CursorFactory?) : SQLiteOpenHelper(context, "GachaSystem", factory, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        TODO("Import .sql file to initialize the system.")
        // execute the query once loaded
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Implement Upgrade for SQLITE")
    }

    // helper functions

    // core functions
    fun initializeUser() {
        TODO("IMPLEMENT THIS SQL STATEMENT" +
                "INSERT INTO User(Name, Money, Image) VALUES ('?', 500, '?')")
    }

    fun addCard() {
        TODO(
            "IMPLEMENT THIS SQL STATEMENTS" +
                    "INSERT INTO Card(Name,Description,Tier) Values ('?', '?', 1)" +
                    "INSERT INTO CardType(Card_ID,Types) Values (?,'?')"
        )
    }

    fun addAchievement() {
        TODO("IMPLEMENT THIS SQL STATEMENT " +
                "INSERT INTO Achievements (Title,Message,CardID,Types) Values ('?', '?', ?, ?)")
    }

    fun getUserInfo() {
        TODO("IMPLEMENT THIS SQL STATEMENT " +
                "SELECT * FROM User LIMIT 1;")
    }

    fun getCardByName() {
        TODO("IMPLEMENT THIS SQL STATEMENT " +
                "SELECT * FROM Card WHERE NAME = '?'")
    }

    fun getCardByID() {
        TODO("IMPLEMENT THIS SQL STATEMENT" +
                "SELECT * FROM Card WHERE ID = ?;")
    }

    fun getCardByTier() {
        TODO("IMPLEMENT THIS SQL STATEMENT" +
                "SELECT * FROM Card WHERE TIER = 1")
    }

    fun getCardByType() {
        TODO("IMPLEMENT THIS SQL STATEMENT" +
                "SELECT * FROM Card WHERE ID IN (SELECT CARD_ID FROM CardType WHERE TYPES = '?')")
    }

    fun getAchivementByName() {
       TODO("IMPLEMENT THIS SQL STATEMENT" +
               "SELECT * FROM Achievements WHERE Title = '?'")
    }

    fun getAchivementBySystem() {
        TODO("IMPLEMENT THIS SQL STATEMENT" +
                "SELECT * FROM Achievements WHERE System = ?")
    }

    fun getAchivementByID() {
        TODO("IMPLEMENT THIS SQL STATEMENT" +
                "SELECT * FROM Achievements WHERE CardID = ?")
    }

    fun getAchivementByTypes() {
        TODO("IMPLEMENT THIS SQL STATEMENT" +
                "SELECT * FROM Achievements WHERE Types IN (SELECT CARD_ID FROM CardType WHERE TYPES = '?')")
    }

    fun getAchivementByOwner() {
        // null or not null
        TODO("IMPLEMENT THIS SQL STATEMENT " +
                "SELECT * FROM Achievements WHERE Owner IS ?")
    }

}