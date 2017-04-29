package view

import controller.singleline.SingleLineController
import model.singleline.Order
import model.singleline.OrderActions
import model.singleline.OrderStatus
import model.singleline.OrdersArchive

import javax.swing.*
import java.awt.*
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.text.ParseException

/**
 * Created by chist on 22.04.2017.
 */
class SingleLineFrame extends CommonFrame implements Observer{
    static final String[] STAT_COLUMN_NAMES = ["All", "Passed", "Lost", "Lost percent"] as String[]
    static final String[] LINE_GRID_COLUMN_NAMES = ['№', 'id', 'Create time', 'Status'] as String[]
    private SingleLineController controller
    private JSpinner maxLineSizeSpinner, producerMinDelaySpinner, producerMaxDelaySpinner, consumerMinDelaySpinner, consumerMaxDelaySpinner
    protected static TextArea logTextArea
    protected JPanel lineViewerPanel1, statViewerPanel

    @Override
    protected void createContent() {
        def panel = new JPanel()
        panel.layout = new BoxLayout(panel, BoxLayout.PAGE_AXIS)
        panel.add createControls()
        panel.add lineViewerPanel1 = createLineViewer(lineViewerPanel1)
        panel.add createLogViewer()
        panel.add createStatisticsViewer()
        contentPane = panel
    }

    protected JScrollPane createTable(Object[][] data, String[] columnNames){
        def table = new JTable(data, columnNames)
        table.setFillsViewportHeight true
        new JScrollPane(table)
    }

    protected JPanel createLogViewer() {
        def panel = new JPanel()
        panel.layout = new BoxLayout(panel, BoxLayout.LINE_AXIS)
        panel.add logTextArea = new TextArea()
        panel
    }

    protected JPanel createControls() {
        def panel = new JPanel()
        panel.layout = new BoxLayout(panel, BoxLayout.LINE_AXIS)
        panel.add createControlGrid()
        addStartAndStopButtons(panel)
        panel
    }

    protected void addStartAndStopButtons(JPanel panel) {
        def startButton = new JButton('Start')
        startButton.addActionListener(createStartButtonListener())
        panel.add startButton
        def stopButton = new JButton('Stop')
        stopButton.addActionListener(createStopButtonListener())
        panel.add stopButton
    }

    protected JPanel createControlGrid() {
        def panel = new JPanel(new GridBagLayout())
        def c = new GridBagConstraints()

        def maxLineSizeLabel = new JLabel('Max line size')
        c.fill = GridBagConstraints.HORIZONTAL
        c.gridx = 0
        c.gridy = 0
        c.gridwidth = 1
        panel.add maxLineSizeLabel, c

        SpinnerModel sm = new SpinnerNumberModel(1, 1, 100, 1)
        maxLineSizeSpinner = new JSpinner(sm)
        c.gridx = 1
        panel.add maxLineSizeSpinner, c

        def producerMinDelayLabel = new JLabel('Producer min delay')
        c.gridx = 0
        c.gridy = 1
        panel.add producerMinDelayLabel, c

        sm = new SpinnerNumberModel(1.0 as double, 1.0 as double, 10.0 as double, 0.5 as double)
        producerMinDelaySpinner = new JSpinner(sm)
        c.gridx = 1
        panel.add producerMinDelaySpinner, c

        def producerMaxDelayLabel = new JLabel('Producer max delay')
        c.gridx = 0
        c.gridy = 2
        panel.add producerMaxDelayLabel, c

        sm = new SpinnerNumberModel(2.0 as double, 1.0 as double, 10.0 as double, 0.5 as double)
        producerMaxDelaySpinner = new JSpinner(sm)
        c.gridx = 1
        panel.add producerMaxDelaySpinner, c

        def consumerMinDelayLabel = new JLabel('Consumer min delay')
        c.gridx = 0
        c.gridy = 3
        panel.add consumerMinDelayLabel, c

        sm = new SpinnerNumberModel(1.0 as double, 1.0 as double, 10.0 as double, 0.5 as double)
        consumerMinDelaySpinner = new JSpinner(sm)
        c.gridx = 1
        panel.add consumerMinDelaySpinner, c

        def consumerMaxDelayLabel = new JLabel('Consumer max delay')
        c.gridx = 0
        c.gridy = 4
        panel.add consumerMaxDelayLabel, c

        sm = new SpinnerNumberModel(2.0 as double, 1.0 as double, 10.0 as double, 0.5 as double)
        consumerMaxDelaySpinner = new JSpinner(sm)
        c.gridx = 1
        panel.add consumerMaxDelaySpinner, c

        panel
    }

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

