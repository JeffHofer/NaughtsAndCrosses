package Players.CPU

import Algorithms.TablularMonteCarloReinforcementLearning
import Board
import MLAlgo
import Outcome
import Players.BasePlayer
import Players.Player

class MachineLearningPlayer(override val team: Char, val algo: MLAlgo): BasePlayer() {
    override val idiom = "CPU"

    override fun takeTurn(board: Board) {
        println("$team Turn($idiom)")
        algo.processTurn(board, team, moveHistory)
    }

    override fun notifyResult(winner: Char) {
        val outcome = if (winner == team){
            Outcome.WIN
        } else if (winner == 'C') {
            Outcome.DRAW
        } else {
            Outcome.LOSS
        }
        algo.updateModelForResult(outcome, moveHistory)
    }
}