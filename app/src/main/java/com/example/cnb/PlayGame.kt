package com.example.cnb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_play_game.*
import kotlinx.android.synthetic.main.activity_spectator.*
import kotlinx.android.synthetic.main.activity_spectator.toolbar

class PlayGame : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_game)

        toolbar.setNavigationOnClickListener {
            finish()
        }

        selectPlayerButton.setOnClickListener {
            val intent = Intent(this, SelectPlayerActivity::class.java)
            startActivity(intent)
        }

    }
}
