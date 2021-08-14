package com.metropolitan.cs330pz3599

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.metropolitan.cs330pz3599.databinding.ActivityAboutusBinding
import com.metropolitan.cs330pz3599.R


class AboutusActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAboutusBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutusBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }


}