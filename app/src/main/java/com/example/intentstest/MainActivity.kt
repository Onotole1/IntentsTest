package com.example.intentstest

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import androidx.core.content.FileProvider
import coil.load
import com.example.intentstest.databinding.ActivityMainBinding
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private companion object {

        const val CAMERA_REQUEST = 43434
    }

    private val photoURI: Uri by lazy(LazyThreadSafetyMode.NONE) {
        FileProvider.getUriForFile(
            this,
            BuildConfig.FILE_PROVIDER_AUTHORITIES,
            createImageFile()
        )
    }
    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityMainBinding.inflate(LayoutInflater.from(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.preview.setOnClickListener {
            camera()
        }

        // Обработка этих интентов зависит от разработчиков сторонних приложений
        // На эмуляторе все 3 запускают СМС, например
        // Проверку через resolveActivity здесь не делаю т.к. наше приложение умеет их обрабатывать
        // Но в идеале нужно добавить
        binding.textAndPictureButton.setOnClickListener {
            Intent(Intent.ACTION_SEND)
                .putExtra(Intent.EXTRA_TEXT, binding.editText.text.toString())
                .putExtra(Intent.EXTRA_STREAM, photoURI)
                .setType("image/*") // text/* тоже подойдёт на самом деле
                .also(::startActivity)
        }

        binding.pictureButton.setOnClickListener {
            Intent(Intent.ACTION_SEND)
                .putExtra(Intent.EXTRA_STREAM, photoURI)
                .setType("image/*")
                .also(::startActivity)
        }

        binding.textButton.setOnClickListener {
            Intent(Intent.ACTION_SEND)
                .putExtra(Intent.EXTRA_TEXT, binding.editText.text.toString())
                .setType("text/plain")
                .also(::startActivity)
        }
    }

    private fun camera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // А здесь делаю
            takePictureIntent.resolveActivity(packageManager)?.also {
                createImageFile().also {
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                }

                startActivityForResult(takePictureIntent, CAMERA_REQUEST)
            }
        }

    }

    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            binding.preview.load(photoURI)
        }
    }
}