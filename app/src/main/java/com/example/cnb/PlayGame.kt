package com.example.cnb

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_play_game.*
import kotlinx.android.synthetic.main.move_selection.view.*

class PlayGame : AppCompatActivity() {

    companion object {
        private const val PLAYER_SELECTION_REQUEST_CODE = 1
    }

    private val gameConnection: GameConnection = GameConnection
    private var selectedPlayerSlot: String? = null
    private var selectedPlayerName: String? = null
    private var selectedPlayerImageName: String? = null
    private var selectedMoveId: String? = null
    private var selectedColorBackground = ColorDrawable(Color.parseColor("#14A76C"))
    private var complete = false

    private fun setSelectPlayerVisibility() {
        val playerSelected = selectedPlayerSlot != null

        if (playerSelected) {
            selectPlayerButton.visibility = View.GONE
            moveSelection.visibility = View.VISIBLE
        } else {
            selectPlayerButton.visibility = View.VISIBLE
            moveSelection.visibility = View.GONE
        }
    }

    private fun setPlayButtonVisibility() {
        val selectedMove = selectedMoveId != null

        if (selectedMove) {
            playGameButton.visibility = View.VISIBLE
        } else {
            playGameButton.visibility = View.INVISIBLE
        }
    }

    private fun selectPlayerMove(moveId: String) {
        if (complete) {
            return
        }
        selectedMoveId = moveId
        moveSelection.optionA.background = ColorDrawable(Color.TRANSPARENT)
        moveSelection.optionB.background = ColorDrawable(Color.TRANSPARENT)
        moveSelection.optionC.background = ColorDrawable(Color.TRANSPARENT)
        when {
            selectedMoveId == "A" -> moveSelection.optionA.background = selectedColorBackground
            selectedMoveId == "B" -> moveSelection.optionB.background = selectedColorBackground
            selectedMoveId == "C" -> moveSelection.optionC.background = selectedColorBackground
        }

        setPlayButtonVisibility()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_game)

        loadingIndicator.visibility = View.INVISIBLE

        setSelectPlayerVisibility()
        setPlayButtonVisibility()

        toolbar.setNavigationOnClickListener {
            finish()
        }

        selectPlayerButton.setOnClickListener {
            val intent = Intent(this, SelectPlayerActivity::class.java)
            startActivityForResult(intent, PLAYER_SELECTION_REQUEST_CODE)
        }

        moveSelection.optionA.setOnClickListener {
            selectPlayerMove("A")
        }

        moveSelection.optionB.setOnClickListener {
            selectPlayerMove("B")
        }

        moveSelection.optionC.setOnClickListener {
            selectPlayerMove("C")
        }

        playGameButton.setOnClickListener {
            gameConnection.makeMove(
                selectedPlayerSlot, selectedMoveId, selectedPlayerName, selectedPlayerImageName
            )
            playGameButton.visibility = View.GONE
            readyText.visibility = View.VISIBLE
            complete = true
        }

        gameConnection.onThemeUpdate { theme ->
            runOnUiThread {
                Picasso
                    .with(this)
                    .load("${gameConnection.baseUrl}/${theme.moves[0].imagePath}")
                    .fit()
                    .centerInside()
                    .into(moveSelection.optionA)

                Picasso
                    .with(this)
                    .load("${gameConnection.baseUrl}/${theme.moves[1].imagePath}")
                    .fit()
                    .centerInside()
                    .into(moveSelection.optionB)

                Picasso
                    .with(this)
                    .load("${gameConnection.baseUrl}/${theme.moves[2].imagePath}")
                    .fit()
                    .centerInside()
                    .into(moveSelection.optionC)
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == PLAYER_SELECTION_REQUEST_CODE) {
            playerName.text = data?.getStringExtra("player-name")
            teamName.text = data?.getStringExtra("team-name")
            selectedPlayerSlot = data?.getStringExtra("player-slot")
            selectedPlayerName = data?.getStringExtra("player-name")
            selectedPlayerImageName = data?.getStringExtra("player-image")
            setSelectPlayerVisibility()
        }
    }
}