    protected ActionListener createStartButtonListener() {
        new ActionListener() {
            @Override
            void actionPerformed(ActionEvent e) {
                SingleLineBundle bundle = new SingleLineBundle()
                bundle.lineMaxSize = getSpinnerValue(maxLineSizeSpinner) as int
                bundle.producerMaxDelay = getSpinnerValue(producerMaxDelaySpinner) as double
                bundle.producerMinDelay = getSpinnerValue(producerMinDelaySpinner) as double
                bundle.consumerMaxDelay = getSpinnerValue(consumerMaxDelaySpinner) as double
                bundle.consumerMinDelay = getSpinnerValue(consumerMinDelaySpinner) as double
                controller = new SingleLineController(bundle, this as Observer)
                controller.start()
            }
        }
    }

    protected Object getSpinnerValue(JSpinner spinner){
        try {
            spinner.commitEdit()
        } catch ( ParseException ignored ){}
        spinner.getValue()
    }

    @Override
    protected void fillProps() {
        super.fillProps()
        title = LabType.SINGLE_LINE.getProperty("labName") as String
        size = new Dimension(800, 600)
    }

    protected LinkedHashMap<Integer, Order> orders = new LinkedHashMap<>()

    @Override
    void update(Observable o, Object arg) {
        ArrayList<Object> props = arg as ArrayList<Object>
        OrderActions action = props[0] as OrderActions
        switch (action) {
            case OrderActions.NEW_ORDER:
                ArrayList<Order> ordersList = props[1] as ArrayList<Order>
                orders.clear()
                for(Order order in ordersList){
                    orders.put(order.id, order)
                }
                updateLineViewTable(orders, lineViewerPanel1)
                break
            case OrderActions.IN_PROGRESS:
                updateLineViewTable(orders, lineViewerPanel1)
                break
            case OrderActions.LOG:
                printLogLine(props[1] as String)
                break
            case OrderActions.RELEASE_ORDER:
                updateStatViewTable()
                break
        }
    }

    static void printLogLine(String text){
        logTextArea.append(text.concat('\n'))
    }

    protected void updateStatViewTable() {
        statViewerPanel.clear()
        def data = new Object[1][STAT_COLUMN_NAMES.length]
        data[0][0] = OrdersArchive.getInstance().getOrdersCount()
        data[0][1] = OrdersArchive.getInstance().getCountByStatus(OrderStatus.PASSED)
        data[0][2] = OrdersArchive.getInstance().getCountByStatus(OrderStatus.LOST)
        data[0][3] = OrdersArchive.getInstance().getResultStatusDiff()
        statViewerPanel.add createTable(data, STAT_COLUMN_NAMES)
        statViewerPanel.validate()
        statViewerPanel.repaint()
    }

    protected JPanel createStatisticsViewer() {
        statViewerPanel = new JPanel()
        def data = new Object[1][STAT_COLUMN_NAMES.length]
        for(int i = 0; i < STAT_COLUMN_NAMES.length; i++){
            data[0][i] = 0
        }
        statViewerPanel.add createTable(data, STAT_COLUMN_NAMES)
        statViewerPanel
    }

    protected JPanel updateLineViewTable(LinkedHashMap<Integer, Order> orders, JPanel liveViewerPanel) {
        if(orders != null && !orders.isEmpty()) {
            liveViewerPanel.clear()
            def data = new Object[orders.size()][LINE_GRID_COLUMN_NAMES.length]
            Set<Integer> set = orders.keySet()
            for (int i = 0; i < set.size(); i++) {
                data[i][0] = (i + 1) as int
                data[i][1] = orders.get(set[i]).id
                data[i][2] = orders.get(set[i]).createTime.toString()
                data[i][3] = orders.get(set[i]).status.name()
            }
            liveViewerPanel.add createTable(data, LINE_GRID_COLUMN_NAMES)
            liveViewerPanel.validate()
            liveViewerPanel.repaint()
        }
    }

    protected JPanel createLineViewer(JPanel lineViewerPanel) {
        lineViewerPanel = new JPanel()
        def data = new Object[1][LINE_GRID_COLUMN_NAMES.length]
        data[0][0] = 0
        data[0][1] = 0
        data[0][2] = 'null'
        data[0][3] = 'null'
        lineViewerPanel.add createTable(data, LINE_GRID_COLUMN_NAMES)
        lineViewerPanel
    }
}
