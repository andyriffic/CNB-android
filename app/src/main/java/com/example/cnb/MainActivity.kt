package com.example.cnb

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var _activateDevModeCount: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        val devMode = sharedPref.getBoolean("DEV_MODE", false)

        if (!devMode) {
            playButton.visibility = View.GONE
        }

        titleTextButton.setOnClickListener {
            this._activateDevModeCount += 1

            if (this._activateDevModeCount % 5 == 0) {
                val newDevMode = !sharedPref.getBoolean("DEV_MODE", false)
                sharedPref.edit().putBoolean("DEV_MODE", newDevMode).commit()
                playButton.visibility = if (newDevMode) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
            }
        }

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
