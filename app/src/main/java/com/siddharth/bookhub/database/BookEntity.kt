package com.siddharth.bookhub.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey



@Entity(tableName = "books")
data class BookEntity(
    @PrimaryKey val book_id:Int,
    @ColumnInfo(name="book_name") val bookName:String,
    @ColumnInfo(name="book_author")  val bookAuthor:String,
    @ColumnInfo(name="book_price") val bookPrice:String,
    @ColumnInfo(name="book_rating")  val bookRating:String,
    @ColumnInfo(name="book_description")  val bookDesc:String,
    @ColumnInfo(name="book_image") val bookImage:String
) {
}



@Entity(tableName = "songs")
data class SongEntity(
    @ColumnInfo(name="Song_name") var songName:String?,
    @ColumnInfo(name="Song_artist")  var songArtist:String?,
    @ColumnInfo(name="Song_url") var mp3Url:String?,
)
{
    @PrimaryKey (autoGenerate = true)
    @ColumnInfo(name="Song_id") var id:Int? =null
}