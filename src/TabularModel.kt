import kotlinx.serialization.Serializable

@Serializable
class TabularModel(val moveSets: MutableMap<String, MutableList<WeightedMove>>, var gamesPlayed: Int) {
    fun movesForState(state: String): List<WeightedMove> {
        return moveSets[state] ?: mutableListOf()
    }

    fun updateMoveValue(state: String, move: Int, transform: (Double) -> Double) {
        val weightedMove = moveSets[state]?.find { it -> it.move == move } ?: return
        weightedMove.weight = transform(weightedMove.weight)
    }

    fun createMoveIfMissing(state: String, move: Int, startValue: Double) {
        moveSets[state]?.find { it -> it.move == move }?.let { return }
        if(!moveSets.containsKey(state)){
            moveSets[state] = mutableListOf()
        }
        moveSets[state]?.add(WeightedMove(move, startValue))
    }
}