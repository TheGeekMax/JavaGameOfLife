data class Vector2(var x: Float, var y: Float) {
    fun moduleSqred(): Float {
        return x * x + y * y
    }
}