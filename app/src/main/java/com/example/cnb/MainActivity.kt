package com.example.cnb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        watchButton.setOnClickListener {
            val intent = Intent(this, Spectator::class.java)
            startActivity(intent)
        }

        playButton.setOnClickListener {
            val intent = Intent(this, PlayGame::class.java)
            startActivity(intent)
        }
    }
}
