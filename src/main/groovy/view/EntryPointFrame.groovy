package view

import javax.swing.*
import java.awt.*
import java.awt.event.ActionEvent
import java.awt.event.ActionListener

/**
 * Created by chist on 2/18/17.
 */
class EntryPointFrame extends CommonFrame implements ActionListener{
    private JComboBox<String> comboBox

    @Override
    protected void createContent(){
        def panel = new JPanel()
        panel.layout =  new BoxLayout(panel, BoxLayout.PAGE_AXIS)
        panel.add createComboBox()
        panel.add createOpenButton()
        contentPane = panel
    }

    private JComboBox<String> createComboBox() {
        comboBox = new JComboBox<>()
        LabType.values().each {
            comboBox.addItem(it.labName as String)
        }
        comboBox.maximumSize = comboBox.getPreferredSize()
        comboBox
    }

    private JButton createOpenButton() {
        def button = new JButton()
        button.text = 'Open'
        button.addActionListener this
        button.alignmentX = CENTER_ALIGNMENT
        button
    }

    @Override
    protected void fillProps() {
        super.fillProps()
        size = new Dimension(250, 150)
        title = 'My Labs'
        defaultCloseOperation = EXIT_ON_CLOSE
    }

    void actionPerformed(ActionEvent actionEvent) {
        LabType.showFrameById(comboBox.selectedIndex)
    }
}
