import java.awt.Graphics

class Camera(private val cw: Int,
             private val tilemapWidth:Int, private val tilemapHeight:Int,
             private var screenWidth:Int, private var screenHeight:Int) {

    //variable internes de l'objet
    var camX:Float
    var camY:Float
    var isInBorder:Boolean

    //variable de calculs
    private var minX:Float
    private var minY:Float

    private var maxX:Float
    private var maxY:Float

    init{
        camX = (tilemapWidth*cw)/2f
        camY = (tilemapHeight*cw)/2f

        minX = 0f
        minY = 0f
        maxX = 0f
        maxY = 0f
        isInBorder = false
        calculateBorder()
    }

    private fun clamp(valeur:Float,min:Float,max:Float):Float{
        return Math.max(min,Math.min(valeur,max))
    }

    fun calculateBorder(){
        minX = screenWidth/2f
        minY = screenHeight/2f

        maxX = tilemapWidth*cw - screenWidth/2f
        maxY = tilemapHeight*cw - screenHeight/2f
    }

    fun UpdateScreenSize(w:Int,h:Int){
        //todo
    }

    fun updateCoors(x:Float, y:Float){
        camX += x
        camX = clamp(camX,minX,maxX)

        camY += y
        camY = clamp(camY,minY,maxY)

        isInBorder = (camX == minX || camX == maxX || camY == minY || camY == maxY)
    }

    private fun getBoundCoors():Pair<Vector2Int,Vector2Int> {
        var minCoors: Vector2Int = Vector2Int(((camX-(screenWidth/2f))/cw).toInt(),((camY-(screenHeight/2f))/cw).toInt())
        var maxCoors: Vector2Int = Vector2Int(((camX+(screenWidth/2f))/cw).toInt()+1,((camY+(screenHeight/2f))/cw).toInt()+1)
        maxCoors.x = Math.min(maxCoors.x,tilemapWidth-1)
        maxCoors.y = Math.min(maxCoors.y,tilemapWidth-1)

        //println(minCoors.x.toString()+" "+minCoors.y.toString())
        return Pair(minCoors,maxCoors)
    }

    fun showView(g:Graphics,cameT:CameraShow){
        val bounds :Pair<Vector2Int,Vector2Int> = getBoundCoors()
        var min:Vector2Int = bounds.first
        var max:Vector2Int = bounds.second
        for(i in min.x..max.x){
            for(j in min.y..max.y){
                cameT.showTile(g,i,j,i*cw-camX.toInt()+(screenWidth/2),j*cw-camY.toInt()+(screenHeight/2))
            }
        }
    }
}