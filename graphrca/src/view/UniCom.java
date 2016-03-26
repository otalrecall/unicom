package view;

import ChartDirector.ChartViewer;
import controller.GenerateCSVController;
import controller.LoadCSVToGraphController;
import controller.SetNewGraphController;
import model.GraphData;
import org.apache.commons.lang.ArrayUtils;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class UniCom {

    private JFrame frame;
    private GridBagConstraints gridBagConstraints;
    private JMenuBar jMenuBar;
    private JButton loadCSVButton;
    private ChartViewer chartViewer;
    private JPanel chartViewerHiddenBarJPanel;
    private JPanel chartViewerJPanel;
    private JScrollPane entriesScrollPane;
    private JScrollPane graphicAreaScrollPane;
    private GraphData graphData;
    private JPanel selectOrderControlJPanel;
    private JRadioButton naturalOrderJRadioButton;
    private JRadioButton owaOrderJRadioButton;
    private JPanel controlPanelJPanel;
    private JPanel selectEntryControlJPanel;
    private JRadioButton referenceObjectJRadioButton;
    private JRadioButton compareObjectJRadioButton;
    private JLabel similarityLabel;
    private JPanel similarityFilterJPanel;
    private JScrollBar similarityFilterScrollBar;
    private JCheckBox similarityFilterJCheckBox;
    private JLabel similarityFilterJLabel;

    private LoadCSVToGraphController loadCSVToGraphController;
    private SetNewGraphController setNewGraphController;
    private GenerateCSVController generateCSVController;

    public UniCom() {
        loadCSVToGraphController = new LoadCSVToGraphController();
        setNewGraphController = new SetNewGraphController();
        generateCSVController = new GenerateCSVController();
        initComponents();
    }

    /**
     * Initializes all view components
     */
    private void initComponents() {
        frame = new JFrame();

        /**
         * Set main window configuration
         */
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("UniCom");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setMinimumSize(new Dimension(300, 300));
        frame.setLocationRelativeTo(null);
        frame.setLayout(new GridBagLayout());

        /**
         * Set program icon
         */
        java.net.URL url = ClassLoader.getSystemResource("resources/icon.png");
        Toolkit kit = Toolkit.getDefaultToolkit();
        Image icon = kit.createImage(url);
        frame.setIconImage(icon);

        initMenu();

        loadCSVButton = new JButton("Load CSV File");
        loadCSVButton.addActionListener( new CSVLoadDialog() );

        similarityLabel = new JLabel();
        similarityLabel.setToolTipText("Similarity");
        similarityLabel.setHorizontalAlignment(SwingConstants.CENTER);
        TitledBorder similarityTitleBorder = BorderFactory.createTitledBorder("Similarity");
        similarityTitleBorder.setTitleJustification(TitledBorder.CENTER);
        similarityLabel.setBorder( similarityTitleBorder );
        similarityLabel.setVisible(false);

        similarityFilterScrollBar = new JScrollBar(JScrollBar.HORIZONTAL, 0, 0, 0, 100);
        similarityFilterScrollBar.setPreferredSize( new Dimension(140, 20) );
        similarityFilterScrollBar.setToolTipText("Overshadows the object rows with a similarity percentage lower than " +
                "the selected one");
        BoundedRangeModel similarityScrollBarModel = similarityFilterScrollBar.getModel();
        similarityScrollBarModel.addChangeListener( new SimilarityScrollBarChangeListener() );

        similarityFilterJLabel = new JLabel("0%");
        similarityFilterJLabel.setPreferredSize( new Dimension(40, 20) );

        similarityFilterJCheckBox = new JCheckBox();
        similarityFilterJCheckBox.addActionListener( new SimilarityFilterJCheckboxListener() );

        naturalOrderJRadioButton = new JRadioButton("Natural");
        naturalOrderJRadioButton.setSelected(true);
        naturalOrderJRadioButton.addActionListener( new OrderSelectionRadioListener() );
        owaOrderJRadioButton = new JRadioButton("OWA");
        owaOrderJRadioButton.addActionListener( new OrderSelectionRadioListener() );

        ButtonGroup selectOrderButtonGroup = new ButtonGroup();
        selectOrderButtonGroup.add(naturalOrderJRadioButton);
        selectOrderButtonGroup.add(owaOrderJRadioButton);

        selectOrderControlJPanel = new JPanel();
        selectOrderControlJPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
        selectOrderControlJPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        selectOrderControlJPanel.setBorder(BorderFactory.createTitledBorder("Chart Axis Order"));
        selectOrderControlJPanel.add(naturalOrderJRadioButton);
        selectOrderControlJPanel.add(owaOrderJRadioButton);

        compareObjectJRadioButton = new JRadioButton("Compare Object");
        compareObjectJRadioButton.setSelected(true);
        referenceObjectJRadioButton = new JRadioButton("Reference Object");

        ButtonGroup selectEntryButtonGroup = new ButtonGroup();
        selectEntryButtonGroup.add(referenceObjectJRadioButton);
        selectEntryButtonGroup.add(compareObjectJRadioButton);

        selectEntryControlJPanel = new JPanel();
        selectEntryControlJPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
        selectEntryControlJPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        selectEntryControlJPanel.setBorder(BorderFactory.createTitledBorder("Item Selection"));
        selectEntryControlJPanel.add(compareObjectJRadioButton);
        selectEntryControlJPanel.add(referenceObjectJRadioButton);

        similarityFilterJPanel = new JPanel();
        similarityFilterJPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
        similarityFilterJPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        similarityFilterJPanel.setBorder(BorderFactory.createTitledBorder("Similarity Filter"));
        similarityFilterJPanel.add(similarityFilterJCheckBox);
        similarityFilterJPanel.add(similarityFilterScrollBar);
        similarityFilterJPanel.add(similarityFilterJLabel);

        controlPanelJPanel = new JPanel();
        controlPanelJPanel.setLayout(new BoxLayout(controlPanelJPanel, BoxLayout.Y_AXIS));
        controlPanelJPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        controlPanelJPanel.add(selectOrderControlJPanel);
        controlPanelJPanel.add(selectEntryControlJPanel);
        controlPanelJPanel.add(similarityFilterJPanel);
        controlPanelJPanel.setVisible(false);

        chartViewer = new ChartViewer();

        chartViewerHiddenBarJPanel = new JPanel();
        chartViewerHiddenBarJPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 20, 0, new Color(238, 238, 238)));
        chartViewerHiddenBarJPanel.setOpaque(false);

        chartViewerJPanel = new JPanel() {
            public boolean isOptimizedDrawingEnabled() {
                return false;
            }
        };
        LayoutManager overlay = new OverlayLayout(chartViewerJPanel);
        chartViewerJPanel.setLayout(overlay);

        chartViewerJPanel.add(chartViewerHiddenBarJPanel);
        chartViewerJPanel.add(chartViewer);
        chartViewerJPanel.setVisible(false);

        graphicAreaScrollPane = new JScrollPane();
        graphicAreaScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        graphicAreaScrollPane.setVisible(false);

        entriesScrollPane = new JScrollPane();
        entriesScrollPane.setPreferredSize(new Dimension(250, 40));
        entriesScrollPane.setMinimumSize(new Dimension(200, 40));
        entriesScrollPane.setVisible(false);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(10, 10, 10, 10);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        frame.add(entriesScrollPane, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        frame.add(controlPanelJPanel, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 1;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.fill = GridBagConstraints.NONE;
        frame.add(loadCSVButton, gridBagConstraints);
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.NORTH;
        frame.add(graphicAreaScrollPane, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        frame.add(similarityLabel, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        frame.add(chartViewerJPanel, gridBagConstraints);
        frame.setVisible(true);
    }

    /**
     * Initializes the menu bar with all its components
     */
    private void initMenu() {
        jMenuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        jMenuBar.add(fileMenu);

        JMenuItem load = new JMenuItem("Load CSV", KeyEvent.VK_L);
        load.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
        load.addActionListener(new CSVLoadDialog());
        fileMenu.add(load);

        JMenuItem generate = new JMenuItem("Generate Results...", KeyEvent.VK_G);
        generate.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.ALT_MASK));
        generate.addActionListener(new GenerateResults());
        generate.setEnabled(false);
        fileMenu.add(generate);

        JMenuItem quit = new JMenuItem("Quit UniCom", KeyEvent.VK_Q);
        quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3, ActionEvent.ALT_MASK));
        quit.addActionListener(new QuitUniCom());
        fileMenu.add(quit);

        JMenu helpMenu = new JMenu("Help");
        helpMenu.setMnemonic(KeyEvent.VK_H);
        jMenuBar.add(helpMenu);

        JMenuItem manual = new JMenuItem("Manual", KeyEvent.VK_M);
        manual.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
        manual.addActionListener(new OpenManual());
        helpMenu.add(manual);

        JMenuItem about = new JMenuItem("About", KeyEvent.VK_A);
        about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.ALT_MASK));
        about.addActionListener(new AboutUniCom());
        helpMenu.add(about);

        frame.setJMenuBar(jMenuBar);
    }

    /**
     * Sets the list of entries to the entriesScrollPane on the view
     *
     * @param entries
     */
    private void setEntriesScrollPane(List<String> labels, List<List<Double>> entries) {
        String[] labelsStringArray = new String[labels.size()];
        String[] labelsEntriesTable = (String[]) ArrayUtils.addAll( new String[]{"#"}, labels.toArray(labelsStringArray) );

        NumberFormat numberFormat = new DecimalFormat("##.####");
        Object[][] dataEntriesTable = new Object[entries.size()][entries.get(0).size() + 1];
        for (int i = 0; i < entries.size(); ++i) {
            for (int j = 0; j < entries.get(i).size(); ++j) {
                if (j == 0) {
                    dataEntriesTable[i][j] = i + 1;
                }
                dataEntriesTable[i][j + 1] = numberFormat.format( entries.get(i).get(j) );
            }
        }

        JTable entriesTable = new JTable(new CustomTableModel(dataEntriesTable, labelsEntriesTable));

        /**
         * Add listener for click events on the entriesJTable
         */
        entriesTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JViewport jViewport = entriesScrollPane.getViewport();
                JTable entriesJTable = (JTable) jViewport.getView();

                int row = entriesJTable.rowAtPoint(evt.getPoint());
                int col = entriesJTable.columnAtPoint(evt.getPoint());
                if (row >= 0 && col >= 0 && referenceObjectJRadioButton.isSelected()) {
                    changeReferenceObject(row);
                }
            }
        });

        entriesTable.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );
        entriesTable.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
        entriesTable.getTableHeader().setReorderingAllowed(false);
        entriesTable.setSelectionBackground( Color.WHITE );
        entriesTable.setSelectionForeground( Color.GRAY );
        entriesTable.setRowSelectionInterval(0, 0);

        ListSelectionModel entriesTableSelectionModel = entriesTable.getSelectionModel();
        entriesTableSelectionModel.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
        entriesTableSelectionModel.addListSelectionListener( new GraphSelectionHandler() );

        entriesScrollPane.setViewportView(entriesTable);
    }

    /**
     * Change the reference object for the selected one in the entriesJTable with index row
     *
     * @param row
     */
    private void changeReferenceObject(int row) {
        setNewGraphController.changeReferenceObject(graphData, row);

        /**
         * Reset similarity filter
         */
        similarityFilterScrollBar.setValue(0);
        similarityFilterJCheckBox.setSelected(false);

        /**
         * Reset chart axis order
         */
        naturalOrderJRadioButton.setSelected(true);

        /**
         * Repaint table
         */
        paintEntriesTable();
    }

    /**
     * Applies the similarity filter on the entries table and paints the reference object
     */
    private void paintEntriesTable() {
        List<Double> commonEntriesAreaPercentage = graphData.getCommonEntriesAreaPercentage();

        List<Integer> filteredRows = new ArrayList<>();
        for (int i  = 0; i < commonEntriesAreaPercentage.size(); ++i) {
            if (similarityFilterScrollBar.getValue() >= commonEntriesAreaPercentage.get(i)) {
                filteredRows.add(i);
            }
        }

        JViewport jViewport = entriesScrollPane.getViewport();
        JTable entriesJTable = (JTable) jViewport.getView();
        for (int i = 0; i < entriesJTable.getColumnCount(); i++) {
            entriesJTable.getColumnModel().getColumn(i).setCellRenderer( new SimilarityFilterCellRenderer(filteredRows) );
        }

        similarityFilterJLabel.setText( similarityFilterScrollBar.getValue() + "%" );
        entriesJTable.repaint();
    }

    private class CustomTableModel extends AbstractTableModel {
        private String[] columnNames;

        private Object[][] data;

        public CustomTableModel(Object[][] data, String[] columnNames) {
            this.columnNames = columnNames;
            this.data = data;
        }

        public void setColumnNames(String[] columnNames) {
            this.columnNames = columnNames;
        }

        public void setData(Object[][] data) {
            this.data = data;
        }

        public String[] getColumnNames() {
            return columnNames;
        }

        public Object[][] getData() {
            return data;
        }

        public int getColumnCount() {
            return columnNames.length;
        }

        public int getRowCount() {
            return data.length;
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }

        public Object getValueAt(int row, int col) {
            return data[row][col];
        }

        public boolean isCellEditable(int row, int col) {
            return false;
        }
    }

    private class CSVLoadDialog implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            JFileChooser jFileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV files (*csv)", "csv");
            jFileChooser.setFileFilter(filter);
            int rVal = jFileChooser.showOpenDialog(frame);

            if (rVal == JFileChooser.APPROVE_OPTION) {
                String filename = jFileChooser.getSelectedFile().getName();
                String path = jFileChooser.getCurrentDirectory().toString() + "/";
                GraphData graphDataTemp = loadCSVToGraphController.loadCSVToGraph(chartViewer, path + filename);

                /**
                 * Load graph or show errors to the user if any
                 */
                if ( loadCSVToGraphController.getLoadCSVToGraphError().isEmpty() ) {
                    graphData = graphDataTemp;
                    setEntriesScrollPane( graphData.getLabels(), graphData.getEntries() );

                    /**
                     * Set visibility on the interface
                     */
                    loadCSVButton.setVisible(false);
                    similarityLabel.setVisible(true);
                    similarityLabel.setOpaque(true);
                    entriesScrollPane.setVisible(true);
                    graphicAreaScrollPane.setVisible(true);
                    controlPanelJPanel.setVisible(true);
                    chartViewerJPanel.setVisible(true);

                    /**
                     * Enable 'Generate Results...' menu option
                     */
                    JMenu jMenu = jMenuBar.getMenu(0);
                    JMenuItem jMenuItem;
                    for (int i = 0; i < jMenu.getItemCount(); ++i) {
                        jMenuItem = jMenu.getItem(i);
                        if (jMenuItem.getText() == "Generate Results...") {
                            jMenuItem.setEnabled(true);
                        }
                    }

                    /**
                     * Set area table
                     */
                    String[] graphicAreaTableColumnNames = {"", "Area"};
                    Object[][] graphicAreaTableData = {
                            {"Reference Object", String.format( "%.4f", graphData.getReferenceArea() ) },
                            {"Compare Object", String.format( "%.4f", graphData.getEntryArea(0) ) },
                            {"Shared", String.format( "%.4f", graphData.getCommonEntryArea(0) ) }
                    };
                    JTable graphicAreaTable = new JTable( new CustomTableModel(graphicAreaTableData,
                            graphicAreaTableColumnNames));
                    graphicAreaTable.setRowSelectionAllowed(false);
                    graphicAreaTable.getTableHeader().setReorderingAllowed(false);
                    graphicAreaScrollPane.setViewportView(graphicAreaTable);

                    /**
                     * Set dimension of graphicAreaScrollPane from the dimension of the table
                     */
                    Dimension graphicAreaTablePreferredSize = graphicAreaTable.getPreferredSize();
                    graphicAreaScrollPane.setPreferredSize( new Dimension(graphicAreaTablePreferredSize.width,
                            graphicAreaTable.getRowHeight()*4 + 5) );
                    graphicAreaScrollPane.setMinimumSize( new Dimension(graphicAreaTablePreferredSize.width,
                            graphicAreaTable.getRowHeight()*4 + 5) );

                    /**
                     * Set similarity label
                     */
                    similarityLabel.setText( String.format( "%.2f", graphData.getCommonEntryAreaPercentage(0) ) + "%" );

                    /**
                     * Set reference color row in entries table
                     */
                    paintEntriesTable();

                    /**
                     * Set similarity filter bar to 0
                     */
                    similarityFilterScrollBar.setValue(0);

                    /**
                     * Set chart axis order to 'Natural' and selector to 'Compare Object'
                     */
                    naturalOrderJRadioButton.setSelected(true);
                    compareObjectJRadioButton.setSelected(true);
                }
                else {
                    JOptionPane.showMessageDialog(null, loadCSVToGraphController.getLoadCSVToGraphError(), "Error!",
                            JOptionPane.ERROR_MESSAGE);
                    loadCSVToGraphController.cleanLoadCSVToGraphErrors();
                }
            }
            else if (rVal == JFileChooser.CANCEL_OPTION) {

            }
        }
    }

    private class GenerateResults implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JFileChooser jFileChooser = newJFileChooserWithConfirmDialog();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV files (*csv)", "csv");
            jFileChooser.setFileFilter(filter);
            int rVal = jFileChooser.showSaveDialog(frame);

            if ( rVal == JFileChooser.APPROVE_OPTION ) {
                File csvFile = jFileChooser.getSelectedFile();
                String csvString = generateCSVController.generateCSVController(graphData);

                PrintWriter out;
                try {
                    String csvFileAbsolutePath = csvFile.getAbsolutePath();
                    if (!csvFileAbsolutePath.endsWith(".csv")) {
                        csvFileAbsolutePath += ".csv";
                    }
                    out = new PrintWriter(csvFileAbsolutePath);
                    out.println(csvString);
                    out.close();
                }
                catch (Exception exception) {
                    JOptionPane.showMessageDialog(null, "Error saving the csv file", "Error!", JOptionPane.ERROR_MESSAGE);
                }

            }
            else if ( rVal == JFileChooser.CANCEL_OPTION ) {

            }

        }

        /**
         * Instantiates a new JFileChooser with a confirm dialog when saving a new file that already exists.
         *
         * @return
         */
        private JFileChooser newJFileChooserWithConfirmDialog() {
            return new JFileChooser(){
                        @Override
                        public void approveSelection(){
                            File f = getSelectedFile();
                            if(f.exists() && getDialogType() == SAVE_DIALOG){
                                int result = JOptionPane.showConfirmDialog(this,"The file exists, overwrite?",
                                        "Existing file",JOptionPane.YES_NO_CANCEL_OPTION);
                                switch(result){
                                    case JOptionPane.YES_OPTION:
                                        super.approveSelection();
                                        return;
                                    case JOptionPane.NO_OPTION:
                                        return;
                                    case JOptionPane.CLOSED_OPTION:
                                        return;
                                    case JOptionPane.CANCEL_OPTION:
                                        cancelSelection();
                                        return;
                                }
                            }
                            super.approveSelection();
                        }
                    };
        }
    }

    private static class QuitUniCom implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    private static class OpenManual implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                String inputPdf = "resources/manual.pdf";
                Path tempOutput = Files.createTempFile("TempManual", ".pdf");
                tempOutput.toFile().deleteOnExit();
                try ( InputStream is = UniCom.class.getClassLoader().getResourceAsStream(inputPdf) ) {
                    Files.copy(is, tempOutput, StandardCopyOption.REPLACE_EXISTING);
                }
                if ( Desktop.isDesktopSupported() ) {
                    Desktop dTop = Desktop.getDesktop();
                    if ( dTop.isSupported(Desktop.Action.OPEN) ) {
                        dTop.open( tempOutput.toFile() );
                    }
                }
            } catch (IOException ex) {

            }
        }
    }

    private static class AboutUniCom implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            /**
             * Get image from resources and create icon
             */
            java.net.URL url = ClassLoader.getSystemResource("resources/icon.png");
            Toolkit kit = Toolkit.getDefaultToolkit();
            Image image = kit.createImage(url);
            Icon icon = new ImageIcon(image);

            JOptionPane.showMessageDialog(null, "UniCom by Daniel Otal Rodríguez. 2016", "About",
                    JOptionPane.INFORMATION_MESSAGE, icon);
        }
    }

    private class GraphSelectionHandler implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent e) {
            if ( !e.getValueIsAdjusting() ) {
                JViewport jViewport = entriesScrollPane.getViewport();
                JTable entriesJTable = (JTable) jViewport.getView();
                int entryId = entriesJTable.getSelectedRow();

                if ( referenceObjectJRadioButton.isSelected() ) {
                    changeReferenceObject(entryId);
                }

                setNewGraphController.setNewGraph(chartViewer, graphData, naturalOrderJRadioButton.isSelected(), entryId);

                /**
                 * Set area table
                 */
                double referenceArea;
                double entryArea;
                double commonEntryArea;
                if (naturalOrderJRadioButton.isSelected()) {
                    referenceArea = graphData.getReferenceArea();
                    entryArea = graphData.getEntryArea(entryId);
                    commonEntryArea = graphData.getCommonEntryArea(entryId);
                } else {
                    referenceArea = graphData.getReferenceOwaArea();
                    entryArea = graphData.getEntryOwaArea(entryId);
                    commonEntryArea = graphData.getCommonEntryOwaArea(entryId);
                }
                Object[][] graphicAreaTableData = {
                        {"Reference Object", String.format("%.4f", referenceArea)},
                        {"Compare Object", String.format("%.4f", entryArea)},
                        {"Shared", String.format("%.4f", commonEntryArea)}
                };

                JTable graphicAreaTable = (JTable) graphicAreaScrollPane.getViewport().getView();
                CustomTableModel graphicAreaTableModel = (CustomTableModel) graphicAreaTable.getModel();
                graphicAreaTableModel.setData(graphicAreaTableData);
                graphicAreaTable.setModel(graphicAreaTableModel);
                graphicAreaScrollPane.setViewportView(graphicAreaTable);

                /**
                 * Set similarity label
                 */
                if (naturalOrderJRadioButton.isSelected()) {
                    similarityLabel.setText(String.format("%.2f", graphData.getCommonEntryAreaPercentage(entryId)) + "%");
                } else {
                    similarityLabel.setText(String.format("%.2f", graphData.getCommonEntryOwaAreaPercentage(entryId)) + "%");
                }
            }
        }
    }

    private class SimilarityScrollBarChangeListener implements ChangeListener {
        public void stateChanged(ChangeEvent changeEvent) {
            paintEntriesTable();
        }
    }

    private class SimilarityFilterCellRenderer extends DefaultTableCellRenderer {

        private List<Integer> filteredRows;

        public SimilarityFilterCellRenderer( List<Integer> filteredRows ) {
            this.filteredRows = filteredRows;
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if ( row == graphData.getReferenceIndex() ) {
                c.setBackground( new Color(222,150,150) );
            }
            else {
                if ( filteredRows.contains(row) && similarityFilterJCheckBox.isSelected() ) {
                    c.setBackground( new Color(135, 206, 250) );
                }
                else {
                    c.setBackground( Color.WHITE );
                }
            }
            return c;
        }
    }

    private class OrderSelectionRadioListener implements ActionListener {
        public void actionPerformed(ActionEvent e){
            JRadioButton button = (JRadioButton) e.getSource();
            JViewport jViewport = entriesScrollPane.getViewport();
            JTable entriesJTable = (JTable) jViewport.getView();

            if ( button.getText().equals("Natural") ) {
                setNewGraphController.setNewGraph(chartViewer, graphData, true, entriesJTable.getSelectedRow());
            } else if ( button.getText().equals("OWA") ) {
                setNewGraphController.setNewGraph(chartViewer, graphData, false, entriesJTable.getSelectedRow());
            }
        }
    }

    private class SimilarityFilterJCheckboxListener implements ActionListener {
        public void actionPerformed(ActionEvent e){
            paintEntriesTable();
        }
    }
}