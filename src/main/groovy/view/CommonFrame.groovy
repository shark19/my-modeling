package view

import javax.swing.JFrame
import java.awt.*

/**
 * Created by chist on 2/18/17.
 */
abstract class CommonFrame extends JFrame{

    CommonFrame(){
        createContent()
        fillProps()
    }

    protected void fillProps() {
        size = new Dimension(800, 600)
        defaultCloseOperation = DISPOSE_ON_CLOSE
        resizable = false
        locationRelativeTo = null
    }

    protected abstract void createContent()
}
