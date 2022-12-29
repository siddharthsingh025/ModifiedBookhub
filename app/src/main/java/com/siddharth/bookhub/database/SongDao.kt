package com.siddharth.bookhub.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SongDao {
    @Insert
    fun insertSong(songEntity: SongEntity)


    @Delete
    fun deleteSong(songEntity: SongEntity)

    @Query("SELECT * FROM songs")
    fun getAllSongs(): List<SongEntity>

//
//    @Query("SELECT * FROM songs WHERE song_id = :songId")
//    fun getSongById(songId:String):SongEntity
}