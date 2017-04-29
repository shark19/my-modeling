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
    private ChartPanel chartPanel

    @Override
    protected void createContent() {
        def panel = new JPanel()
        panel.layout = new BoxLayout(panel, BoxLayout.PAGE_AXIS)
        if(chartPanel == null) {
            chartPanel = createChartPanel(["1"] as HashSet<String>)
        }
        panel.add chartPanel
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
                chartPanel.chart.getXYPlot().setDataset(createDataset(lambdaSet))
            }
        }
    }

    private JTextField createTextField() {
        textField = new JTextField()
        textField.horizontalAlignment = JTextField.CENTER
        textField
    }

    private ChartPanel createChartPanel(HashSet<String> lambdas) {
        new ChartPanel(ChartFactory.createXYLineChart(
                "P(k)",
                "Number of events", "Probability",
                createDataset(lambdas),
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
}
