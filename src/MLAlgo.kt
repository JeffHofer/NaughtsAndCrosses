interface MLAlgo {
    fun processTurn(board: Board, team: Char, moveHistory: MutableList<Pair<String, Int>>)
    fun updateModelForResult(outcome: Outcome, moveHistory: MutableList<Pair<String, Int>>)
}