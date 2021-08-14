package com.metropolitan.cs330pz3599.pitanja

data class Pitanje(
    val id: Int,
    val tekstPitanja: String,
    val odgovor1: String,
    val odgovor2: String,
    val odgovor3: String,
    val odgovor4: String,
    val tacanOdgovor: Int
)