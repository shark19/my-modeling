package view

import math.UniformDistribution
import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartPanel
import org.jfree.chart.plot.PlotOrientation
import org.jfree.data.xy.XYDataset
import org.jfree.data.xy.XYSeries
import org.jfree.data.xy.XYSeriesCollection
import view.components.HintTextField

import javax.swing.BoxLayout
import javax.swing.JButton
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTextField
import java.awt.event.ActionEvent
import java.awt.event.ActionListener

/**
 * Created by chist on 5/1/17.
 */
class UniformDistrFrame extends CommonFrame{
    private JTextField textField1, textField2
    private ChartPanel distrFunc
    private ChartPanel probFunc
    private JLabel mWaiting
    private JLabel mDisp

    @Override
    protected void createContent() {
        def panel = new JPanel()
        panel.layout = new BoxLayout(panel, BoxLayout.PAGE_AXIS)
        if(distrFunc == null) {
            distrFunc = createDistrFunc()
        }
        panel.add distrFunc
        if(probFunc == null) {
            probFunc = createProbFunc()
        }
        panel.add probFunc
        panel.add createLabels()
        panel.add createTextField1()
        panel.add createTextField2()
        panel.add createButton()
        contentPane = panel
    }

    private JPanel createLabels(){
        def panel1 = new JPanel()
        panel1.layout = new BoxLayout(panel1, BoxLayout.PAGE_AXIS)
        def panel2 = new JPanel()
        panel2.layout = new BoxLayout(panel2, BoxLayout.LINE_AXIS)
        def panel3 = new JPanel()
        panel3.layout = new BoxLayout(panel3, BoxLayout.LINE_AXIS)

        panel2.add new JLabel('Ожидание:    ')
        panel2.add mWaiting = new JLabel(UniformDistribution.mWaiting(1, 10) as String)
        panel3.add new JLabel('Дисперсия:    ')
        panel3.add mDisp = new JLabel(UniformDistribution.mDisp(1, 10) as String)

        panel1.add panel2
        panel1.add panel3
        panel1
    }

    @Override
    protected void fillProps() {
        super.fillProps()
        title = LabType.UNIFORM_DISTR.getProperty("labName") as String
    }

    private JButton createButton() {
        def button = new JButton("Draw")
        button.addActionListener(createButtonListener())
        button.alignmentX = CENTER_ALIGNMENT
        button.addActionListener(createButtonListener())
        button
    }

    private ActionListener createButtonListener() {
        new ActionListener() {
            @Override
            void actionPerformed(ActionEvent e) {
                distrFunc.chart.getXYPlot().setDataset(createDataset())
                probFunc.chart.getXYPlot().setDataset(createDataset2())
                mWaiting.text = UniformDistribution.mWaiting(textField1.text as double, textField2.text as double) as String
                mDisp.text = UniformDistribution.mDisp(textField1.text as double, textField2.text as double) as String
            }
        }
    }

    private JTextField createTextField1() {
        textField1 = new HintTextField('from')
        textField1.horizontalAlignment = JTextField.CENTER
        textField1
    }

    private JTextField createTextField2() {
        textField2 = new HintTextField('to')
        textField2.horizontalAlignment = JTextField.CENTER
        textField2
    }

    private ChartPanel createDistrFunc() {
        new ChartPanel(ChartFactory.createXYLineChart(
                "Функция распределения",
                "x", "y",
                createDataset(),
                PlotOrientation.VERTICAL,
                true, true, false))
    }

    private ChartPanel createProbFunc() {
        new ChartPanel(ChartFactory.createXYLineChart(
                "Функция вероятности",
                "x", "y",
                createDataset2(),
                PlotOrientation.VERTICAL,
                true, true, false))
    }

    private XYDataset createDataset() {
        def dataSet = new XYSeriesCollection()
        double a = textField1 == null ? 1 : Double.parseDouble(textField1.text)
        double b = textField2 == null ? 10 : Double.parseDouble(textField2.text)
        final XYSeries xySeries = new XYSeries( "Uni:" + a + "-" + b)
        UniformDistribution.getDistr(a, b).each { pair ->
            xySeries.add(pair.key, pair.value)
        }
        dataSet.addSeries(xySeries)
        dataSet
    }

    private XYDataset createDataset2() {
        def dataSet = new XYSeriesCollection()
        double a = textField1 == null ? 1 : Double.parseDouble(textField1.text)
        double b = textField2 == null ? 10 : Double.parseDouble(textField2.text)
        final XYSeries xySeries = new XYSeries( "Uni:" + a + "-" + b)
        UniformDistribution.getProb(a, b).each { pair ->
            xySeries.add(pair.key, pair.value)
        }
        dataSet.addSeries(xySeries)
        dataSet
    }
}
