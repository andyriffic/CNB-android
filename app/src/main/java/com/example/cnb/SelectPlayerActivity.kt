package com.example.cnb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SelectPlayerActivity : AppCompatActivity() {

    private val gameConnection: GameConnection = GameConnection

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_player)
    }
}
