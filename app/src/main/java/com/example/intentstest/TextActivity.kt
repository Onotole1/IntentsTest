package com.example.intentstest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.example.intentstest.databinding.ActivityTextBinding

class TextActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityTextBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        binding.root.text = intent.getStringExtra(Intent.EXTRA_TEXT)
    }
}
