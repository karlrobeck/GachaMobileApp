package com.example.gachamobileapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import java.io.File
import java.io.BufferedReader

val DATABASE_NAME ="MyDB"
val UsersTable="User"
val Name = "NAME"
val Age = "AGE"
val id = "ID"

class Database(context:Context,factory:SQLiteDatabase.CursorFactory?) : SQLiteOpenHelper(context, "GachaSystem", factory, 1) {
    private val mContext: Context = context
    override fun onCreate(db: SQLiteDatabase?) {
        val bufferedReader: BufferedReader = File("./SQL/up.sql").bufferedReader()
        val upSQL = bufferedReader.use { it.readText() }
        db?.execSQL(upSQL)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val bufferedReader: BufferedReader = File("./SQL/down.sql").bufferedReader()
        val upSQL = bufferedReader.use { it.readText() }
        db?.execSQL(upSQL)
    }

    fun insertData(user: User) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(Name, user.name)
        cv.put(Age, user.age)
        initializeUser(db)
        val result = db.insert(UsersTable, null, cv)
        if (result == -1.toLong()) {
            Toast.makeText(mContext, "Failed", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(mContext, "Success", Toast.LENGTH_SHORT).show()
        }
    }

    // helper functions

    // core functions
    fun initializeUser(db: SQLiteDatabase?) {
        db?.execSQL("INSERT INTO User(Name, Money, Image) VALUES ('?', 500, '?')")
    }

    fun addCard(db: SQLiteDatabase?, name: String, description: String, tier: Int) {
        val values = ContentValues().apply {
            put("Name", name)
            put("Description", description)
            put("Tier", tier)
        }
        db?.insert("Card", null, values)
    }

    fun addAchievement(
        db: SQLiteDatabase?,
        title: String,
        message: String,
        cardId: Int?,
        types: Int?
    ) {
        val values = ContentValues().apply {
            put("Title", title)
            put("Message", message)
            put("CardID", cardId)
            put("Types", types)
        }
        db?.insert("Achievements", null, values)
    }

    fun getUserInfo(db: SQLiteDatabase?): Cursor? {
        return db?.rawQuery("SELECT * FROM User LIMIT 1;", null)
    }

    fun getCardByName(db: SQLiteDatabase?, name: String): Cursor? {
        return db?.rawQuery("SELECT * FROM Card WHERE NAME = ?", arrayOf(name))
    }

    fun getCardByID(db: SQLiteDatabase?, id: Int): Cursor? {
        return db?.rawQuery("SELECT * FROM Card WHERE ID = ?;", arrayOf(id.toString()))
    }

    fun getCardByTier(db: SQLiteDatabase?, tier: Int): Cursor? {
        return db?.rawQuery("SELECT * FROM Card WHERE TIER = ?", arrayOf(tier.toString()))
    }

    fun getCardByType(db: SQLiteDatabase?, cardType: String): Cursor? {
        return db?.rawQuery(
            "SELECT * FROM Card WHERE ID IN (SELECT CARD_ID FROM CardType WHERE TYPES = ?)",
            arrayOf(cardType)
        )
    }

    fun getAchievementByName(db: SQLiteDatabase?, name: String): Cursor? {
        return db?.rawQuery("SELECT * FROM Achievements WHERE Title = ?", arrayOf(name))
    }

    fun getAchievementBySystem(db: SQLiteDatabase?, isSystem: Boolean): Cursor? {
        val systemValue = if (isSystem) 1 else 0
        return db?.rawQuery("SELECT * FROM Achievements WHERE System = ?", arrayOf(systemValue.toString()))
    }

    fun getAchievementByID(db: SQLiteDatabase?, id: Int): Cursor? {
        return db?.rawQuery("SELECT * FROM Achievements WHERE CardID = ?", arrayOf(id.toString()))
    }

    fun getAchievementByTypes(db: SQLiteDatabase?, cardType: String): Cursor? {
        return db?.rawQuery(
            "SELECT * FROM Achievements WHERE Types IN (SELECT CARD_ID FROM CardType WHERE TYPES = ?)",
            arrayOf(cardType)
        )
    }

    fun getAchievementByOwner(db: SQLiteDatabase?, owner: String?, isNull: Boolean): Cursor? {
        val nullValue = if (isNull) "NULL" else "NOT NULL"
        return db?.rawQuery("SELECT * FROM Achievements WHERE Owner IS $nullValue", arrayOf(owner))
    }
}