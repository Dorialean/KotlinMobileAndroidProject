package ru.mirea.lukutin.mireaproject.ui.slideshow.database

import androidx.room.*

@Dao
interface StoryDao {
    @get:Query("SELECT * FROM story")
    val all: List<Story>

    @Query("SELECT * FROM story WHERE id = :id")
    fun getById(id: Int): Story

    @Insert
    fun insert(story: Story)

    @Update
    fun update(story: Story)

    @Delete
    fun delete(story: Story)
}