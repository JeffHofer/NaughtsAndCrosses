class Board {
    private var state = "         "

    fun checkForWinner(): Char? {
        arrayOf('X', 'O').forEach { it ->
            // Horizontals
            if(state[0] == it && state[1] == it && state[2] == it) return it
            if(state[3] == it && state[4] == it && state[5] == it) return it
            if(state[6] == it && state[7] == it && state[8] == it) return it

            // Verticals
            if(state[0] == it && state[3] == it && state[6] == it) return it
            if(state[1] == it && state[4] == it && state[7] == it) return it
            if(state[2] == it && state[5] == it && state[8] == it) return it

            // Diagonals
            if(state[0] == it && state[4] == it && state[8] == it) return it
            if(state[2] == it && state[4] == it && state[6] == it) return it
        }

        if (state.contains(" ")) {
            return null
        } else {
            return 'C'
        }

    }

    fun validateMove(index: Int): Boolean {
        return if (index == -1 || state[index] != ' ') {
            false
        } else {
            true
        }
    }

    fun applyMove(index: Int, team: Char): Boolean {
        if (!validateMove(index)) return false

        val boardArray = state.toCharArray()
        boardArray[index] = team
        state = String(boardArray)
        return true
    }

    fun print() {
        println("   ${state[0]}|${state[1]}|${state[2]}")
        println("   -----")
        println("   ${state[3]}|${state[4]}|${state[5]}")
        println("   -----")
        println("   ${state[6]}|${state[7]}|${state[8]}")
    }

    fun size(): Int {
        return state.length
    }

    fun teamAgnosticState(forPlayer: Char): String {
        if(forPlayer == 'X'){
            return state.replace('X', 'A').replace('O', 'B')
        }else{
            return state.replace('O', 'A').replace('X', 'B')
        }
    }
}