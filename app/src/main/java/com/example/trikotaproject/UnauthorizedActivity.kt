package com.example.trikotaproject

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.trikotaproject.databinding.ActivityUnauthorizedMainBinding

class UnauthorizedActivity: AppCompatActivity() {

    private lateinit var binding: ActivityUnauthorizedMainBinding
    private lateinit var listIntent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUnauthorizedMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.getStartedBtn.setOnClickListener {
            listIntent = Intent(this, AuthorizedActivity::class.java)
            startActivity(listIntent)
        }
    }
}