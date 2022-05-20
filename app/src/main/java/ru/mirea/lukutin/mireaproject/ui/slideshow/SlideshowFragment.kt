package ru.mirea.lukutin.mireaproject.ui.slideshow

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.mirea.lukutin.mireaproject.R
import ru.mirea.lukutin.mireaproject.databinding.FragmentSlideshowBinding
import ru.mirea.lukutin.mireaproject.ui.slideshow.database.App
import ru.mirea.lukutin.mireaproject.ui.slideshow.database.AppDatabase
import ru.mirea.lukutin.mireaproject.ui.slideshow.database.Story
import ru.mirea.lukutin.mireaproject.ui.slideshow.database.StoryDao

class SlideshowFragment : Fragment() {

    private lateinit var _binding: FragmentSlideshowBinding
    private val binding get() = _binding!!

    private lateinit var list: RecyclerView
    private lateinit var stories: ArrayList<Story>
    private lateinit var storyDao: StoryDao
    private lateinit var appDatabase: AppDatabase
    private lateinit var recyclerView: RecyclerView
    private lateinit var addStoryBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSlideshowBinding.inflate(inflater, container, false)
        val root: View = binding.root

        list = binding.recyclerView
        appDatabase = App.instance.database
        storyDao = appDatabase.storyDao()
        stories = storyDao.all as ArrayList<Story>
        println(stories)
        val adapter = HistoryAdapter(context, stories)
        _binding.recyclerView.adapter = adapter

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view?.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(view?.context)

        addStoryBtn = view?.findViewById(R.id.addStoryButton)
        addStoryBtn.setOnClickListener{
            val intent = Intent(view.context, HistoryView::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}