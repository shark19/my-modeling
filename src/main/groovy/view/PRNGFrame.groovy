package view

import math.Common
import math.CustomRandom
import math.Kolmogorov
import math.UniformDistribution
import view.components.HintTextField

import javax.swing.BorderFactory
import javax.swing.BoxLayout
import javax.swing.JButton
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.JTable
import javax.swing.JTextField
import javax.swing.border.TitledBorder
import java.awt.Dimension
import java.awt.Font
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.event.ActionEvent
import java.awt.event.ActionListener

/**
 * Created by chist on 4/1/17.
 */
class PRNGFrame extends CommonFrame{
    private HintTextField numbersCount, seed
    private JTextField tf0, tf1, tf2, tf3, tf4, tf5
    private int r0, r1, r2, r3, r4, r5
    private int rows
    private JPanel algoPanel, tablePanel
    public static String[] columnNames = ['0', '1', '2', '3'] as String[]
    private PRNGBundle bundle = new PRNGBundle()

    protected void createContent() {
        def panel = new JPanel()
        panel.layout = new BoxLayout(panel, BoxLayout.LINE_AXIS)

        algoPanel = new JPanel()
        algoPanel.layout = new BoxLayout(algoPanel, BoxLayout.PAGE_AXIS)
        algoPanel.add new JScrollPane(new JTable())
        panel.add algoPanel

        panel.add createConrols()

        tablePanel = new JPanel()
        tablePanel.layout = new BoxLayout(tablePanel, BoxLayout.PAGE_AXIS)
        tablePanel.add new JScrollPane(new JTable())
        panel.add tablePanel

        contentPane = panel
    }

    private JComponent createConrols() {
        def panel = new JPanel()
        panel.setBorder(BorderFactory.createEmptyBorder(330, 0, 330, 0))
        panel.layout = new BoxLayout(panel, BoxLayout.PAGE_AXIS)
        panel.add numbersCount = new HintTextField('Numbers count')
        panel.add seed = new HintTextField('Seed')
        panel.add createGridPanel()
        def generate = new JButton('Generate')
        generate.addActionListener(generateListener())
        panel.add generate
        def check = new JButton('Check')
        check.addActionListener(checkListener())
        panel.add check
        panel
    }

    private ActionListener generateListener() {
        new ActionListener(){
            @Override
            void actionPerformed(ActionEvent e) {
                updateAlgoPanel()
                updateTablePanel()
            }
        }
    }

    private void updateTablePanel(){
        tablePanel.clear()
        rows = numbersCount.text as int
        def data = new Object[rows][columnNames.length]
        bundle.tableF = Common.getRandoms(rows, r0 = tf0.text as int, r1 = tf1.text as int)
        bundle.tableS = Common.getRandoms(rows, r2 = tf2.text as int, r3 = tf3.text as int)
        bundle.tableT = Common.getRandoms(rows, r4= tf4.text as int, r5 = tf5.text as int)
        for(int i = 1; i <= rows; i++) {
            data[i - 1][0] = i
            data[i - 1][1] = bundle.tableF.get i-1
            data[i - 1][2] = bundle.tableS.get i-1
            data[i - 1][3] = bundle.tableT.get i-1
        }
        tablePanel.setBorder (BorderFactory.createTitledBorder (BorderFactory.createEtchedBorder (),
                'Table',
                TitledBorder.CENTER,
                TitledBorder.TOP,
                new Font("Serif", Font.BOLD, 18)))
        tablePanel.add createTable(data, columnNames)
        tablePanel.validate()
        tablePanel.repaint()
    }

