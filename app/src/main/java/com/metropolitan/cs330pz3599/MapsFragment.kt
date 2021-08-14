package com.metropolitan.cs330pz3599

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment() {

    private val callback = OnMapReadyCallback { googleMap ->

        /* val sydney = LatLng(-34.0, 151.0)
          googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
          googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))*/

        val met = LatLng(44.83099771074645, 20.455403992055672)

        googleMap.mapType = GoogleMap.MAP_TYPE_NORMAL
        googleMap.uiSettings.isCompassEnabled = true
        googleMap.addMarker(MarkerOptions().position(met).title("Univerzitet Metropolitan"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(met))
        googleMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(
                    44.83099771074645,
                    20.455403992055672
                ), 15.0f
            )
        )
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