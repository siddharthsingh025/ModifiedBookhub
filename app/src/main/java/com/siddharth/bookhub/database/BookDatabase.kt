package com.siddharth.bookhub.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [BookEntity::class,SongEntity::class],version = 1)
abstract class BookDatabase :RoomDatabase(){

    abstract fun bookDao():BookDao

    abstract fun songDao():SongDao

}


