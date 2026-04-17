import kotlinx.serialization.Serializable

@Serializable
class WeightedMove(val move: Int, var weight: Double)