package com.example.apinoticias

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.apinoticias.databinding.ActivityMuestraNoticiasBinding

class MuestraNoticias : AppCompatActivity() {
    private lateinit var binding: ActivityMuestraNoticiasBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityMuestraNoticiasBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //obtener valores de los datos por medio del intent
        val Titulo=intent.getStringExtra("title")
        val Descrip=intent.getStringExtra("description")
        val img=intent.getStringExtra("image_url")

        binding.txtTitulo.text=Titulo
        binding.txtDescripcion.text=Descrip

        Glide.with(this)
            .load(img)
            .into(binding.imgNoticia)
    }
}