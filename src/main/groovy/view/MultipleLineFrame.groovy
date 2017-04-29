package view

import controller.multipleline.MultipleLineController
import model.singleline.Order
import model.singleline.OrderActions

import javax.swing.BoxLayout
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JSpinner
import javax.swing.SpinnerModel
import javax.swing.SpinnerNumberModel
import java.awt.Dimension
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.event.ActionEvent
import java.awt.event.ActionListener


/**
 * Created by chist on 19.04.2017.
 */
class MultipleLineFrame extends SingleLineFrame{
    private JPanel lineViewerPanel2
    private JSpinner maxLineSizeSpinner1, maxLineSizeSpinner2,
            generatorMinDelaySpinner, generatorMaxDelaySpinner,
            producerMinDelaySpinner1, producerMaxDelaySpinner1,
            producerMinDelaySpinner2, producerMaxDelaySpinner2,
            producerMinDelaySpinner3, producerMaxDelaySpinner3,
            consumerMinDelaySpinner1, consumerMaxDelaySpinner1,
            consumerMinDelaySpinner2, consumerMaxDelaySpinner2
    private MultipleLineController controller

    @Override
    protected void createContent() {
        def panel = new JPanel()
        panel.layout = new BoxLayout(panel, BoxLayout.PAGE_AXIS)
        panel.add createControls()
        panel.add lineViewerPanel1 = createLineViewer(lineViewerPanel1)
        panel.add lineViewerPanel2 = createLineViewer(lineViewerPanel2)
        panel.add createLogViewer()
        panel.add createStatisticsViewer()
        contentPane = panel
    }

    @Override
    protected JPanel createControls() {
        def panel = new JPanel()
        panel.layout = new BoxLayout(panel, BoxLayout.LINE_AXIS)
        panel.add createCommonGrid()
        panel.add createProducersGrid()
        panel.add createConsumersGrid()
        addStartAndStopButtons(panel)
        panel
    }

    private JPanel createConsumersGrid() {
        def panel = new JPanel(new GridBagLayout())
        def c = new GridBagConstraints()

        def consumerMinDelayLabel1 = new JLabel('First computer min delay')
        c.fill = GridBagConstraints.HORIZONTAL
        c.gridx = 0
        c.gridy = 0
        c.gridwidth = 1
        panel.add consumerMinDelayLabel1, c

        SpinnerModel sm = new SpinnerNumberModel(1.0 as double, 1.0 as double, 20.0 as double, 0.5 as double)
        consumerMinDelaySpinner1 = new JSpinner(sm)
        c.gridx = 1
        panel.add consumerMinDelaySpinner1, c

        def consumerMaxDelayLabel1 = new JLabel('First computer max delay')
        c.gridx = 0
        c.gridy = 1
        panel.add consumerMaxDelayLabel1, c

        sm = new SpinnerNumberModel(4.0 as double, 1.0 as double, 20.0 as double, 0.5 as double)
        consumerMaxDelaySpinner1 = new JSpinner(sm)
        c.gridx = 1
        panel.add consumerMaxDelaySpinner1, c

        def consumerMinDelayLabel2 = new JLabel('Second computer min delay')
        c.gridx = 0
        c.gridy = 2
        panel.add consumerMinDelayLabel2, c

        sm = new SpinnerNumberModel(1.0 as double, 1.0 as double, 20.0 as double, 0.5 as double)
        consumerMinDelaySpinner2 = new JSpinner(sm)
        c.gridx = 1
        panel.add consumerMinDelaySpinner2, c

        def consumerMaxDelayLabel2 = new JLabel('Second computer max delay')
        c.gridx = 0
        c.gridy = 3
        panel.add consumerMaxDelayLabel2, c

        sm = new SpinnerNumberModel(4.0 as double, 1.0 as double, 20.0 as double, 0.5 as double)
        consumerMaxDelaySpinner2 = new JSpinner(sm)
        c.gridx = 1
        panel.add consumerMaxDelaySpinner2, c

        panel
    }

