package ru.mirea.lukutin.mireaproject.ui.slideshow.database

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
class Story {
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
    var storyDate: String = ""
    var storyTitle: String = ""
    var storyText: String = ""
}