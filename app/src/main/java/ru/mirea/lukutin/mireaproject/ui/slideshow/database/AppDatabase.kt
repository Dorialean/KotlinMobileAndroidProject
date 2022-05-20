package ru.mirea.lukutin.mireaproject.ui.slideshow.database

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [Story::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun storyDao(): StoryDao
}