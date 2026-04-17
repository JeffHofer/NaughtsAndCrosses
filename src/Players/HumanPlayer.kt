package Players

import Board

class HumanPlayer(override val team: Char): BasePlayer() {
    override val idiom = "Human"

    override fun takeTurn(board: Board) {
        var applied = false
        while (!applied) {
            board.print()
            println("$team Turn($idiom)")
            print("Enter your move: ")
            val playerMove = readln()
            val index = parsePlayerMove(playerMove)
            index?.let {
                applied = board.applyMove(index, team)
                if(!applied) {
                    println("invalid move")
                }
            }
        }
    }

    fun parsePlayerMove(inputMove: String): Int? {
        val move = inputMove.uppercase()

        if (move.length < 2) return -1

        val row = move[0]
        val col = move[1]

        if (row !in 'A'..'C' || col !in '1'..'3') {
            return null
        }

        val rowIndex = (row - 'A') * 3
        val colIndex = col - '1'

        return rowIndex + colIndex
    }
}