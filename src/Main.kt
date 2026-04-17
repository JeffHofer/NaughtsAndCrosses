import Algorithms.TablularMonteCarloReinforcementLearning
import Players.CPU.MachineLearningPlayer
import Players.CPU.RandomPlayer
import Players.HumanPlayer
import kotlinx.serialization.json.Json
import java.io.File
import java.lang.Thread.sleep
import kotlin.random.Random

object Main {
    @kotlin.jvm.JvmStatic
    fun main(args: Array<String>) {
        val gamesToPlay = 1
        val modelVersion = "5"
        val modelInstance = createModelInstance(modelVersion)
        for(i in 1..gamesToPlay) {
            var currentTurn = 'X'
            val player1 = MachineLearningPlayer(if (Random.nextBoolean()) 'X' else 'O',
                TablularMonteCarloReinforcementLearning(modelInstance))
//            val player2 = MachineLearningPlayer(if (player1.team == 'X') 'O' else 'X',
//                TablularMonteCarloReinforcementLearning(modelInstance))
            val player2 = HumanPlayer(if (player1.team == 'X') 'O' else 'X')
            var board = Board()

            while (board.checkForWinner() == null) {
                if (currentTurn == player1.team) {
                    player1.takeTurn(board)
                } else {
                    player2.takeTurn(board)
                }
                currentTurn = if (currentTurn == 'O') 'X' else 'O'
            }
            board.checkForWinner()?.let { winner ->
                player1.notifyResult(winner)
                player2.notifyResult(winner)
                printGameOver(board, winner)
            }
            modelInstance.gamesPlayed++
        }
        writeModelInstanceToFile(modelInstance, modelVersion)
    }

    fun createModelInstance(modelVersion: String): TabularModel {
        return Json.decodeFromString(File("/Users/jeff.hofer/personal-repo/NaughtsAndCrosses/models/tabular-$modelVersion.json").readText())
    }

    fun writeModelInstanceToFile(modelInstance: TabularModel, modelVersion: String){
        File("/Users/jeff.hofer/personal-repo/NaughtsAndCrosses/models/tabular-$modelVersion.json").writeText(Json.encodeToString(modelInstance))
    }

    fun printGameOver(board: Board, winner: Char) {
        println()
        if (winner == 'C') {
            println("    CAT    ")
        } else {
            println("  $winner WINS!  ")
        }
        println("===========")
        board.print()
        println("===========")
    }
}