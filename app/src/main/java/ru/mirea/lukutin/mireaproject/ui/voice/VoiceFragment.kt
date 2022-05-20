package ru.mirea.lukutin.mireaproject.ui.voice

import android.Manifest
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewModelScope
import ru.mirea.lukutin.mireaproject.R
import java.io.File
import java.io.IOException

class VoiceFragment : Fragment() {

    companion object {
        fun newInstance() = VoiceFragment()
    }

    private lateinit var viewModel: VoiceViewModel
    private lateinit var button_start_recording: Button
    private lateinit var button_stop_recording: Button
    private lateinit var button_pause_recording: Button
    private lateinit var button_play_recording: Button
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var audioFile : File
    private lateinit var mediaRecorder: MediaRecorder
    private var state: Boolean = false
    private var recordingStopped: Boolean = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_voice, container, false)
        button_play_recording = view.findViewById(R.id.button_play_recording)
        button_start_recording = view.findViewById(R.id.button_start_recording)
        button_start_recording.setOnClickListener {
            if (ContextCompat.checkSelfPermission(view.context,
                    Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(view.context,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                val permissions = arrayOf(android.Manifest.permission.RECORD_AUDIO, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                ActivityCompat.requestPermissions(requireActivity(), permissions,0)
            } else {
                startRecording()
            }
        }
        button_stop_recording = view.findViewById(R.id.button_stop_recording)
        button_stop_recording.setOnClickListener{
            stopRecording()
        }
        button_pause_recording = view.findViewById(R.id.button_pause_recording)
        button_pause_recording.setOnClickListener {
            pauseRecording()
        }
        ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.RECORD_AUDIO),0);
        mediaRecorder = MediaRecorder()
        mediaPlayer = MediaPlayer()
        audioFile = File(
            requireContext().getExternalFilesDir(
                Environment.DIRECTORY_MUSIC
            ), "mirea.mp3"
        )
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC)
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
        mediaRecorder.setOutputFile(audioFile.absolutePath)

        button_play_recording.setOnClickListener {
            mediaPlayer.release()
            mediaPlayer = MediaPlayer()
            mediaPlayer.setDataSource(audioFile.path)
            mediaPlayer.prepare()
            mediaPlayer.start()
        }
        return view
    }

    private fun startRecording() {
        try {
            mediaRecorder.prepare()
            mediaRecorder.start()
            state = true
            Toast.makeText(view?.context, "Recording started!", Toast.LENGTH_SHORT).show()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun stopRecording(){
        if(state){
            mediaRecorder.stop()
            mediaRecorder.release()
            state = false
        }else{
            Toast.makeText(view?.context, "You are not recording right now!", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("RestrictedApi", "SetTextI18n")
    @TargetApi(Build.VERSION_CODES.N)
    private fun pauseRecording() {
        if(state) {
            if(!recordingStopped){
                Toast.makeText(view?.context,"Stopped!", Toast.LENGTH_SHORT).show()
                mediaRecorder.pause()
                recordingStopped = true
                button_pause_recording.text = "Resume"
            }else{
                resumeRecording()
            }
        }
    }

    @SuppressLint("RestrictedApi", "SetTextI18n")
    @TargetApi(Build.VERSION_CODES.N)
    private fun resumeRecording() {
        Toast.makeText(view?.context,"Resume!", Toast.LENGTH_SHORT).show()
        mediaRecorder?.resume()
        button_pause_recording.text = "Pause"
        recordingStopped = false
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(VoiceViewModel::class.java)
    }

}