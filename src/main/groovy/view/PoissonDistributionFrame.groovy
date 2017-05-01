package view

import math.PoissonDistribution
import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartPanel
import org.jfree.chart.plot.PlotOrientation
import org.jfree.data.xy.XYDataset
import org.jfree.data.xy.XYSeries
import org.jfree.data.xy.XYSeriesCollection

import javax.swing.*
import java.awt.event.ActionEvent
import java.awt.event.ActionListener

/**
 * Created by chist on 2/26/17.
 */

class PoissonDistributionFrame extends CommonFrame{
    private JTextField textField
    private ChartPanel distrFunc
    private ChartPanel probFunc

    @Override
    protected void createContent() {
        def panel = new JPanel()
        panel.layout = new BoxLayout(panel, BoxLayout.PAGE_AXIS)
        if(distrFunc == null) {
            distrFunc = createDistrFunc(["1"] as HashSet<String>)
        }
        panel.add distrFunc
        if(probFunc == null) {
            probFunc = createProbFunc(["1"] as HashSet<String>)
        }
        panel.add probFunc
        panel.add createTextField()
        panel.add createButton()
        contentPane = panel
    }

    @Override
    protected void fillProps() {
        super.fillProps()
        title = LabType.POISSON_DISTRIBUTION.getProperty("labName") as String
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
                def lambdaSet = Arrays.asList(textField.text.split(" ")) as HashSet<String>
                distrFunc.chart.getXYPlot().setDataset(createDataset(lambdaSet))
                probFunc.chart.getXYPlot().setDataset(createDataset2(lambdaSet))
            }
        }
    }

    private JTextField createTextField() {
        textField = new JTextField()
        textField.horizontalAlignment = JTextField.CENTER
        textField
    }

    private ChartPanel createDistrFunc(HashSet<String> lambdas) {
        new ChartPanel(ChartFactory.createXYLineChart(
                "Функция распределения",
                "x", "y",
                createDataset(lambdas),
                PlotOrientation.VERTICAL,
                true, true, false))
    }

    private ChartPanel createProbFunc(HashSet<String> lambdas) {
        new ChartPanel(ChartFactory.createXYLineChart(
                "Функция вероятности",
                "x", "y",
                createDataset2(lambdas),
                PlotOrientation.VERTICAL,
                true, true, false))
    }

    private XYDataset createDataset(HashSet<String> lambdas) {
        def dataSet = new XYSeriesCollection()
        lambdas.each { lambda ->
            if(lambda.isNumber()) {
                final XYSeries xySeries = new XYSeries( "poisson" + lambda )
                PoissonDistribution.poisson(Double.parseDouble(lambda)).each { pair ->
                    xySeries.add(pair.key, pair.value)
                }
                dataSet.addSeries(xySeries)
            }
        }
        dataSet
    }

    private XYDataset createDataset2(HashSet<String> lambdas) {
        def dataSet = new XYSeriesCollection()
        lambdas.each { lambda ->
            if(lambda.isNumber()) {
                final XYSeries xySeries = new XYSeries( "poisson" + lambda )
                PoissonDistribution.poisson1(Double.parseDouble(lambda)).each { pair ->
                    xySeries.add(pair.key, pair.value)
                }
                dataSet.addSeries(xySeries)
            }
        }
        dataSet
    }
}
