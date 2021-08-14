package com.metropolitan.cs330pz3599

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.metropolitan.cs330pz3599.baza.Baza
import com.metropolitan.cs330pz3599.databinding.ActivityPoslednjiBinding
import com.metropolitan.cs330pz3599.pitanja.Deklaracije
import com.metropolitan.cs330pz3599.rest.ApiRest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Retrofit


class PoslednjiActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPoslednjiBinding
    private var polozio = true
    internal var helper = Baza(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPoslednjiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val brojindeksa = intent.getStringExtra(Deklaracije.brojIndeksa)
        val ukupnopitanja = intent.getIntExtra(Deklaracije.ukupno, 0)
        val tacniodgovori = intent.getIntExtra(Deklaracije.tacni, 0)

        binding.tvPoslednjaPrvi.text = brojindeksa
        if (tacniodgovori < ((ukupnopitanja / 2) + 1)) {
            polozio = false
            binding.tvPoslednjaDrugi.text = "Više sreće drugi put"
            binding.tvPoslednjaDrugi.setTextColor(Color.parseColor("#781F1F"))
            binding.tvPoslednjaDrugi.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25F)
            binding.tvPoslednjaPrvi.setTextColor(Color.parseColor("#781F1F"))
            binding.btnRezultati.setBackgroundColor(Color.parseColor("#531313"))
            // binding.kardvju.setCardBackgroundColor (Color.parseColor("#FF6666"))
            binding.root.setBackgroundResource(R.drawable.pozadina2)
            supportActionBar?.apply {
                title = "Pali ste :("
                setBackgroundDrawable(
                    ColorDrawable(
                        Color.parseColor("#531313")
                    )
                )
            }
            val window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = Color.parseColor("#531313")
        }

        if (brojindeksa != null) {
            helper.insertData(brojindeksa, "$tacniodgovori/$ukupnopitanja")
        }

        val thread = Thread {
            try {
                if (brojindeksa != null) {
                    rawJSON(brojindeksa, "$tacniodgovori/$ukupnopitanja")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        if (isOnline(this)) {
            thread.start()
        } else {
            Toast.makeText(
                applicationContext,
                "Nemate pristup internetu za POST na sajt",
                Toast.LENGTH_LONG
            ).show()
        }

        binding.btnRezultati.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Rezultati:")
            var krajnji = ""
            for (rezult in helper.getAllData()) {
                krajnji += "$rezult\n"
            }
            builder.setMessage(krajnji.trim())
            builder.setPositiveButton("Super") { dialogInterface, which ->
                //Toast.makeText(applicationContext,"Odlicno",Toast.LENGTH_LONG).show()
            }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (polozio) {
            menuInflater.inflate(R.menu.hvaljenje_menu, menu)
        }
        else{
            menuInflater.inflate(R.menu.pao_menu, menu)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.pohvali_se) {
            // var obj = SmsManager.getDefault()
            //obj.sendTextMessage("+381232323", null, "Polozio sam ispit iz IT355", null, null)
            val intent = Intent(Intent.ACTION_SENDTO)
            // intent.type = "text/plain"
            intent.data = Uri.parse("mailto: Example@gmail.com")
            intent.putExtra(Intent.EXTRA_SUBJECT, "IT355 Rezultat")
            intent.putExtra(Intent.EXTRA_TEXT, "Cao, polozio sam ispit iz IT355 :)")
            startActivity(intent)
            return true
        }
        if (id == R.id.o_nama) {
            val intent = Intent(this, AboutusActivity::class.java)
            startActivity(intent)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun rawJSON(indeks: String, poeni: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://mferketic.com/")
            .build()
        val service = retrofit.create(ApiRest::class.java)
        val jsonObject = JSONObject()
        jsonObject.put("brInd", indeks)
        jsonObject.put("points", poeni)
        val jsonObjectString = jsonObject.toString()
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
        CoroutineScope(Dispatchers.IO).launch {
            service.rawJSON(requestBody)
        }
    }

    private fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }
}