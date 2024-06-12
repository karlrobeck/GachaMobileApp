package com.example.gachamobileapp

import java.math.BigDecimal

data class UserDataClass(
    val name: String, // Primary Key (TEXT)
    val money: BigDecimal, // DECIMAL(10,2) NOT NULL
    val image: String // TEXT NOT NULL
) {
    init {
        require(name.isNotEmpty()) { "Name cannot be empty" }
        require(money >= BigDecimal.ZERO) { "Money cannot be negative" }
    }
}

data class CardDataClass(
    val owner: String, // Foreign Key referencing User(name)
    val id: Int, // Primary Key (INTEGER) AUTOINCREMENT
    val name: String, // Unique (TEXT) NOT NULL
    val description: String, // TEXT NOT NULL
    val tier: Int // INTEGER NOT NULL CHECK (TIER <= 3)
) {
    init {
        require(name.isNotEmpty()) { "Card name cannot be empty" }
        require(description.isNotEmpty()) { "Card description cannot be empty" }
        require(tier in 1..3) { "Tier must be between 1 and 3" }
    }
}

data class CardTypeDataClass(
    val id: Int, // Primary Key (INTEGER) AUTOINCREMENT
    val cardId: Int, // Foreign Key referencing Card(id)
    val type: String // TEXT NOT NULL CHECK (TYPES IN ('nonchalant','god of programming', 'special'))
) {
    init {
        require(type in listOf("nonchalant", "god of programming", "special")) { "Invalid card type" }
    }
}

data class AchievementDataClass(
    val title: String, // TEXT NOT NULL
    val message: String, // TEXT NOT NULL
    val system: Boolean = false, // BOOL DEFAULT FALSE
    val cardId: Int?, // INTEGER references Card(id)
    val type: Int?, // INTEGER references another table (implementation depends)
    val owner: String? // references User(name)
)
