package view;

import ChartDirector.ChartViewer;
import controller.GenerateCSVController;
import controller.LoadCSVToGraphController;
import controller.SetNewGraphController;
import model.GraphData;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
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
    private JScrollBar similarityScrollBar;
    private JTextField similarityTextField;

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
        similarityLabel.setHorizontalAlignment(SwingConstants.CENTER);
        similarityLabel.setFont(new Font("Arial", Font.BOLD, 32));
        similarityLabel.setBackground(Color.WHITE);
        similarityLabel.setBorder(BorderFactory.createLineBorder(Color.lightGray));
        similarityLabel.setVisible(false);

        similarityScrollBar = new JScrollBar(JScrollBar.HORIZONTAL, 0, 0, 0, 100);
        BoundedRangeModel similarityScrollBarModel = similarityScrollBar.getModel();
        similarityScrollBarModel.addChangeListener( new SimilarityScrollBarChangeListener() );
        similarityScrollBar.setVisible(false);

        similarityTextField = new JTextField("0%");

        chartViewer = new ChartViewer();
        chartViewer.setVisible(false);

        graphicAreaScrollPane = new JScrollPane();
        graphicAreaScrollPane.setPreferredSize(new Dimension(250, 40));
        graphicAreaScrollPane.setVisible(false);

        entriesScrollPane = new JScrollPane();
        entriesScrollPane.setPreferredSize(new Dimension(250, 40));
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
        frame.add(similarityScrollBar, gridBagConstraints);

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
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        frame.add(similarityLabel, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        //gridBagConstraints.gridwidth = 3;
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
    private void setEntriesScrollPane(List<List<Double>> entries) {
        loadCSVButton.setVisible(false);

        Integer[] entriesIterator = new Integer[entries.size()];
        for (int i = 0; i < entriesIterator.length; ++i) {
            entriesIterator[i] = i + 1;
        }
        JList jList = new JList(entriesIterator);
        jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jList.setLayoutOrientation(JList.VERTICAL);
        jList.setVisibleRowCount(-1);
        jList.addListSelectionListener(new GraphSelectionHandler());

        entriesScrollPane.setViewportView(jList);
        entriesScrollPane.setVisible(true);
        similarityLabel.setVisible(true);
        similarityLabel.setOpaque(true);
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
                graphData = loadCSVToGraphController.loadCSVToGraph(chartViewer, graphicAreaScrollPane, similarityLabel,
                        path + filename);

                /**
                 * Load graph or show errors to the user if any
                 */
                if (loadCSVToGraphController.getLoadCSVToGraphError().isEmpty()) {
                    setEntriesScrollPane(graphData.getEntries());
                    graphicAreaScrollPane.setVisible(true);
                    similarityScrollBar.setVisible(true);

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
        public void valueChanged(ListSelectionEvent e) {
            int entryId = ((JList) e.getSource()).getSelectedIndex();
            setNewGraphController.setNewGraph(chartViewer, graphicAreaScrollPane, similarityLabel, graphData, entryId );
        }
    }

    private class SimilarityScrollBarChangeListener implements ChangeListener {
        public void stateChanged(ChangeEvent changeEvent) {
            List<Double> commonEntriesAreaPercentage = graphData.getCommonEntriesAreaPercentage();

            List<Integer> filteredRows = new ArrayList<Integer>();
            for (int i  = 0; i < commonEntriesAreaPercentage.size(); ++i) {
                if (similarityScrollBar.getValue() >= commonEntriesAreaPercentage.get(i)) {
                    filteredRows.add(i);
                }
            }

            JViewport jViewport = entriesScrollPane.getViewport();
            JList jList = (JList)jViewport.getView();
            jList.setCellRenderer( new SimilarityFilterCellRenderer(filteredRows) );

            similarityTextField.setText( similarityScrollBar.getValue() + "%" );
        }
    }

    private static class SimilarityFilterCellRenderer extends DefaultListCellRenderer {

        private List<Integer> filteredRows;

        public SimilarityFilterCellRenderer( List<Integer> filteredRows ) {
            this.filteredRows = filteredRows;
        }

        public Component getListCellRendererComponent( JList list, Object value, int index, boolean isSelected, boolean cellHasFocus ) {
            Component c = super.getListCellRendererComponent( list, value, index, isSelected, cellHasFocus );
            if ( filteredRows.contains(index) ) {
                c.setBackground( Color.lightGray );
            }
            return c;
        }
    }
}
