package com.example.gachamobileapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import java.io.File

class Database(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    private val mContext: Context = context

    companion object {
        private const val DATABASE_NAME = "GachaSystem"
        private const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val upSQL = File("./SQL/up.sql").readText()
        db?.execSQL(upSQL)
        initializeUser(db)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val downSQL = File("./SQL/down.sql").readText()
        db?.execSQL(downSQL)
        onCreate(db)
    }

    private fun initializeUser(db: SQLiteDatabase?) {
        val defaultUser = UserDataClass("DefaultUser", 50000, "default_image.jpg")
        insertUser(defaultUser, db)
    }

    fun insertUser(user: UserDataClass, db: SQLiteDatabase? = writableDatabase) {
        val cv = ContentValues().apply {
            put("Name", user.name)
            put("Money", user.money)
            put("Image", user.image)
        }
        val result = db?.insert("User", null, cv) ?: -1
        if (result == -1L) {
            Toast.makeText(mContext, "Failed to insert user", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(mContext, "User inserted successfully", Toast.LENGTH_SHORT).show()
        }
    }

    fun insertCard(card: CardDataClass, db: SQLiteDatabase? = writableDatabase) {
        val cv = ContentValues().apply {
            put("Owner", card.owner)
            put("Name", card.name)
            put("Description", card.description)
            put("Tier", card.tier)
        }
        val result = db?.insert("Card", null, cv) ?: -1
        if (result == -1L) {
            Toast.makeText(mContext, "Failed to insert card", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(mContext, "Card inserted successfully", Toast.LENGTH_SHORT).show()
        }
    }

    fun insertCardType(cardType: CardTypeDataClass, db: SQLiteDatabase? = writableDatabase) {
        val cv = ContentValues().apply {
            put("CardID", cardType.cardId)
            put("Types", cardType.type)
        }
        db?.insert("CardType", null, cv)
    }

    fun insertAchievement(achievement: AchievementDataClass, db: SQLiteDatabase? = writableDatabase) {
        val cv = ContentValues().apply {
            put("Title", achievement.title)
            put("Message", achievement.message)
            put("System", if (achievement.system) 1 else 0)
            put("CardID", achievement.cardId)
            put("Types", achievement.type)
            put("Owner", achievement.owner)
        }
        db?.insert("Achievements", null, cv)
    }

    fun getUserInfo(db: SQLiteDatabase? = readableDatabase): UserDataClass? {
        val cursor = db?.rawQuery("SELECT * FROM User LIMIT 1", null)
        return cursor?.use {
            if (it.moveToFirst()) {
                UserDataClass(
                    it.getString(it.getColumnIndexOrThrow("Name")),
                    it.getLong(it.getColumnIndexOrThrow("Money")),
                    it.getString(it.getColumnIndexOrThrow("Image"))
                )
            } else null
        }
    }

    fun getCardByName(name: String, db: SQLiteDatabase? = readableDatabase): CardDataClass? {
        val cursor = db?.rawQuery("SELECT * FROM Card WHERE Name = ?", arrayOf(name))
        return cursor?.use {
            if (it.moveToFirst()) {
                CardDataClass(
                    it.getString(it.getColumnIndexOrThrow("Owner")),
                    it.getInt(it.getColumnIndexOrThrow("ID")),
                    it.getString(it.getColumnIndexOrThrow("Name")),
                    it.getString(it.getColumnIndexOrThrow("Description")),
                    it.getInt(it.getColumnIndexOrThrow("Tier"))
                )
            } else null
        }
    }

    fun getCardByID(id: Int, db: SQLiteDatabase? = readableDatabase): CardDataClass? {
        val cursor = db?.rawQuery("SELECT * FROM Card WHERE ID = ?", arrayOf(id.toString()))
        return cursor?.use {
            if (it.moveToFirst()) {
                CardDataClass(
                    it.getString(it.getColumnIndexOrThrow("Owner")),
                    it.getInt(it.getColumnIndexOrThrow("ID")),
                    it.getString(it.getColumnIndexOrThrow("Name")),
                    it.getString(it.getColumnIndexOrThrow("Description")),
                    it.getInt(it.getColumnIndexOrThrow("Tier"))
                )
            } else null
        }
    }

    fun getCardsByTier(tier: Int, db: SQLiteDatabase? = readableDatabase): List<CardDataClass> {
        val cards = mutableListOf<CardDataClass>()
        val cursor = db?.rawQuery("SELECT * FROM Card WHERE Tier = ?", arrayOf(tier.toString()))
        cursor?.use {
            while (it.moveToNext()) {
                cards.add(CardDataClass(
                    it.getString(it.getColumnIndexOrThrow("Owner")),
                    it.getInt(it.getColumnIndexOrThrow("ID")),
                    it.getString(it.getColumnIndexOrThrow("Name")),
                    it.getString(it.getColumnIndexOrThrow("Description")),
                    it.getInt(it.getColumnIndexOrThrow("Tier"))
                ))
            }
        }
        return cards
    }

    fun getCardsByType(cardType: String, db: SQLiteDatabase? = readableDatabase): List<CardDataClass> {
        val cards = mutableListOf<CardDataClass>()
        val cursor = db?.rawQuery(
            "SELECT c.* FROM Card c JOIN CardType ct ON c.ID = ct.CardID WHERE ct.Types = ?",
            arrayOf(cardType)
        )
        cursor?.use {
            while (it.moveToNext()) {
                cards.add(CardDataClass(
                    it.getString(it.getColumnIndexOrThrow("Owner")),
                    it.getInt(it.getColumnIndexOrThrow("ID")),
                    it.getString(it.getColumnIndexOrThrow("Name")),
                    it.getString(it.getColumnIndexOrThrow("Description")),
                    it.getInt(it.getColumnIndexOrThrow("Tier"))
                ))
            }
        }
        return cards
    }

    fun getAchievementByName(name: String, db: SQLiteDatabase? = readableDatabase): AchievementDataClass? {
        val cursor = db?.rawQuery("SELECT * FROM Achievements WHERE Title = ?", arrayOf(name))
        return cursor?.use {
            if (it.moveToFirst()) {
                AchievementDataClass(
                    it.getString(it.getColumnIndexOrThrow("Title")),
                    it.getString(it.getColumnIndexOrThrow("Message")),
                    it.getInt(it.getColumnIndexOrThrow("System")) == 1,
                    it.getInt(it.getColumnIndexOrThrow("CardID")),
                    it.getInt(it.getColumnIndexOrThrow("Types")),
                    it.getString(it.getColumnIndexOrThrow("Owner"))
                )
            } else null
        }
    }

    fun getAchievementsBySystem(isSystem: Boolean, db: SQLiteDatabase? = readableDatabase): List<AchievementDataClass> {
        val achievements = mutableListOf<AchievementDataClass>()
        val systemValue = if (isSystem) 1 else 0
        val cursor = db?.rawQuery("SELECT * FROM Achievements WHERE System = ?", arrayOf(systemValue.toString()))
        cursor?.use {
            while (it.moveToNext()) {
                achievements.add(AchievementDataClass(
                    it.getString(it.getColumnIndexOrThrow("Title")),
                    it.getString(it.getColumnIndexOrThrow("Message")),
                    it.getInt(it.getColumnIndexOrThrow("System")) == 1,
                    it.getInt(it.getColumnIndexOrThrow("CardID")),
                    it.getInt(it.getColumnIndexOrThrow("Types")),
                    it.getString(it.getColumnIndexOrThrow("Owner"))
                ))
            }
        }
        return achievements
    }

    fun getAchievementByCardID(cardId: Int, db: SQLiteDatabase? = readableDatabase): AchievementDataClass? {
        val cursor = db?.rawQuery("SELECT * FROM Achievements WHERE CardID = ?", arrayOf(cardId.toString()))
        return cursor?.use {
            if (it.moveToFirst()) {
                AchievementDataClass(
                    it.getString(it.getColumnIndexOrThrow("Title")),
                    it.getString(it.getColumnIndexOrThrow("Message")),
                    it.getInt(it.getColumnIndexOrThrow("System")) == 1,
                    it.getInt(it.getColumnIndexOrThrow("CardID")),
                    it.getInt(it.getColumnIndexOrThrow("Types")),
                    it.getString(it.getColumnIndexOrThrow("Owner"))
                )
            } else null
        }
    }

    fun getAchievementsByType(cardType: String, db: SQLiteDatabase? = readableDatabase): List<AchievementDataClass> {
        val achievements = mutableListOf<AchievementDataClass>()
        val cursor = db?.rawQuery(
            "SELECT a.* FROM Achievements a JOIN CardType ct ON a.Types = ct.ID WHERE ct.Types = ?",
            arrayOf(cardType)
        )
        cursor?.use {
            while (it.moveToNext()) {
                achievements.add(AchievementDataClass(
                    it.getString(it.getColumnIndexOrThrow("Title")),
                    it.getString(it.getColumnIndexOrThrow("Message")),
                    it.getInt(it.getColumnIndexOrThrow("System")) == 1,
                    it.getInt(it.getColumnIndexOrThrow("CardID")),
                    it.getInt(it.getColumnIndexOrThrow("Types")),
                    it.getString(it.getColumnIndexOrThrow("Owner"))
                ))
            }
        }
        return achievements
    }

    fun getAchievementsByOwner(owner: String?, isNull: Boolean, db: SQLiteDatabase? = readableDatabase): List<AchievementDataClass> {
        val achievements = mutableListOf<AchievementDataClass>()
        val query = if (isNull) {
            "SELECT * FROM Achievements WHERE Owner IS NULL"
        } else {
            "SELECT * FROM Achievements WHERE Owner = ?"
        }
        val cursor = if (isNull) {
            db?.rawQuery(query, null)
        } else {
            db?.rawQuery(query, arrayOf(owner))
        }
        cursor?.use {
            while (it.moveToNext()) {
                achievements.add(AchievementDataClass(
                    it.getString(it.getColumnIndexOrThrow("Title")),
                    it.getString(it.getColumnIndexOrThrow("Message")),
                    it.getInt(it.getColumnIndexOrThrow("System")) == 1,
                    it.getInt(it.getColumnIndexOrThrow("CardID")),
                    it.getInt(it.getColumnIndexOrThrow("Types")),
                    it.getString(it.getColumnIndexOrThrow("Owner"))
                ))
            }
        }
        return achievements
    }

    override fun close() {
        super.close()
    }







    /*fun insertData(user: User) {
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
     */
}