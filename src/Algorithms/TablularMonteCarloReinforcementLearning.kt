package Algorithms

import Board
import MLAlgo
import Outcome
import TabularModel
import kotlinx.serialization.json.Json
import java.io.File
import kotlin.math.pow
import kotlin.random.Random

class TablularMonteCarloReinforcementLearning(val modelInstance: TabularModel): MLAlgo {
    val initializationValue = 0.5
    val learningRate = 0.1

    // Random sample values
    val epsilonDecayRate = 0.99995

    override fun processTurn(board: Board, team: Char, moveHistory: MutableList<Pair<String, Int>>) {
        val state = board.teamAgnosticState(team)
        val moves = modelInstance.movesForState(state)
        if (moves.isEmpty() || Random.nextInt(100) <= calculateSamplePercent()){
            println("I'm selecting a move randomly, either because I don't have one, or I'm random sampling")
            val move = randomMove(board, team)
            board.applyMove(move, team)
            moveHistory.add(Pair(state, move))
        } else {
            println("I'm selecting a move based off rankings")
            val move = moves.maxBy { it.weight }.move
            board.applyMove(move, team)
            moveHistory.add(Pair(state, move))
        }
    }

    fun randomMove(board: Board, team: Char): Int {
        var applied = false
        var index = 0
        // Trial and error is a stupid way to choose moves, but it's the quickest solution.
        while (!applied) {
            index = Random.nextInt(0, board.size())
            index.let {
                applied = board.applyMove(index, team)
            }
        }
        return index
    }

    override fun updateModelForResult(outcome: Outcome, moveHistory: MutableList<Pair<String, Int>>){
        val reward = if(outcome == Outcome.WIN){
            1.0
        } else if (outcome == Outcome.LOSS){
            -1.0
        } else {
            0.1
        }

        moveHistory.forEach { move ->
            modelInstance.createMoveIfMissing(move.first, move.second, initializationValue)
            modelInstance.updateMoveValue(move.first, move.second,{ currentValue ->
                currentValue + (learningRate * (reward-currentValue))
            })
        }
    }

    fun calculateSamplePercent(): Double {
        val gamesPlayed = modelInstance.gamesPlayed
        val epsilon = epsilonDecayRate.pow(gamesPlayed)
        return epsilon * 100
    }
}