package view.components

import javax.swing.JTextField
import java.awt.Color
import java.awt.FontMetrics
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.Insets
import java.awt.RenderingHints

/**
 * Created by chist on 09.04.2017.
 */
class HintTextField extends JTextField{
    HintTextField(String hint) {
        _hint = hint
    }

    @Override
    void paint(Graphics g) {
        super.paint(g)
        if (getText().length() == 0) {
            int h = getHeight()
            ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON)
            Insets ins = getInsets()
            FontMetrics fm = g.getFontMetrics()
            int c0 = getBackground().getRGB()
            int c1 = getForeground().getRGB()
            int m = 0xfefefefe
            int c2 = ((c0 & m) >>> 1) + ((c1 & m) >>> 1)
            g.setColor(new Color(c2, true))
            g.drawString(_hint, ins.left as int, h / 2 + fm.getAscent() / 2 - 2 as int)
        }
    }
    private final String _hint
}
