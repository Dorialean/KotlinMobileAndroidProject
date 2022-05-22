package ru.mirea.lukutin.mireaproject.ui.map

import android.app.Dialog
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import ru.mirea.lukutin.mireaproject.R

class MapsFragment : Fragment()
{
    private val callback = OnMapReadyCallback { googleMap ->
        val mireaStrominka = LatLng(55.79368114001169, 37.70125089815368)
        val mireaVernandka1 = LatLng(55.661733, 37.477908)
        val mireaVernandka2 = LatLng(55.669803, 37.479429)
        val mireaPirog = LatLng(55.731457, 37.574415)
        val mireaSokol = LatLng(55.764925, 37.742172)
        val mireaKulak = LatLng(45.052221, 41.912577)
        val mireaVokzal = LatLng(55.966766, 38.050778)

        googleMap.addMarker(MarkerOptions().position(mireaStrominka).title("ул. Стромынка, 20, Москва")
            .snippet("МГУПИ \nДата основания:1936г.\nКоординаты:[55.794292, 37.701564]"))?.showInfoWindow()
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(mireaStrominka))
        googleMap.addMarker(MarkerOptions().position(mireaVernandka1).title("просп. Вернадского, 86, Москва")
            .snippet("МИТХТ \nДата основания: 1900 г. \nКоординаты: 55.661733, 37.477908 "))
        googleMap.addMarker(MarkerOptions().position(mireaVernandka2).title("просп. Вернадского, 78, Москва")
            .snippet("МИРЭА \nДата основания: 1947 г. \nКоординаты: 55.669803, 37.479429 "))
        googleMap.addMarker(MarkerOptions().position(mireaPirog).title("Малая Пироговская улица, 1с5, Москва")
            .snippet("МИРЭА \nДата основания: 1 июля 1900 г. \nКоординаты: 55.731457, 37.574415"))
        googleMap.addMarker(MarkerOptions().position(mireaSokol).title("5-я улица Соколиной Горы, 22, Москва")
            .snippet("МИРЭА \nДата основания: 1 июля 1900 г.\nКоординаты: 55.764925, 37.742172"))
        googleMap.addMarker(MarkerOptions().position(mireaKulak).title("проспект Кулакова, 8литА, Ставрополь")
            .snippet("МИРЭА Филиал в г. Ставрополе \nДата основания: 18 декабря 1996 г. \nКоординаты: 45.052221, 41.912577"))
        googleMap.addMarker(MarkerOptions().position(mireaVokzal).title("Вокзальная ул., 2А, корп. 61, Фрязино")
            .snippet("МИРЭА Филиал в г. Фрязино \nДата основания: 1962 г.\nКоординаты: 55.966766, 38.050778 "))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}