    private JPanel createProducersGrid() {
        def panel = new JPanel(new GridBagLayout())
        def c = new GridBagConstraints()

        def producerMinDelayLabel1 = new JLabel('First operator min delay')
        c.fill = GridBagConstraints.HORIZONTAL
        c.gridx = 0
        c.gridy = 0
        c.gridwidth = 1
        panel.add producerMinDelayLabel1, c

        SpinnerModel sm = new SpinnerNumberModel(1.0 as double, 1.0 as double, 20.0 as double, 0.5 as double)
        producerMinDelaySpinner1 = new JSpinner(sm)
        c.gridx = 1
        panel.add producerMinDelaySpinner1, c

        def producerMaxDelayLabel1 = new JLabel('First operator max delay')
        c.gridx = 0
        c.gridy = 1
        panel.add producerMaxDelayLabel1, c

        sm = new SpinnerNumberModel(4.0 as double, 1.0 as double, 20.0 as double, 0.5 as double)
        producerMaxDelaySpinner1 = new JSpinner(sm)
        c.gridx = 1
        panel.add producerMaxDelaySpinner1, c

        def producerMinDelayLabel2 = new JLabel('Second operator min delay')
        c.gridx = 0
        c.gridy = 2
        panel.add producerMinDelayLabel2, c

        sm = new SpinnerNumberModel(1.0 as double, 1.0 as double, 20.0 as double, 0.5 as double)
        producerMinDelaySpinner2 = new JSpinner(sm)
        c.gridx = 1
        panel.add producerMinDelaySpinner2, c

        def producerMaxDelayLabel2 = new JLabel('Second operator max delay')
        c.gridx = 0
        c.gridy = 3
        panel.add producerMaxDelayLabel2, c

        sm = new SpinnerNumberModel(4.0 as double, 1.0 as double, 20.0 as double, 0.5 as double)
        producerMaxDelaySpinner2 = new JSpinner(sm)
        c.gridx = 1
        panel.add producerMaxDelaySpinner2, c

        def producerMinDelayLabel3 = new JLabel('Third operator min delay')
        c.gridx = 0
        c.gridy = 4
        panel.add producerMinDelayLabel3, c

        sm = new SpinnerNumberModel(1.0 as double, 1.0 as double, 20.0 as double, 0.5 as double)
        producerMinDelaySpinner3 = new JSpinner(sm)
        c.gridx = 1
        panel.add producerMinDelaySpinner3, c

        def producerMaxDelayLabel3 = new JLabel('Third operator max delay')
        c.gridx = 0
        c.gridy = 5
        panel.add producerMaxDelayLabel3, c

        sm = new SpinnerNumberModel(4.0 as double, 1.0 as double, 20.0 as double, 0.5 as double)
        producerMaxDelaySpinner3 = new JSpinner(sm)
        c.gridx = 1
        panel.add producerMaxDelaySpinner3, c

        panel
    }

    private JPanel createCommonGrid() {
        def panel = new JPanel(new GridBagLayout())
        def c = new GridBagConstraints()

        def generatorMinDelayLabel = new JLabel('Generator min delay')
        c.fill = GridBagConstraints.HORIZONTAL
        c.gridx = 0
        c.gridy = 0
        c.gridwidth = 1
        panel.add generatorMinDelayLabel, c

        SpinnerModel sm = new SpinnerNumberModel(1, 1, 20, 1)
        generatorMinDelaySpinner = new JSpinner(sm)
        c.gridx = 1
        panel.add generatorMinDelaySpinner, c

        def generatorMaxDelayLabel = new JLabel('Generator max delay')
        c.gridx = 0
        c.gridy = 1
        panel.add generatorMaxDelayLabel, c

        sm = new SpinnerNumberModel(4, 1, 20, 1)
        generatorMaxDelaySpinner = new JSpinner(sm)
        c.gridx = 1
        panel.add generatorMaxDelaySpinner, c

        def maxLineSizeLabel1 = new JLabel('First line max size')
        c.gridx = 0
        c.gridy = 2
        panel.add maxLineSizeLabel1, c

        sm = new SpinnerNumberModel(1, 1, 100, 1)
        maxLineSizeSpinner1 = new JSpinner(sm)
        c.gridx = 1
        panel.add maxLineSizeSpinner1, c

        def maxLineSizeLabel2 = new JLabel('Second line max size')
        c.gridx = 0
        c.gridy = 3
        panel.add maxLineSizeLabel2, c

        sm = new SpinnerNumberModel(1, 1, 100, 1)
        maxLineSizeSpinner2 = new JSpinner(sm)
        c.gridx = 1
        panel.add maxLineSizeSpinner2, c

        panel
    }

