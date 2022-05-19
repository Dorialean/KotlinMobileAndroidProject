package ru.mirea.lukutin.mireaproject.ui.sensors

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import ru.mirea.lukutin.mireaproject.R

class SensorsFragment : Fragment(), View.OnClickListener,SensorEventListener {

    lateinit var accelerometrTextView: TextView
    lateinit var gravityTextView: TextView
    lateinit var magnetTextView: TextView
    lateinit var startBtn: Button

    companion object {
        fun newInstance() = SensorsFragment()
    }

    private lateinit var viewModel: SensorsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sensors, container, false)
        accelerometrTextView = view.findViewById(R.id.accelerometrTextView)
        gravityTextView = view.findViewById(R.id.gravityTextView)
        magnetTextView = view.findViewById(R.id.magnetTextView)
        startBtn = view.findViewById(R.id.sensorButton)
        startBtn.setOnClickListener(this)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SensorsViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onClick(p0: View?) {
        val sensorsMan: SensorManager = activity?.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val accelerometrSensor = sensorsMan.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        val gravitySensor = sensorsMan.getDefaultSensor(Sensor.TYPE_GRAVITY)
        val magnetSensor = sensorsMan.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
        sensorsMan.registerListener(this,accelerometrSensor,SensorManager.SENSOR_DELAY_NORMAL)
        sensorsMan.registerListener(this,gravitySensor,SensorManager.SENSOR_DELAY_NORMAL)
        sensorsMan.registerListener(this,magnetSensor,SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onSensorChanged(p0: SensorEvent?) {
        when(p0?.sensor?.type){
            Sensor.TYPE_ACCELEROMETER -> accelerometrTextView.text = p0.values[0].toString() + " " + p0.values[1].toString() + " " + p0.values[2].toString()
            Sensor.TYPE_MAGNETIC_FIELD -> magnetTextView.text = p0.values[0].toString() + " " + p0.values[1].toString() + " " + p0.values[2].toString()
            Sensor.TYPE_GRAVITY -> gravityTextView.text = p0.values[0].toString() + " " + p0.values[1].toString() + " " + p0.values[2].toString()
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

}