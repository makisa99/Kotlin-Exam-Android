package com.metropolitan.cs330pz3599

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.metropolitan.cs330pz3599.pitanja.Deklaracije
import com.metropolitan.cs330pz3599.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnGo.setOnClickListener {
            if (binding.etBrInd.text.toString()
                    .isNotEmpty() && binding.etBrInd.text.toString().length == 4
            ) {
                val intent = Intent(this, PitanjeActivity::class.java)
                intent.putExtra(Deklaracije.brojIndeksa, binding.etBrInd.text.toString())
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Broj indeksa se sastoji od 4 cifre", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}