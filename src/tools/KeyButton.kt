package tools

import java.awt.event.KeyEvent

class KeyButton(private val keyCode:Int, val name:String) {
    private var keyPressed :Boolean = false
    private var functionActivated :Boolean = false

    fun keyPressed(e:KeyEvent) {
        if(!keyPressed && e.keyCode == keyCode){
            keyPressed = true
            functionActivated = true
        }
    }

    fun keyReleased(e:KeyEvent){
        if(e.keyCode == keyCode){
            keyPressed = false
            functionActivated = false
        }
    }

    fun activable():Boolean{
        if(functionActivated){
            functionActivated = false
            return true
        }
        return false
    }
}