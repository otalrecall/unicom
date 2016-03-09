package view;

import ChartDirector.ChartViewer;
import controller.GenerateCSVController;
import controller.LoadCSVToGraphController;
import controller.SetNewGraphController;
import model.GraphData;
import org.apache.commons.lang.ArrayUtils;

import javax.swing.*;
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
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class GraphRCA {

    private JFrame frame;
    private GridBagConstraints gridBagConstraints;
    private JMenuBar jMenuBar;
    private JButton loadCSVButton;
    private ChartViewer chartViewer;
    private JScrollPane entriesScrollPane;
    private JScrollPane graphicAreaScrollPane;
    private GraphData graphData;
    private JLabel similarityLabel;
    private JPanel similarityFilterJPanel;
    private JScrollBar similarityFilterScrollBar;
    private JLabel similarityFilterJLabel;

    private LoadCSVToGraphController loadCSVToGraphController;
    private SetNewGraphController setNewGraphController;
    private GenerateCSVController generateCSVController;

    public GraphRCA() {
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
        frame.setTitle("GraphRCA");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setMinimumSize(new Dimension(300, 300));
        frame.setLocationRelativeTo(null);
        frame.setLayout(new GridBagLayout());

        initMenu();

        loadCSVButton = new JButton("Load CSV File");
        loadCSVButton.addActionListener( new CSVLoadDialog() );

        similarityLabel = new JLabel();
        similarityLabel.setToolTipText("Similarity");
        similarityLabel.setHorizontalAlignment(SwingConstants.CENTER);
        similarityLabel.setBorder(BorderFactory.createTitledBorder(new TitledBorder("Similarity")));
        similarityLabel.setVisible(false);

        similarityFilterScrollBar = new JScrollBar(JScrollBar.HORIZONTAL, 0, 0, 0, 100);
        similarityFilterScrollBar.setPreferredSize( new Dimension(140, 20) );
        similarityFilterScrollBar.setToolTipText("Overshadows the object rows with a similarity percentage lower than " +
                "the selected one");
        BoundedRangeModel similarityScrollBarModel = similarityFilterScrollBar.getModel();
        similarityScrollBarModel.addChangeListener( new SimilarityScrollBarChangeListener() );

        similarityFilterJLabel = new JLabel("0%");
        similarityFilterJLabel.setPreferredSize( new Dimension(40, 20) );

        FlowLayout similarityFilterFlowLayout = new FlowLayout(FlowLayout.CENTER, 10, 0);
        similarityFilterJPanel = new JPanel();
        similarityFilterJPanel.setLayout(similarityFilterFlowLayout);
        similarityFilterJPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        similarityFilterJPanel.add(similarityFilterScrollBar);
        similarityFilterJPanel.add(similarityFilterJLabel);
        similarityFilterJPanel.setVisible(false);

        chartViewer = new ChartViewer();
        chartViewer.setVisible(false);

        graphicAreaScrollPane = new JScrollPane();
        graphicAreaScrollPane.setPreferredSize(new Dimension(250, 40));
        graphicAreaScrollPane.setMinimumSize(new Dimension(250, 40));
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
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        frame.add(entriesScrollPane, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        frame.add(similarityFilterJPanel, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 1;
        gridBagConstraints.fill = GridBagConstraints.NONE;
        frame.add(loadCSVButton, gridBagConstraints);
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        frame.add(graphicAreaScrollPane, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        frame.add(similarityLabel, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        frame.add(chartViewer, gridBagConstraints);

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

        JMenuItem quit = new JMenuItem("Quit GraphRCA", KeyEvent.VK_Q);
        quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3, ActionEvent.ALT_MASK));
        quit.addActionListener(new QuitGraphRCA());
        fileMenu.add(quit);

        JMenu helpMenu = new JMenu("Help");
        helpMenu.setMnemonic(KeyEvent.VK_H);
        jMenuBar.add(helpMenu);

        JMenuItem manual = new JMenuItem("Manual", KeyEvent.VK_M);
        manual.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
        helpMenu.add(manual);

        JMenuItem about = new JMenuItem("About", KeyEvent.VK_A);
        about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.ALT_MASK));
        about.addActionListener(new AboutGraphRCA());
        helpMenu.add(about);

        frame.setJMenuBar(jMenuBar);
    }

    /**
     * Sets the list of entries to the entriesScrollPane on the view
     *
     * @param entries
     */
    private void setEntriesScrollPane(List<String> labels, List<Double> reference, List<List<Double>> entries) {
        String[] labelsStringArray = new String[labels.size()];
        String[] labelsEntriesTable = (String[]) ArrayUtils.addAll( new String[]{"#"}, labels.toArray(labelsStringArray) );

        Object[][] dataEntriesTable = new Object[entries.size() + 1][entries.get(0).size() + 1];
        for (int i = 0; i < reference.size(); ++i) {
            if (i == 0) {
                dataEntriesTable[0][0] = 0;
            }
            dataEntriesTable[0][i + 1] = reference.get(i);
        }
        for (int i = 0; i < entries.size(); ++i) {
            for (int j = 0; j < entries.get(i).size(); ++j) {
                if (j == 0) {
                    dataEntriesTable[i + 1][j] = i + 1;
                }
                dataEntriesTable[i + 1][j + 1] = entries.get(i).get(j);
            }
        }

        JTable entriesTable = new JTable(new CustomTableModel(dataEntriesTable, labelsEntriesTable));

        entriesTable.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );
        entriesTable.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
        entriesTable.setSelectionBackground( Color.WHITE );
        entriesTable.setSelectionForeground( Color.GRAY );

        ListSelectionModel entriesTableSelectionModel = entriesTable.getSelectionModel();
        entriesTableSelectionModel.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
        entriesTableSelectionModel.addListSelectionListener( new GraphSelectionHandler(entriesTable) );

        entriesScrollPane.setViewportView(entriesTable);
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
                graphData = loadCSVToGraphController.loadCSVToGraph(chartViewer, path + filename);

                /**
                 * Load graph or show errors to the user if any
                 */
                if ( loadCSVToGraphController.getLoadCSVToGraphError().isEmpty() ) {
                    setEntriesScrollPane( graphData.getLabels(), graphData.getReference(), graphData.getEntries() );

                    /**
                     * Set visibility on the interface
                     */
                    loadCSVButton.setVisible(false);
                    similarityLabel.setVisible(true);
                    similarityLabel.setOpaque(true);
                    entriesScrollPane.setVisible(true);
                    graphicAreaScrollPane.setVisible(true);
                    similarityFilterJPanel.setVisible(true);

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
                            {"Selected Object", String.format( "%.4f", graphData.getEntryArea(0) ) }
                    };
                    JTable graphicAreaTable = new JTable( new CustomTableModel(graphicAreaTableData,
                            graphicAreaTableColumnNames));
                    graphicAreaScrollPane.setViewportView(graphicAreaTable);

                    /**
                     * Set similarity label
                     */
                    similarityLabel.setText( String.format( "%.2f", graphData.getCommonEntryAreaPercentage(0) ) + "%" );

                    /**
                     * Set similarity filter bar to 0
                     */
                    similarityFilterScrollBar.setValue(0);
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

    private class QuitGraphRCA implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    private class AboutGraphRCA implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(null, "GraphRCA by Daniel Otal Rodríguez. 2016", "About",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private class GraphSelectionHandler implements ListSelectionListener {

        private JTable entriesTable;

        public GraphSelectionHandler(JTable entriesTable) {
            this.entriesTable = entriesTable;
        }

        public void valueChanged(ListSelectionEvent e) {
            int entryId = entriesTable.getSelectedRow();

            setNewGraphController.setNewGraph(chartViewer, graphData, entryId);

            /**
             * Set area table
             */
            double selectedObjectArea;
            if ( entryId == 0 ) {
                selectedObjectArea = graphData.getReferenceArea();
            }
            else {
                selectedObjectArea = graphData.getEntryArea(entryId - 1);
            }
            Object[][] graphicAreaTableData = {
                    {"Reference Object", String.format("%.4f", graphData.getReferenceArea())},
                    {"Selected Object", String.format("%.4f", selectedObjectArea)}
            };

            JTable graphicAreaTable = (JTable) graphicAreaScrollPane.getViewport().getView();
            CustomTableModel graphicAreaTableModel = (CustomTableModel) graphicAreaTable.getModel();
            graphicAreaTableModel.setData(graphicAreaTableData);
            graphicAreaTable.setModel(graphicAreaTableModel);
            graphicAreaScrollPane.setViewportView(graphicAreaTable);

            /**
             * Set similarity label
             */
            if (entryId == 0) {
                similarityLabel.setText("100%");
            }
            else {
                similarityLabel.setText(String.format("%.2f", graphData.getCommonEntryAreaPercentage(entryId - 1)) + "%");
            }
        }
    }

    private class SimilarityScrollBarChangeListener implements ChangeListener {
        public void stateChanged(ChangeEvent changeEvent) {
            List<Double> commonEntriesAreaPercentage = graphData.getCommonEntriesAreaPercentage();

            List<Integer> filteredRows = new ArrayList<Integer>();
            for (int i  = 0; i < commonEntriesAreaPercentage.size(); ++i) {
                if (similarityFilterScrollBar.getValue() >= commonEntriesAreaPercentage.get(i)) {
                    filteredRows.add(i + 1);
                }
            }

            /**
             * Set filtered row for the reference object
             */
            if ( similarityFilterScrollBar.getValue() == 100 ) {
                filteredRows.add(0);
            }

            JViewport jViewport = entriesScrollPane.getViewport();
            JTable entriesJTable = (JTable) jViewport.getView();
            for (int i = 0; i < entriesJTable.getColumnCount(); i++) {
                entriesJTable.getColumnModel().getColumn(i).setCellRenderer( new SimilarityFilterCellRenderer(filteredRows) );
            }

            similarityFilterJLabel.setText( similarityFilterScrollBar.getValue() + "%" );
            entriesJTable.repaint();
        }
    }

    private static class SimilarityFilterCellRenderer extends DefaultTableCellRenderer {

        private List<Integer> filteredRows;

        public SimilarityFilterCellRenderer( List<Integer> filteredRows ) {
            this.filteredRows = filteredRows;
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if ( filteredRows.contains(row) ) {
                c.setBackground( new Color(135, 206, 250) );
            }
            else {
                c.setBackground( Color.WHITE );
                table.setSelectionForeground( Color.GRAY );
            }
            return c;
        }
    }
}
