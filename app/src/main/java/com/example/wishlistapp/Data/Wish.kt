package com.example.wishlistapp.Data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "wish-table")
data class Wish(
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0L,
    @ColumnInfo("wish-title")
    val title : String = "",
    @ColumnInfo("wish-desc")
    val description : String = ""
)


object DummyWish{

    val wishlist = listOf(
        Wish(title = "Android Dev", description = "Get a job as Android Dev!"),
        Wish(title = "DSA", description = "learn DSA")
    )
}