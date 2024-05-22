package com.example.apinoticias

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apinoticias.databinding.ActivityMainBinding
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.result.Result
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.rclnoticias.layoutManager=LinearLayoutManager(this)
        fetchNews()
    }
    private fun fetchNews() {
        CoroutineScope(Dispatchers.IO).launch {
            Fuel.get("https://newsdata.io/api/1/news?apikey=pub_44668c4f60cd062cd44249f2600d37bd03d21&language=es")
                .responseString { _, _, result ->
                    when (result) {
                        is Result.Failure -> {
                            runOnUiThread {
                                Toast.makeText(this@MainActivity, "Error fetching data", Toast.LENGTH_LONG).show()
                            }
                        }
                        is Result.Success -> {
                            val data = result.get()
                            val newsResponse = Gson().fromJson(data, ApiResponse::class.java)
                            runOnUiThread {
                                binding.txtCantidad.text = "Hay un total de: ${newsResponse.totalResults} Noticias"

                                binding.rclnoticias.adapter = NewsAdapter(newsResponse.results) { news ->
                                    val intent = Intent(this@MainActivity, MuestraNoticias::class.java).apply {
                                        putExtra("title", news.title)
                                        putExtra("description",news.description)
                                        putExtra("image_url", news.image_url)
                                        putExtra("link",news.link)
                                    }
                                    startActivity(intent)
                                }
                            }
                        }
                    }
                }
        }
    }
}