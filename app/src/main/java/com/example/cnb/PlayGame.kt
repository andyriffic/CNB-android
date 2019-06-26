package com.example.cnb

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_play_game.*
import kotlinx.android.synthetic.main.activity_spectator.*
import kotlinx.android.synthetic.main.activity_spectator.toolbar

class PlayGame : AppCompatActivity() {

    companion object {
        private const val PLAYER_SELECTION_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_game)

        toolbar.setNavigationOnClickListener {
            finish()
        }

        selectPlayerButton.setOnClickListener {
            val intent = Intent(this, SelectPlayerActivity::class.java)
            startActivityForResult(intent, PLAYER_SELECTION_REQUEST_CODE)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == PLAYER_SELECTION_REQUEST_CODE) {
            playerName.text = data?.getStringExtra("player-name")
            teamName.text = data?.getStringExtra("team-name")
        }
    }
}
