package tools

import java.awt.event.KeyEvent

class KeyManager {
    var buttons:MutableList<KeyButton> = mutableListOf()

    fun addButton(keyCode:Int,name:String):Boolean{
        return buttons.add(KeyButton(keyCode,name))
    }

    fun keyPressed(e: KeyEvent) {
        for(button in buttons){
            button.keyPressed(e)
        }
    }

    fun keyReleased(e: KeyEvent){
        for(button in buttons){
            button.keyReleased(e)
        }
    }

    fun activable(name: String):Boolean{
        for(button in buttons){
            if(button.name == name){
                return button.activable()
            }
        }
        return false
    }

}