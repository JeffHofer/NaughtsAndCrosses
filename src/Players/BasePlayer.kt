package Players

abstract class BasePlayer: Player{
    val moveHistory: MutableList<Pair<String, Int>>

    init {
        moveHistory = ArrayList()
    }
}