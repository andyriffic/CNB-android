package com.example.cnb

data class Avatar(
    val name: String,
    val imageName: String
)

data class PlayerGameState(
    val name: String?,
    val move: String?,
    val powerUp: String?,
    val avatar: Avatar?
)

data class GameResult(
    val draw: Boolean?,
    val winner: String?
)

data class GameState(
    val player1: PlayerGameState?,
    val player2: PlayerGameState?,
    val status: String,
    val result: GameResult?
)

data class Player(
    val name: String,
    val imageName: String,
    val team: String,
    val slot: String
)

data class Move(
    val id: String,
    val name: String,
    val imagePath: String
)

data class Theme(
    val name: String,
    val moves: Array<Move>
)