    @Override
    protected ActionListener createStartButtonListener() {
        new ActionListener() {
            @Override
            void actionPerformed(ActionEvent e) {
                MultipleLineBundle bundle = new MultipleLineBundle()
                bundle.generatorMinDelay = getSpinnerValue(generatorMinDelaySpinner) as int
                bundle.generatorMaxDelay = getSpinnerValue(generatorMaxDelaySpinner) as int
                bundle.lineMaxSize1 = getSpinnerValue(maxLineSizeSpinner1) as int
                bundle.lineMaxSize2 = getSpinnerValue(maxLineSizeSpinner2) as int
                bundle.producerMinDelay1 = getSpinnerValue(producerMinDelaySpinner1) as double
                bundle.producerMinDelay2 = getSpinnerValue(producerMinDelaySpinner2) as double
                bundle.producerMinDelay3 = getSpinnerValue(producerMinDelaySpinner3) as double
                bundle.producerMaxDelay1 = getSpinnerValue(producerMaxDelaySpinner1) as double
                bundle.producerMaxDelay2 = getSpinnerValue(producerMaxDelaySpinner2) as double
                bundle.producerMaxDelay3 = getSpinnerValue(producerMaxDelaySpinner3) as double
                bundle.consumerMinDelay1 = getSpinnerValue(consumerMinDelaySpinner1) as double
                bundle.consumerMinDelay2 = getSpinnerValue(consumerMinDelaySpinner2) as double
                bundle.consumerMaxDelay1 = getSpinnerValue(consumerMaxDelaySpinner1) as double
                bundle.consumerMaxDelay2 = getSpinnerValue(consumerMaxDelaySpinner2) as double
                controller = new MultipleLineController(bundle, this as Observer)
                controller.start()
            }
        }
    }

    @Override
    protected ActionListener createStopButtonListener() {
        new ActionListener() {
            @Override
            void actionPerformed(ActionEvent e) {
                if(controller != null){
                    controller.stopWork()
                }
            }
        }
    }

    protected LinkedHashMap<Integer, Order> orders1 = new LinkedHashMap<>()

    @Override
    void update(Observable o, Object arg) {
        ArrayList<Object> props = arg as ArrayList<Object>
        OrderActions action = props[0] as OrderActions
        switch (action) {
            case OrderActions.NEW_ORDER:
                ArrayList<Order> ordersList = props[1] as ArrayList<Order>
                String lineName = props[2]
                if(lineName == MultipleLineController.line1Name){
                    updateLineViewTable(orders, lineViewerPanel1)
                    orders.clear()
                    for (Order order in ordersList) {
                        orders.put(order.id, order)
                    }
                }
                else {
                    updateLineViewTable(orders1, lineViewerPanel2)
                    orders1.clear()
                    for (Order order in ordersList) {
                        orders1.put(order.id, order)
                    }
                }
                break
            case OrderActions.IN_PROGRESS:
                updateLineViewTable(orders, lineViewerPanel1)
                updateLineViewTable(orders1, lineViewerPanel2)
                break
            case OrderActions.LOG:
                printLogLine(props[1] as String)
                break
            case OrderActions.RELEASE_ORDER:
                orders.remove(((Order)props[1]).id)
                orders1.remove(((Order)props[1]).id)
                updateStatViewTable()
                break
        }
    }

    @Override
    protected void fillProps() {
        super.fillProps()
        title = LabType.MULTIPLE_LINE.getProperty("labName") as String
        size = new Dimension(1024, 768)
    }
}
