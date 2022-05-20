package ru.mirea.lukutin.mireaproject.ui.settings

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import ru.mirea.lukutin.mireaproject.R

class SettingsFragment : Fragment() {

    private lateinit var preferences: SharedPreferences
    private lateinit var editPropsText : EditText
    private lateinit var sunBtn : Button
    private lateinit var cloudBtn : Button
    private lateinit var rainyBtn : Button
    private lateinit var saveBtn : Button
    private lateinit var radioGrp : RadioGroup

    companion object {
        fun newInstance() = SettingsFragment()
    }

    private lateinit var viewModel: SettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)
        sunBtn = view?.findViewById(R.id.radio_sunny)!!
        cloudBtn = view?.findViewById(R.id.radio_cloudy)!!
        rainyBtn = view?.findViewById(R.id.radio_rainy)!!
        editPropsText = view?.findViewById(R.id.sharPropEditText)!!
        saveBtn = view?.findViewById(R.id.saveBtn)!!
        radioGrp = view?.findViewById(R.id.radioGroup)!!
        preferences = requireActivity().getSharedPreferences("SETTINGS", Context.MODE_PRIVATE)
        saveBtn.setOnClickListener{
            saveAllProperties()
        }
    }

    fun saveAllProperties(){
        val editor = preferences.edit()
        val weatherReport = when(radioGrp.checkedRadioButtonId){
            rainyBtn.id -> "Дождливо"
            sunBtn.id -> "Солнечно"
            cloudBtn.id -> "Облачно"
            else -> {"Нет погоды"}
        }

        editor.putString("Погода", weatherReport)
        editor.putString("Ощущения",editPropsText.text.toString())
        editor.apply()

        val result = "Сегодня " + preferences.getString("Погода","") + ", " + preferences.getString("Ощущения","")

        Toast.makeText(context, "Данные сохранены: $result",Toast.LENGTH_LONG).show()
    }

}