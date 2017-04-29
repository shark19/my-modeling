package view

import com.sun.istack.internal.Nullable
import math.Common
import math.StateGraphMath
import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartPanel
import org.jfree.chart.JFreeChart
import org.jfree.chart.plot.Plot
import org.jfree.chart.plot.PlotOrientation
import org.jfree.chart.plot.PlotRenderingInfo
import org.jfree.chart.plot.PlotState
import org.jfree.data.xy.XYDataset
import org.jfree.data.xy.XYSeries
import org.jfree.data.xy.XYSeriesCollection
import view.components.HintTextField

import javax.swing.BorderFactory
import javax.swing.BoxLayout
import javax.swing.JButton
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.JTable
import java.awt.Dimension
import java.awt.Graphics2D
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.geom.Point2D
import java.awt.geom.Rectangle2D

/**
 * Created by chist on 19.04.2017.
 */
class StateGraphFrame extends CommonFrame{
    private JPanel tablePanel, graphPanel
    private HintTextField tf

    @Override
    protected void createContent() {
        def panel = new JPanel()
        panel.layout = new BoxLayout(panel, BoxLayout.PAGE_AXIS)
        panel.add createButtonsPanel()
        tablePanel = new JPanel()
        tablePanel.layout = new BoxLayout(tablePanel, BoxLayout.PAGE_AXIS)
        tablePanel.add new JScrollPane(new JTable())
        panel.add tablePanel
        graphPanel = new JPanel()
        graphPanel.layout = new BoxLayout(graphPanel, BoxLayout.PAGE_AXIS)
        graphPanel.add new ChartPanel(new JFreeChart(new Plot() {
            @Override
            String getPlotType() {
                return null
            }

            @Override
            void draw(Graphics2D graphics2D, Rectangle2D rectangle2D, Point2D point2D, PlotState plotState, PlotRenderingInfo plotRenderingInfo) {

            }
        }))
        panel.add graphPanel
        contentPane = panel
    }

    private void updateChart(double[][] pointOfTime) {
        def chart = ChartFactory.createXYLineChart(
                "Title",
                "x", "P(i)",
                createDataset(pointOfTime),
                PlotOrientation.VERTICAL,
                true, true, false)
        chart.getXYPlot().domainAxis.setRange(-0.025, 0.1)
        ChartPanel panel = new ChartPanel(chart)
        graphPanel.clear()
        graphPanel.add panel
        graphPanel.validate()
        graphPanel.repaint()
    }

    private XYDataset createDataset(double[][] pointOfTime) {
        def dataSet = new XYSeriesCollection()
        for(int i = 0; i < pointOfTime.length; i++){
            final XYSeries series = new XYSeries( 'Points_'.concat(String.valueOf(i+1)) )
            for(int j = 0; j < pointOfTime[i].length; j++) {
                series.add i.toDouble() / 100.0, pointOfTime[i][j]
            }
            dataSet.addSeries series
        }
        dataSet
    }

    private JScrollPane createTable(@Nullable Object[][] data, @Nullable String[] columnNames) {
        def table = data != null && columnNames != null ? new JTable(data, columnNames) : new JTable()
        table.setFillsViewportHeight true
        new JScrollPane(table)
    }

    private JPanel createButtonsPanel() {
        def panel = new JPanel()
        panel.layout = new BoxLayout(panel, BoxLayout.LINE_AXIS)
        tf = new HintTextField('Matrix size')
        tf.maximumSize = new Dimension(60, 20)
        panel.add tf
        def b1 = new JButton('Assemble')
        b1.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(getBackground(), 15),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)))
        b1.addActionListener(createB1Listener())
        panel.add b1
        panel
    }

    private ActionListener createB1Listener() {
        new ActionListener() {
            @Override
            void actionPerformed(ActionEvent e) {
                if (tf.text.isNumber() && (tf.text as int) > 0 && (tf.text as int) < 10) {
                    ArrayList<ArrayList<Integer>> list = new ArrayList()
                    for(int i = 0; i < (tf.text as int); i++){
                        list.add(Common.getRandoms((tf.text as int), 1, 10))
                    }
                    double[] solve, time
                    double[][] pointOfTime
                    (solve, time, pointOfTime) = StateGraphMath.getPAndT(list)
                    upateMatrix(tf.text as int, list, solve, time)
                    updateChart(pointOfTime)
                }
            }

            private void upateMatrix(int size, ArrayList<ArrayList<Integer>> list, double[] solve, double[] time) {
                String[] columnNames = createColumnNames(size)
                def data = new Object[size][columnNames.length]

                for(int i = 0; i < size; i++){
                    data[i][0] = 'R'.concat((i+1).toString())
                    for(int j = 1; j <= size; j++){
                        data[i][j] = list.get(i).get(j-1)
                    }
                    data[i][size+1] = solve[i]
                    data[i][size+2] = time[i]
                }
                tablePanel.clear()
                tablePanel.add createTable(data, columnNames)
                tablePanel.validate()
                tablePanel.repaint()
            }

            private String[] createColumnNames(int size) {
                String[] rowNames = new String[1 + size + 2]
                rowNames[0] = ''
                for (int i = 1; i <= size; i++) {
                    rowNames[i] = 'C'.concat(i as String)
                }
                rowNames[size + 1] = 'P(i)'
                rowNames[size + 2] = 't'
                rowNames
            }
        }
    }

    @Override
    protected void fillProps() {
        super.fillProps()
        title = LabType.STATE_GRAPH.getProperty("labName") as String
        size = new Dimension(1024, 768)
    }
}
