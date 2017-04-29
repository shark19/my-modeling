package view

import math.Kolmogorov
import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartPanel
import org.jfree.chart.plot.PlotOrientation
import org.jfree.data.xy.XYDataset
import org.jfree.data.xy.XYSeries
import org.jfree.data.xy.XYSeriesCollection

import javax.swing.BorderFactory
import javax.swing.BoxLayout
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.JTable
import javax.swing.border.TitledBorder
import java.awt.Dimension
import java.awt.Font

/**
 * Created by chist on 13.04.2017.
 */
class PRNGCheckFrame extends CommonFrame{
    private static PRNGBundle bundle

    static void show(PRNGBundle bundle){
        this.bundle = bundle
        new PRNGCheckFrame().setVisible true
    }

    @Override
    protected void createContent() {
        def panel = new JPanel()
        panel.layout = new BoxLayout(panel, BoxLayout.LINE_AXIS)
        def algData = new Object[2][PRNGFrame.columnNames.length]
        def kolmogorov = new Kolmogorov()
        algData[0][0] = '\u03BB'
        algData[0][1] = kolmogorov.getLambda(bundle.statF, bundle.uniformF, bundle.statF.size())
        algData[0][2] = kolmogorov.getLambda(bundle.statS, bundle.uniformS, bundle.statS.size())
        algData[0][3] = kolmogorov.getLambda(bundle.statT, bundle.uniformT, bundle.statT.size())
        algData[1][0] = "P(${'\u03BB'})"
        for (int i = 1; i < 4; i++) {
            algData[1][i] = kolmogorov.getProb(algData[0][i] as double)
        }
        panel.add createTable('Algorithmic', algData, PRNGFrame.columnNames)
        panel.add createGraphics()
        def tabData = new Object[2][PRNGFrame.columnNames.length]
        tabData[0][0] = '\u03BB'
        tabData[1][0] = "P(${'\u03BB'})"
        tabData[0][1] = kolmogorov.getLambda(bundle.statTableF, bundle.uniformTableF, bundle.statTableF.size())
        tabData[0][2] = kolmogorov.getLambda(bundle.statTableS, bundle.uniformTableS, bundle.statTableS.size())
        tabData[0][3] = kolmogorov.getLambda(bundle.statTableT, bundle.uniformTableT, bundle.statTableT.size())
        for (int i = 1; i < 4; i++) {
            tabData[1][i] = kolmogorov.getProb(tabData[0][i] as double)
        }
        panel.add createTable('Table', tabData, PRNGFrame.columnNames)
        contentPane = panel
    }

    private JScrollPane createTable(String title, Object[][] data, String[] rowNames) {
        def table = new JTable(data, rowNames)
        table.setFillsViewportHeight true
        def scrollPane = new JScrollPane(table)
        scrollPane.setBorder (BorderFactory.createTitledBorder (BorderFactory.createEtchedBorder (),
                title,
                TitledBorder.CENTER,
                TitledBorder.TOP,
                new Font("Serif", Font.BOLD, 18)))
        scrollPane
    }

    private JPanel createGraphics() {
        def panel = new JPanel()
        panel.layout = new BoxLayout(panel, BoxLayout.PAGE_AXIS)
        panel.add createGraphic(bundle.algF, bundle.uniformF, bundle.statF, bundle.tableF, bundle.statTableF)
        panel.add createGraphic(bundle.algS, bundle.uniformS, bundle.statS, bundle.tableS, bundle.statTableS)
        panel.add createGraphic(bundle.algT, bundle.uniformT, bundle.statT, bundle.tableT, bundle.statTableT)
        panel
    }

    private ChartPanel createGraphic(ArrayList<Double>... lists) {
        new ChartPanel(ChartFactory.createXYLineChart(
                "Title",
                "x", "y",
                createDataset(lists),
                PlotOrientation.VERTICAL,
                true, true, false))
    }

    private XYDataset createDataset(ArrayList<Double>... lists) {
        def dataSet = new XYSeriesCollection()
        final XYSeries uniSeries = new XYSeries( 'Uni' )
        for(int i = 0; i < lists[0].size(); i++){
            uniSeries.add(lists[0].get(i), lists[1].get(i))
        }
        final XYSeries algSeries = new XYSeries( 'Alg' )
        for(int i = 0; i < lists[0].size(); i++){
            algSeries.add(lists[0].get(i), lists[2].get(i))
        }
        final XYSeries tabSeries = new XYSeries( 'Tab' )
        for(int i = 0; i < lists[0].size(); i++){
            tabSeries.add(lists[3].get(i), lists[4].get(i))
        }
        dataSet.addSeries(uniSeries)
        dataSet.addSeries(algSeries)
        dataSet.addSeries(tabSeries)
        dataSet
    }

    @Override
    protected void fillProps() {
        super.fillProps()
        title = 'Pseudorandom number generator checker'
        size = new Dimension(1280, 900)
    }
}
