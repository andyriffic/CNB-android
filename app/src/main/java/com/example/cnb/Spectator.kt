package com.example.cnb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_spectator.*
import kotlinx.android.synthetic.main.team_view.view.*

class Spectator : AppCompatActivity() {

    private val TAG = "SOCKET"
    private val gameConnection: GameConnection = GameConnection


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spectator)

        toolbar.setNavigationOnClickListener {
            finish()
        }

        player1View.winnerIcon.visibility = View.INVISIBLE
        player1View.winnerText.text = ""

        player2View.winnerIcon.visibility = View.INVISIBLE
        player2View.winnerText.text = ""

        gameConnection.onGameStateUpdate { gameState ->
            runOnUiThread {

                loadingIndicator.visibility = View.INVISIBLE
                gameState.player1?.let { player ->
                    player1View.teamName.text = player.name
                    player1View.playerName.text = player.avatar?.name

                    if (gameState.status == "FINISHED") {
                        player1View.usedPowerup.text = player.powerUp

                    } else {
                        when {
                            player.powerUp == null -> player1View.usedPowerup.text = ""
                            player.powerUp != "NONE" -> player1View.usedPowerup.text = "YES"
                            else -> player1View.usedPowerup.text = "NO"
                        }
                    }
                }

                gameState.player2?.let { player ->
                    player2View.teamName.text = player.name
                    player2View.playerName.text = player.avatar?.name

                    if (gameState.status == "FINISHED") {
                        player2View.usedPowerup.text = player.powerUp

                    } else {
                        when {
                            player.powerUp == null -> player2View.usedPowerup.text = ""
                            player.powerUp != "NONE" -> player2View.usedPowerup.text = "YES"
                            else -> player2View.usedPowerup.text = "NO"
                        }
                    }
                }

                player1View.winnerIcon.visibility = View.INVISIBLE
                player1View.winnerText.text = ""

                player2View.winnerIcon.visibility = View.INVISIBLE
                player2View.winnerText.text = ""

                gameState.result?.let {
                    when {
                        gameState.result.draw == true -> {
                            player1View.winnerIcon.visibility = View.VISIBLE
                            player1View.winnerText.text = "Draw"
                            player2View.winnerIcon.visibility = View.VISIBLE
                            player2View.winnerText.text = "Draw"
                        }
                        gameState.result.winner == "player1" -> {
                            player1View.winnerIcon.visibility = View.VISIBLE
                            player1View.winnerText.text = "Winner"
                        }
                        gameState.result.winner == "player2" -> {
                            player2View.winnerIcon.visibility = View.VISIBLE
                            player2View.winnerText.text = "Winner"
                        }
                    }
                }

            }
        }

    }
}
