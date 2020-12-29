package com.example.intentstest

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import coil.load
import com.example.intentstest.databinding.ActivityPictureBinding
import com.example.intentstest.databinding.ActivityTextAndPictureBinding

class TextAndPictureActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityTextAndPictureBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        binding.image.load(intent.getParcelableExtra<Uri>(Intent.EXTRA_STREAM))
        binding.text.text = intent.getStringExtra(Intent.EXTRA_TEXT)
    }
}