    private void updateAlgoPanel(){
        algoPanel.clear()
        rows = numbersCount.text as int
        def data = new Object[rows][columnNames.length]
        def rand = new CustomRandom()
        long seedVal = seed.text.isNumber() ? Long.parseLong(seed.text) : System.currentTimeMillis()
        bundle.algF = rand.getRandomValueList(r0 = tf0.text as int, r1 = tf1.text as int, rows, seedVal)
        bundle.algS = rand.getRandomValueList(r2 = tf2.text as int, r3 = tf3.text as int, rows, seedVal)
        bundle.algT = rand.getRandomValueList(r4 = tf4.text as int, r5 = tf5.text as int, rows, seedVal)
        for(int i = 1; i <= rows; i++) {
            data[i - 1][0] = i
            data[i - 1][1] = bundle.algF.get i-1
            data[i - 1][2] = bundle.algS.get i-1
            data[i - 1][3] = bundle.algT.get i-1
        }
        algoPanel.setBorder (BorderFactory.createTitledBorder (BorderFactory.createEtchedBorder (),
                'Algorithmic',
                TitledBorder.CENTER,
                TitledBorder.TOP,
                new Font("Serif", Font.BOLD, 18)))
        algoPanel.add createTable(data, columnNames)
        algoPanel.validate()
        algoPanel.repaint()
    }

    private ActionListener checkListener() {
        new ActionListener(){
            @Override
            void actionPerformed(ActionEvent e) {
                bundle.uniformF = UniformDistribution.getUniDistrFuncValues(bundle.algF, r0, r1, rows)
                bundle.uniformS = UniformDistribution.getUniDistrFuncValues(bundle.algS, r2, r3, rows)
                bundle.uniformT = UniformDistribution.getUniDistrFuncValues(bundle.algT, r4, r5, rows)
                bundle.uniformTableF = UniformDistribution.getUniDistrFuncValues(bundle.tableF, r0, r1, rows)
                bundle.uniformTableS = UniformDistribution.getUniDistrFuncValues(bundle.tableS, r2, r3, rows)
                bundle.uniformTableT = UniformDistribution.getUniDistrFuncValues(bundle.tableT, r4, r5, rows)
                def kolmogorov = new Kolmogorov()
                bundle.statF = kolmogorov.statFunc(bundle.algF, r0, r1, rows)
                bundle.statS = kolmogorov.statFunc(bundle.algS, r2, r3, rows)
                bundle.statT = kolmogorov.statFunc(bundle.algT, r4, r5, rows)
                bundle.statTableF = kolmogorov.statFunc(bundle.tableF, r0, r1, rows)
                bundle.statTableS = kolmogorov.statFunc(bundle.tableS, r2, r3, rows)
                bundle.statTableT = kolmogorov.statFunc(bundle.tableT, r4, r5, rows)
                PRNGCheckFrame.show bundle
            }
        }
    }

    private JComponent createGridPanel() {
        def panel = new JPanel(new GridBagLayout())
        def c = new GridBagConstraints()

        def label = new JLabel('Ranges')
        c.fill = GridBagConstraints.HORIZONTAL
        c.gridx = 1
        c.gridy = 0
        c.gridwidth = 3
        panel.add label, c

        def lab0 = new JLabel('0-10: ')
        c.gridx = 0
        c.gridy = 1
        c.weightx = 0.5
        c.gridwidth = 1
        panel.add lab0, c

        tf0 = new JTextField()
        c.gridx = 1
        panel.add tf0, c

        tf1 = new JTextField()
        c.gridx = 2
        panel.add tf1, c

        def lab1 = new JLabel('10-100: ')
        c.gridx = 0
        c.gridy = 2
        panel.add lab1, c

        tf2 = new JTextField()
        c.gridx = 1
        panel.add tf2, c

        tf3 = new JTextField()
        c.gridx = 2
        panel.add tf3, c

        def lab2 = new JLabel('100-1000: ')
        c.gridx = 0
        c.gridy = 3
        panel.add lab2, c

        tf4 = new JTextField()
        c.gridx = 1
        panel.add tf4, c

        tf5 = new JTextField()
        c.gridx = 2
        panel.add tf5, c

        panel
    }

    private JScrollPane createTable( Object[][] data, String[] columnNames) {
        def table = data != null && columnNames != null ? new JTable(data, columnNames) : new JTable()
        table.setFillsViewportHeight true
        new JScrollPane(table)
    }

    @Override
    protected void fillProps() {
        super.fillProps()
        title = LabType.PRNG.getProperty("labName") as String
        size = new Dimension(1280, 900)
    }
}
