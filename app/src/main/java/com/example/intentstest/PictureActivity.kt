package com.example.intentstest

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import coil.load
import com.example.intentstest.databinding.ActivityPictureBinding

class PictureActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityPictureBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        binding.root.load(intent.getParcelableExtra<Uri>(Intent.EXTRA_STREAM))
    }
}