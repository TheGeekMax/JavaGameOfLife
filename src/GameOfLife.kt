class GameOfLife(var plateau: Array<BooleanArray>) {
    private fun isNeighbour(x:Int, y:Int) : Boolean = if (x < 0 || x >= plateau.size || y < 0 || y >= plateau[0].size) false else plateau[x][y]


    private fun countNeightbour(x:Int, y:Int): Int{
        var count= 0
        for(i in (-1)..1) {
            for(j in (-1)..1) {
                if(i != 0 || j != 0){
                    if(isNeighbour(x+i,y+j)){
                        count++
                    }
                }
            }
        }
        return count
    }

    fun iteration(){
        val newItt = Array(plateau.size) {BooleanArray(plateau[0].size)}
        for(i in newItt.indices) {
            for(j in 0 until newItt[i].size) {
                var count = countNeightbour(i,j)

                if(plateau[i][j]){
                    newItt[i][j] = (count == 2 || count == 3)
                }else{
                    newItt[i][j] = (count == 3)
                }
            }
        }
        for(i in newItt.indices) {
            for(j in 0 until newItt[i].size) {
                plateau[i][j] = newItt[i][j]
            }
        }

    }
}