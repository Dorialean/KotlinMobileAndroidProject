package ru.mirea.lukutin.mireaproject.ui.slideshow

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.internal.ContextUtils.getActivity
import ru.mirea.lukutin.mireaproject.MainActivity
import ru.mirea.lukutin.mireaproject.R
import ru.mirea.lukutin.mireaproject.ui.slideshow.database.App
import ru.mirea.lukutin.mireaproject.ui.slideshow.database.Story
import java.util.*

class HistoryView: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story_view)
        findViewById<Button>(R.id.saveButton).setOnClickListener(this::saveClick)
    }

    private fun saveClick(view: View){
        val database = App.instance.database
        val storyDao = database.storyDao()

        val story = Story()
        story.storyTitle = findViewById<EditText>(R.id.titleEditText).text.toString()
        story.storyDate = SimpleDateFormat("d MMM. yyyy HH:mm").format(Date())
        story.storyText = findViewById<EditText>(R.id.historyEditText).text.toString()
        storyDao.insert(story)

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

}