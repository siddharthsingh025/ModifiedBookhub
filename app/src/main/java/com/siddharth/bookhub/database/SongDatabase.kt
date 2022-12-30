package com.siddharth.bookhub.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [SongEntity::class],version = 1)
abstract class SongDatabase : RoomDatabase(){

    abstract fun songDao():SongDao

}
