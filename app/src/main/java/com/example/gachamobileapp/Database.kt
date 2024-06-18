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
        db ?: return
        val cv = ContentValues().apply {
            put("Name", user.name)
            put("Money", user.money)
            put("Image", user.image)
        }
        val result = db.insert("User", null, cv)
        if (result == -1L) {
            Toast.makeText(mContext, "Failed to insert user", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(mContext, "User inserted successfully", Toast.LENGTH_SHORT).show()
        }
    }


    fun updateUser(user: UserDataClass, db: SQLiteDatabase? = null): Boolean {
        db ?: return false

            val contentValues = ContentValues().apply {
                put("Money", user.money)
                put("Image", user.image)
            }
            val whereClause = "Name = ?"
            val whereArgs = arrayOf(user.name)
            val rowsAffected = db.update("User", contentValues, whereClause, whereArgs)
            return rowsAffected > 0
    }


    fun deleteUser(user: UserDataClass): Boolean {
            val db = writableDatabase ?: return false
            val whereClause = "Name = ?"
            val whereArgs = arrayOf(user.name)
            val rowsDeleted = db.delete("User", whereClause, whereArgs)
            return rowsDeleted > 0
    }


    fun insertCard(card: CardDataClass, db: SQLiteDatabase? = writableDatabase) {
        db ?: return
        val cv = ContentValues().apply {
            put("Owner", card.owner)
            put("Name", card.name)
            put("Description", card.description)
            put("Tier", card.tier)
        }
        val result = db.insert("Card", null, cv)
        if (result == -1L) {
            Toast.makeText(mContext, "Failed to insert card", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(mContext, "Card inserted successfully", Toast.LENGTH_SHORT).show()
        }
    }

    fun updateCard(card: CardDataClass, db: SQLiteDatabase? = writableDatabase) {
        db ?: return
        val cv = ContentValues().apply {
            put("Owner", card.owner)
            put("Description", card.description)
            put("Tier", card.tier)
        }
        val whereClause = "Name = ?"
        val whereArgs = arrayOf(card.name)
        val rowsUpdated = db.update("Card", cv, whereClause, whereArgs)
        if (rowsUpdated > 0) {
            Toast.makeText(mContext, "Card updated successfully", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(mContext, "Failed to update card", Toast.LENGTH_SHORT).show()
        }
    }


    fun deleteCard(card: CardDataClass) {
        val db = writableDatabase ?: return
        val whereClause = "Name = ?"
        val whereArgs = arrayOf(card.name)
        val rowsDeleted = db.delete("Card", whereClause, whereArgs)
        if (rowsDeleted > 0) {
            Toast.makeText(mContext, "Card deleted successfully", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(mContext, "Failed to delete card", Toast.LENGTH_SHORT).show()
        }
    }



    fun insertCardType(cardType: CardTypeDataClass, db: SQLiteDatabase? = writableDatabase) {
        db ?: return
        val cv = ContentValues().apply {
            put("CardID", cardType.cardId)
            put("Types", cardType.type)
        }
        db.insert("CardType", null, cv)
    }


    fun insertAchievement(achievement: AchievementDataClass, db: SQLiteDatabase? = writableDatabase) {
        db ?: return
        val cv = ContentValues().apply {
            put("Title", achievement.title)
            put("Message", achievement.message)
            put("System", if (achievement.system) 1 else 0)
            put("CardID", achievement.cardId)
            put("Types", achievement.type)
            put("Owner", achievement.owner)
        }
        db.insert("Achievements", null, cv)
    }


    fun updateAchievement(achievement: AchievementDataClass, db: SQLiteDatabase? = writableDatabase) {
        db ?: return
        val cv = ContentValues().apply {
            put("Title", achievement.title)
            put("Message", achievement.message)
            put("System", if (achievement.system) 1 else 0)
            put("CardID", achievement.cardId)
            put("Types", achievement.type)
            put("Owner", achievement.owner)
        }
        val whereClause = "Title = ?"
        val whereArgs = arrayOf(achievement.title)
        db.update("Achievements", cv, whereClause, whereArgs)
    }

    fun deleteAchievement(achievement: AchievementDataClass, db: SQLiteDatabase? = writableDatabase) {
        db ?: return
        val whereClause = "Title = ?"
        val whereArgs = arrayOf(achievement.title)
        db.delete("Achievements", whereClause, whereArgs)
    }


    fun getUserInfo(db: SQLiteDatabase? = readableDatabase): UserDataClass? {
        db ?: return null
        val cursor = db.rawQuery("SELECT * FROM User LIMIT 1", null)
        cursor.use {
            if (it.moveToFirst()) {
                return UserDataClass(
                    it.getString(it.getColumnIndexOrThrow("Name")),
                    it.getLong(it.getColumnIndexOrThrow("Money")),
                    it.getString(it.getColumnIndexOrThrow("Image"))
                )
            }
        }
        return null
    }


    fun getCardByName(name: String, db: SQLiteDatabase? = readableDatabase): CardDataClass? {
        db ?: return null
        val cursor = db.rawQuery("SELECT * FROM Card WHERE Name = ?", arrayOf(name))
        cursor.use {
            if (it.moveToFirst()) {
                return CardDataClass(
                    it.getString(it.getColumnIndexOrThrow("Owner")),
                    it.getInt(it.getColumnIndexOrThrow("ID")),
                    it.getString(it.getColumnIndexOrThrow("Name")),
                    it.getString(it.getColumnIndexOrThrow("Description")),
                    it.getInt(it.getColumnIndexOrThrow("Tier"))
                )
            }
        }
        return null
    }


    fun getCardByID(id: Int, db: SQLiteDatabase? = readableDatabase): CardDataClass? {
        db ?: return null
        val cursor = db.rawQuery("SELECT * FROM Card WHERE ID = ?", arrayOf(id.toString()))
        cursor.use {
            if (it.moveToFirst()) {
                return CardDataClass(
                    it.getString(it.getColumnIndexOrThrow("Owner")),
                    it.getInt(it.getColumnIndexOrThrow("ID")),
                    it.getString(it.getColumnIndexOrThrow("Name")),
                    it.getString(it.getColumnIndexOrThrow("Description")),
                    it.getInt(it.getColumnIndexOrThrow("Tier"))
                )
            }
        }
        return null
    }


    fun getCardsByTier(tier: Int, db: SQLiteDatabase? = readableDatabase): List<CardDataClass> {
        val cards = mutableListOf<CardDataClass>()
        db?.rawQuery("SELECT * FROM Card WHERE Tier = ?", arrayOf(tier.toString()))?.use { cursor ->
            while (cursor.moveToNext()) {
                cards.add(
                    CardDataClass(
                        cursor.getString(cursor.getColumnIndexOrThrow("Owner")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("ID")),
                        cursor.getString(cursor.getColumnIndexOrThrow("Name")),
                        cursor.getString(cursor.getColumnIndexOrThrow("Description")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("Tier"))
                    )
                )
            }
        }
        return cards
    }



    fun getCardsByType(cardType: String, db: SQLiteDatabase? = readableDatabase): List<CardDataClass> {
        val cards = mutableListOf<CardDataClass>()
        db?.rawQuery(
            "SELECT c.* FROM Card c JOIN CardType ct ON c.ID = ct.CardID WHERE ct.Types = ?",
            arrayOf(cardType)
        )?.use { cursor ->
            while (cursor.moveToNext()) {
                cards.add(
                    CardDataClass(
                        cursor.getString(cursor.getColumnIndexOrThrow("Owner")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("ID")),
                        cursor.getString(cursor.getColumnIndexOrThrow("Name")),
                        cursor.getString(cursor.getColumnIndexOrThrow("Description")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("Tier"))
                    )
                )
            }
        }
        return cards
    }


    fun getAchievementByName(name: String, db: SQLiteDatabase? = readableDatabase): AchievementDataClass? {
        val cursor = db?.rawQuery("SELECT * FROM Achievements WHERE Title = ?", arrayOf(name))
        return cursor?.use { cursor ->
            if (cursor.moveToFirst()) {
                AchievementDataClass(
                    cursor.getString(cursor.getColumnIndexOrThrow("Title")),
                    cursor.getString(cursor.getColumnIndexOrThrow("Message")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("System")) == 1,
                    cursor.getInt(cursor.getColumnIndexOrThrow("CardID")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("Types")),
                    cursor.getString(cursor.getColumnIndexOrThrow("Owner"))
                )
            } else {
                null
            }
        }
    }


    fun getAchievementsBySystem(isSystem: Boolean, db: SQLiteDatabase? = readableDatabase): List<AchievementDataClass> {
        db ?: return emptyList()
        val systemValue = if (isSystem) 1 else 0
        val achievements = mutableListOf<AchievementDataClass>()

        db.rawQuery("SELECT * FROM Achievements WHERE System = ?", arrayOf(systemValue.toString()))?.use { cursor ->
            while (cursor.moveToNext()) {
                achievements.add(
                    AchievementDataClass(
                        cursor.getString(cursor.getColumnIndexOrThrow("Title")),
                        cursor.getString(cursor.getColumnIndexOrThrow("Message")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("System")) == 1,
                        cursor.getInt(cursor.getColumnIndexOrThrow("CardID")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("Types")),
                        cursor.getString(cursor.getColumnIndexOrThrow("Owner"))
                    )
                )
            }
        }
        return achievements
    }


    fun getAchievementByCardID(cardId: Int, db: SQLiteDatabase? = readableDatabase): AchievementDataClass? {
        db ?: return null
        val cursor = db.rawQuery("SELECT * FROM Achievements WHERE CardID = ?", arrayOf(cardId.toString()))
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
            } else {
                null
            }
        }
    }


    fun getAchievementsByType(cardType: String, db: SQLiteDatabase? = readableDatabase): List<AchievementDataClass> {
        db ?: return emptyList()
        val achievements = mutableListOf<AchievementDataClass>()
        val query = """SELECT a.* FROM Achievements a JOIN CardType ct ON a.Types = ct.ID WHERE ct.Types = ?""".trimIndent()
        db.rawQuery(query, arrayOf(cardType))?.use { cursor ->
            while (cursor.moveToNext()) {
                achievements.add(
                    AchievementDataClass(
                        cursor.getString(cursor.getColumnIndexOrThrow("Title")),
                        cursor.getString(cursor.getColumnIndexOrThrow("Message")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("System")) == 1,
                        cursor.getInt(cursor.getColumnIndexOrThrow("CardID")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("Types")),
                        cursor.getString(cursor.getColumnIndexOrThrow("Owner"))
                    )
                )
            }
        }
        return achievements
    }


    fun getAchievementsByOwner(owner: String?, isNull: Boolean, db: SQLiteDatabase? = readableDatabase): List<AchievementDataClass> {
        db ?: return emptyList()
        val achievements = mutableListOf<AchievementDataClass>()
        val query = if (isNull) {
            "SELECT * FROM Achievements WHERE Owner IS NULL"
        } else {
            "SELECT * FROM Achievements WHERE Owner = ?"
        }
        val cursor = if (isNull) {
            db.rawQuery(query, null)
        } else {
            db.rawQuery(query, arrayOf(owner))
        }
        cursor?.use { cursor ->
            while (cursor.moveToNext()) {
                achievements.add(
                    AchievementDataClass(
                        cursor.getString(cursor.getColumnIndexOrThrow("Title")),
                        cursor.getString(cursor.getColumnIndexOrThrow("Message")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("System")) == 1,
                        cursor.getInt(cursor.getColumnIndexOrThrow("CardID")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("Types")),
                        cursor.getString(cursor.getColumnIndexOrThrow("Owner"))
                    )
                )
            }
        }
        return achievements
    }

    fun insertData(db: SQLiteDatabase?) {
        db?.beginTransaction()
            val users = listOf(
                UserDataClass("Andrei", 50000, "andrei.jpg"),
                UserDataClass("Arrow", 50000, "arrow.jpg"),
                UserDataClass("Berkeley", 50000, "berkeley.jpg"),
                UserDataClass("Vien", 50000, "vien.jpg"),
                UserDataClass("Blase", 50000, "blase.jpg"),
                UserDataClass("Chi", 50000, "chi.jpg"),
                UserDataClass("Clark", 50000, "clark.jpg"),
                UserDataClass("Gio", 50000, "gio.jpg"),
                UserDataClass("Gregorio", 50000, "gregorio.jpg"),
                UserDataClass("Jerwin", 50000, "jerwin.jpg"),
                UserDataClass("Jude", 50000, "jude.jpg"),
                UserDataClass("Julie", 50000, "julie.jpg"),
                UserDataClass("Robeck", 50000, "robeck.jpg"),
                UserDataClass("Keren", 50000, "keren.jpg"),
                UserDataClass("Loise", 50000, "loise.jpg"),
                UserDataClass("Newton", 50000, "newton.jpg"),
                UserDataClass("Dexter", 50000, "dexter.jpg"),
                UserDataClass("Pot", 50000, "pot.jpg"),
                UserDataClass("Rap", 50000, "rap.jpg"),
                UserDataClass("Renz", 50000, "renz.jpg"),
                UserDataClass("Ymmyll", 50000, "ymmyll.jpg"),
                UserDataClass("Wensdee", 50000, "wensdee.jpg"),
                UserDataClass("Tendenilla", 50000, "tendenilla.jpg"),
                UserDataClass("Bilches", 50000, "bilches.jpg"),
                UserDataClass("Marlyn", 50000, "marlyn.jpg"),
                UserDataClass("Rodian", 50000, "rodian.jpg"),
                UserDataClass("Gene", 50000, "gene.jpg"),
                UserDataClass("Euzen", 50000, "euzen.jpg"),
                UserDataClass("Villaflor", 50000, "villaflor.jpg"),
                UserDataClass("Vivien", 50000, "vivien.jpg"),
                UserDataClass("Mhell", 50000, "mhell.jpg"),
                UserDataClass("Rebeca", 50000, "rebeca.jpg"),
                UserDataClass("Sean", 50000, "sean.jpg"),
                UserDataClass("Eleyn", 50000, "eleyn.jpg")
            )
            users.forEach { insertUser(it, db) }
            val cards = listOf(
                CardDataClass("Andrei", 0, "Andrie", "God of Beasts", 3),
                CardDataClass("Arrow", 0, "Archer", "The eye the see through everything", 2),
                CardDataClass("Berkeley", 0, "Caster", "The commander of programmers", 3),
                CardDataClass("Vien", 0, "Kuma", "Bear", 3),
                CardDataClass("Blase", 0, "Gecko", "I choose the bed", 1),
                CardDataClass("Chi", 0, "Engel", "It is what it is", 3),
                CardDataClass("Clark", 0, "CJ", "I will die for honor", 2),
                CardDataClass("Gio", 0, "King", "We in it boys", 1),
                CardDataClass("Gregorio", 0, "Greeno", "I am the one above all", 2),
                CardDataClass("Jerwin", 0, "Queen", "yass queen!", 1),
                CardDataClass("Jude", 0, "Jood", "this dood", 2),
                CardDataClass("Julie", 0, "Bagger", "HE GOIN", 1),
                CardDataClass("Robeck", 0, "Python", "The darkhorse of programmers", 3),
                CardDataClass("Keren", 0, "Pony", "The purity of programmers", 2),
                CardDataClass("Loise", 0, "Eep", "Why this dude even here", 1),
                CardDataClass("Newton", 0, "Hegown", "BAG HER!", 1),
                CardDataClass("Dexter", 0, "Maxy", "Maxy got that waxy", 2),
                CardDataClass("Pot", 0, "Cholo", "The leader of the horsemen", 2),
                CardDataClass("Rap", 0, "Ist", "The badger rangers' investor", 1),
                CardDataClass("Renz", 0, "TBL Tennis", "The Jesus Christ of Pingpong", 3),
                CardDataClass("Ymmyll", 0, "Gojo", "Nah I'd win", 1),
                CardDataClass("Wensdee", 0, "4'11", "Got that goblin height", 1),
                CardDataClass("Tendenilla", 0, "Buttercup", "Feistiest powerpuff", 1),
                CardDataClass("Bilches", 0, "Sliz", "Nakaupo nakatulala Hindi ko alam bat ako natatawa", 3),
                CardDataClass("Marlyn", 0, "Unity", "*nagleave ng gc", 3),
                CardDataClass("Rodian", 0, "Kid", "Stop lying to me n-", 1),
                CardDataClass("Gene", 0, "Peter", "lois", 3),
                CardDataClass("Euzen", 0, "HOY", "HOY EUZEN!", 1),
                CardDataClass("Villaflor", 0, "6 Days", "RIP", 3),
                CardDataClass("Vivien", 0, "Y2K", "Bro is friends with taco", 1),
                CardDataClass("Mhell", 0, "Ughhh", "Sarap mo kuya", 2),
                CardDataClass("Rebeca", 0, "Friday", "Change school speedrun", 3),
                CardDataClass("Sean", 0, "Johnson", "Work over school on top", 3),
                CardDataClass("Eleyn", 0, "Ellie", "Badger for life", 1)
            )
            cards.forEach { insertCard(it, db) }

            val cardTypes = listOf(
                CardTypeDataClass(0, cards.first { it.name == "Andrei" }.id, "Limitless"),
                CardTypeDataClass(0, cards.first { it.name == "Andrei" }.id, "nonchalant"),
                CardTypeDataClass(0, cards.first { it.name == "Arrow" }.id, "Seggs"),
                CardTypeDataClass(0, cards.first { it.name == "Arrow" }.id, "nonchalant"),
                CardTypeDataClass(0, cards.first { it.name == "Berkeley" }.id, "Limitless"),
                CardTypeDataClass(0, cards.first { it.name == "Berkeley" }.id, "god of programming"),
                CardTypeDataClass(0, cards.first { it.name == "Vien" }.id, "Limitless"),
                CardTypeDataClass(0, cards.first { it.name == "Vien" }.id, "Gamer"),
                CardTypeDataClass(0, cards.first { it.name == "Blase" }.id, "Badger Ranger"),
                CardTypeDataClass(0, cards.first { it.name == "Blase" }.id, "Gamer"),
                CardTypeDataClass(0, cards.first { it.name == "Chi" }.id, "Limitless"),
                CardTypeDataClass(0, cards.first { it.name == "Chi" }.id, "Worker"),
                CardTypeDataClass(0, cards.first { it.name == "Clark" }.id, "Seggs"),
                CardTypeDataClass(0, cards.first { it.name == "Clark" }.id, "Gamer"),
                CardTypeDataClass(0, cards.first { it.name == "Gio" }.id, "Horsemen"),
                CardTypeDataClass(0, cards.first { it.name == "Gio" }.id, "Nonchalant"),
                CardTypeDataClass(0, cards.first { it.name == "Gregorio" }.id, "Holy Trinity"),
                CardTypeDataClass(0, cards.first { it.name == "Gregorio" }.id, "Gamer"),
                CardTypeDataClass(0, cards.first { it.name == "Jerwin" }.id, "Badger Ranger"),
                CardTypeDataClass(0, cards.first { it.name == "Jerwin" }.id, "Slay"),
                CardTypeDataClass(0, cards.first { it.name == "Jude" }.id, "Seggs"),
                CardTypeDataClass(0, cards.first { it.name == "Jude" }.id, "Slay"),
                CardTypeDataClass(0, cards.first { it.name == "Julie" }.id, "Badger Ranger"),
                CardTypeDataClass(0, cards.first { it.name == "Julie" }.id, "Lovers"),
                CardTypeDataClass(0, cards.first { it.name == "Robeck" }.id, "Lovers"),
                CardTypeDataClass(0, cards.first { it.name == "Robeck" }.id, "God of programming"),
                CardTypeDataClass(0, cards.first { it.name == "Keren" }.id, "Seggs haus"),
                CardTypeDataClass(0, cards.first { it.name == "Keren" }.id, "God of programming"),
                CardTypeDataClass(0, cards.first { it.name == "Loise" }.id, "Badger Ranger"),
                CardTypeDataClass(0, cards.first { it.name == "Loise" }.id, "Nonchalant"),
                CardTypeDataClass(0, cards.first { it.name == "Newton" }.id, "Badger Ranger"),
                CardTypeDataClass(0, cards.first { it.name == "Newton" }.id, "Lovers"),
                CardTypeDataClass(0, cards.first { it.name == "Dexter" }.id, "Horsemen"),
                CardTypeDataClass(0, cards.first { it.name == "Dexter" }.id, "Gamer"),
                CardTypeDataClass(0, cards.first { it.name == "Pot" }.id, "Lovers"),
                CardTypeDataClass(0, cards.first { it.name == "Pot" }.id, "Horsemen"),
                CardTypeDataClass(0, cards.first { it.name == "Rap" }.id, "Badger Ranger"),
                CardTypeDataClass(0, cards.first { it.name == "Rap" }.id, "Worker"),
                CardTypeDataClass(0, cards.first { it.name == "Renz" }.id, "Seggs haus"),
                CardTypeDataClass(0, cards.first { it.name == "Renz" }.id, "God of Programming"),
                CardTypeDataClass(0, cards.first { it.name == "Ymmyll" }.id, "Lovers"),
                CardTypeDataClass(0, cards.first { it.name == "Ymmyll" }.id, "Worker"),
                CardTypeDataClass(0, cards.first { it.name == "Wensdee" }.id, "Lovers"),
                CardTypeDataClass(0, cards.first { it.name == "Wensdee" }.id, "Powerpuff"),
                CardTypeDataClass(0, cards.first { it.name == "Tendenilla" }.id, "Lovers"),
                CardTypeDataClass(0, cards.first { it.name == "Tendenilla" }.id, "Powerpuff"),
                CardTypeDataClass(0, cards.first { it.name == "Bilches" }.id, "Powerpuff"),
                CardTypeDataClass(0, cards.first { it.name == "Bilches" }.id, "Nonchalant"),
                CardTypeDataClass(0, cards.first { it.name == "Marlyn" }.id, "Powerpuff"),
                CardTypeDataClass(0, cards.first { it.name == "Marlyn" }.id, "Slay"),
                CardTypeDataClass(0, cards.first { it.name == "Rodian" }.id, "Holy Trinity"),
                CardTypeDataClass(0, cards.first { it.name == "Rodian" }.id, "Nonchalant"),
                CardTypeDataClass(0, cards.first { it.name == "Gene" }.id, "Irreg"),
                CardTypeDataClass(0, cards.first { it.name == "Gene" }.id, "Holy Trinity"),
                CardTypeDataClass(0, cards.first { it.name == "Euzen" }.id, "Irreg"),
                CardTypeDataClass(0, cards.first { it.name == "Euzen" }.id, "Nonchalant"),
                CardTypeDataClass(0, cards.first { it.name == "Villaflor" }.id, "Extinct"),
                CardTypeDataClass(0, cards.first { it.name == "Villaflor" }.id, "Slay"),
                CardTypeDataClass(0, cards.first { it.name == "Vivien" }.id, "Lovers"),
                CardTypeDataClass(0, cards.first { it.name == "Vivien" }.id, "Slay"),
                CardTypeDataClass(0, cards.first { it.name == "Mhell" }.id, "Irreg"),
                CardTypeDataClass(0, cards.first { it.name == "Mhell" }.id, "Gamer"),
                CardTypeDataClass(0, cards.first { it.name == "Rebeca" }.id, "Extinct"),
                CardTypeDataClass(0, cards.first { it.name == "Rebeca" }.id, "Gamer"),
                CardTypeDataClass(0, cards.first { it.name == "Sean" }.id, "Extinct"),
                CardTypeDataClass(0, cards.first { it.name == "Sean" }.id, "Worker"),
                CardTypeDataClass(0, cards.first { it.name == "Eleyn" }.id, "Extinct"),
                CardTypeDataClass(0, cards.first { it.name == "Eleyn" }.id, "Badger Ranger")
            )
            cardTypes.forEach { insertCardType(it, db) }

            val achievements = listOf(
                AchievementDataClass("Love birds", "all lovers", false, null, null, null),
                AchievementDataClass("Codefest", "god of programming", false, null, null, null),
                AchievementDataClass("The horsemen of calamity", "horsemen", false, null, null, null),
                AchievementDataClass("The trinity of holiness", "holy trinity", false, null, null, null),
                AchievementDataClass("Limitless gala", "limitless", false, null, null, null),
                AchievementDataClass("Badger rangers roll out", "badger rangers", false, null, null, null),
                AchievementDataClass("The god of all who breathes", "greeno tier 3", false, cards.first { it.name == "Greeno" }.id, null, null),
                AchievementDataClass("Stiers", "all students", false, null, null, null),
                AchievementDataClass("Irregulars", "irregs", false, null, null, null),
                AchievementDataClass("Nonchalanters", "nonchalants", false, null, null, null),
                AchievementDataClass("Slayyy", "slays", false, null, null, null),
                AchievementDataClass("Ice queen", "viv tier 3", false, cards.first { it.name == "Y2K" }.id, null, null),
                AchievementDataClass("Late night devs", "andrie,kuma,robeck,mhelibog,blaseng", false, null, null, null),
                AchievementDataClass("Extinction", "extinct", false, null, null, null),
                AchievementDataClass("RIP", "6days tier 3", false, cards.first { it.name == "6 Days" }.id, null, null),
                AchievementDataClass("Cj seggs haus", "seggs", false, null, null, null),
                AchievementDataClass("Powerpuff Girls", "all power puff girls", false, null, null, null),
                AchievementDataClass("Grinders", "workers", false, null, null, null),
                AchievementDataClass("OG", "bek,chi,viv,mmyl,me,newton,andrei", false, null, null, null),
                AchievementDataClass("The battle between gods", "ker 3, bek 3", false, null, null, null),
                AchievementDataClass("Animal buddies", "kuma, gecko 3", false, null, null, null),
                AchievementDataClass("E-gamerz", "gamers", false, null, null, null),
                AchievementDataClass("Item completion", "items", false, null, null, null),
                AchievementDataClass("Bek's cheso", "bek, cheso", false, null, null, null),
                AchievementDataClass("Wet mysteries", "greeno,condensation", false, null, null, null),
                AchievementDataClass("Sister business", "Caster, crochet mushroom", false, cards.first { it.name == "Caster" }.id, null, null),
                AchievementDataClass("50pesos PC", "Maxy, photocards", false, cards.first { it.name == "Maxy" }.id, null, null),
                AchievementDataClass("Friendship is forever", "friendship bracelets, kuma, gecko, andrie, engel, Hegown", false, null, null, null),
                AchievementDataClass("muhahahaaa", "Bonbon,cholo", false, null, null, null),
                AchievementDataClass("STI greatest roberry", "samsnug J2, kuma, greeno", false, null, null, null),
                AchievementDataClass("YOBUDS", "andrie,kuma,gecko,Hegown,marlboro,vape", false, null, null, null),
                AchievementDataClass("Baka programmer to", "chatgpt,all of god of programmers", false, null, null, null)
            )
            achievements.forEach { insertAchievement(it, db) }
            db?.setTransactionSuccessful()
        }

            override fun close() {
        super.close()
    }
}