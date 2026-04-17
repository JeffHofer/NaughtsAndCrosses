package Players

import Board

interface Player {
    val team: Char
    val idiom: String
    fun takeTurn(board: Board)
    fun notifyResult(winner: Char) {}
}