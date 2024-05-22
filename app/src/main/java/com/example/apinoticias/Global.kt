package com.example.apinoticias


data class ApiResponse(
    val status: String,
    val totalResults: Int,
    val results: List<Article>
)

data class Article(
    val title: String,
    val description: String,
    val image_url: String,
    val link:String
)