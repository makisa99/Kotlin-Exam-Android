package com.metropolitan.cs330pz3599

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.metropolitan.cs330pz3599.pitanja.Deklaracije
import com.metropolitan.cs330pz3599.databinding.ActivityPitanjeBinding
import com.metropolitan.cs330pz3599.pitanja.Pitanje

class PitanjeActivity : AppCompatActivity(), View.OnClickListener {

    private var TrenutnoPitanje: Int = 0
    private var TacniOdgovori: Int = 0
    private var SvaPitanja: ArrayList<Pitanje>? = null
    private var OdabranOdgovor: Int = 0
    private lateinit var binding: ActivityPitanjeBinding
    private var brojindeksa: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPitanjeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        brojindeksa = intent.getStringExtra(Deklaracije.brojIndeksa)

        SvaPitanja = Deklaracije.getSvaPitanja()

        postaviPitanje()

        binding.tvOdg1.setOnClickListener(this)
        binding.tvOdg2.setOnClickListener(this)
        binding.tvOdg3.setOnClickListener(this)
        binding.tvOdg4.setOnClickListener(this)
        binding.btnsledecepitanje.setOnClickListener(this)

    }

    private fun postaviPitanje() {
        OdabranOdgovor = 0
        val pitanje = SvaPitanja!![TrenutnoPitanje]
        neselektovan()
        //Log.d("TRENUTNO: ", SvaPitanja!![TrenutnoPitanje].toString())
        binding.tvPitanje.text = pitanje!!.tekstPitanja
        binding.tvOdg1.text = pitanje.odgovor1
        binding.tvOdg2.text = pitanje.odgovor2
        binding.tvOdg3.text = pitanje.odgovor3
        binding.tvOdg4.text = pitanje.odgovor4

    }

    private fun neselektovan() {
        val odluke = ArrayList<TextView>()
        odluke.add(0, binding.tvOdg1)
        odluke.add(1, binding.tvOdg2)
        odluke.add(2, binding.tvOdg3)
        odluke.add(3, binding.tvOdg4)
        for (odluka in odluke) {
            odluka.setTextColor(Color.parseColor("#2E6A1B"))
            odluka.background = ContextCompat.getDrawable(this, R.drawable.odgovor_tv)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_odg1 -> {
                selektovanOdgovor(binding.tvOdg1, 1)
            }
            R.id.tv_odg2 -> {
                selektovanOdgovor(binding.tvOdg2, 2)
            }
            R.id.tv_odg3 -> {
                selektovanOdgovor(binding.tvOdg3, 3)
            }
            R.id.tv_odg4 -> {
                selektovanOdgovor(binding.tvOdg4, 4)
            }
            R.id.btnsledecepitanje -> {
                if (OdabranOdgovor != 0) {
                    if (SvaPitanja!![TrenutnoPitanje].tacanOdgovor == OdabranOdgovor) {
                        TacniOdgovori++
                        //Log.d("Tacno: ", TacniOdgovori.toString())
                    }
                    if (TrenutnoPitanje <= (SvaPitanja!!.size - 2)) {
                        //Log.d("Bilo je tacno: ", SvaPitanja!![TrenutnoPitanje].tacanOdgovor.toString())
                        TrenutnoPitanje++
                        postaviPitanje()
                    } else {
                        val intent = Intent(this, PoslednjiActivity::class.java)
                        intent.putExtra(Deklaracije.brojIndeksa, brojindeksa)
                        intent.putExtra(Deklaracije.tacni, TacniOdgovori)
                        intent.putExtra(Deklaracije.ukupno, SvaPitanja!!.size)
                        startActivity(intent)
                        finish()

                    }
                } else {
                    Toast.makeText(this, "Morate odabrati neki odgovor", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun selektovanOdgovor(tv: TextView, brojOdabira: Int) {
        neselektovan()
        OdabranOdgovor = brojOdabira
        tv.setTextColor(Color.parseColor("#2B5EDE"))
        tv.background = ContextCompat.getDrawable(this, R.drawable.odgovor_tv_selected)
    }
}