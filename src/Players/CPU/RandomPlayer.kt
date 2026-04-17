package Players.CPU

import Board
import Players.BasePlayer
import Players.Player
import kotlin.random.Random

class RandomPlayer(override val team: Char): BasePlayer() {
    override val idiom = "CPU"

    override fun takeTurn(board: Board) {
        println("$team Turn($idiom)")
        var applied = false
        // Trial and error is a stupid way to choose moves, but it's the quickest solution.
        while (!applied) {
            val index = Random.nextInt(0, board.size())
            index.let {
                applied = board.applyMove(index, team)
            }
        }
    }
}