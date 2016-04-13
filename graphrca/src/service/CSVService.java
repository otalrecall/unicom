package service;

import model.GraphData;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVService {

    private enum EntryType {
        LABEL_TYPE, NUMERIC_TYPE
    }

    /**
     * Reads the CSV and converts it into java List
     *
     * @param path
     * @return
     */
    public List<List<String>> readCSV( String path ) throws Exception {
        BufferedReader br = null;
        String line = "";
        String csvSplitBy = ";";
        List<List<String>> entries = new ArrayList<>();
        int labelsNumber = 0;

        try {
            br = new BufferedReader( new FileReader(path) );
            int lineCounter = 0;
            while ( ( line = br.readLine() ) != null ) {
                List<String> entry = Arrays.asList( line.split(csvSplitBy) );
                if ( lineCounter == 0 ) {
                    if ( entry.size() < 2 ) {
                        throw new Exception("Not enough data. Objects must have more than one comparable attribute.");
                    }
                    checkFormat(entry, EntryType.LABEL_TYPE);
                    labelsNumber = entry.size();

                } else {
                    checkFormat(entry, EntryType.NUMERIC_TYPE);

                    if( labelsNumber != entry.size()) {
                        /**
                         * If it is not a white line, throws exception
                         */
                        if ( !(entry.size() == 1 && entry.get(0).isEmpty()) ) {
                            throw new Exception("Not enough data. Objects must have the same number of attributes than " +
                                    "the number of labels defined.");
                        }
                    }
                }

                if ( entry.size() > 0 ) {
                    /**
                     * If it is not a white line, adds the entry
                     */
                    if ( !(entry.size() == 1 && entry.get(0).isEmpty()) ) {
                        entries.add(entry);
                    }
                }
                ++lineCounter;
            }
            if (entries.size() < 2) {
                throw new Exception("Not enough data. At least two objects are necessary to be compared.");
            }
        } catch ( FileNotFoundException e ) {
            e.printStackTrace();
        } catch ( IOException e ) {
            e.printStackTrace();
        } finally {
            if ( br != null ) {
                try {
                    br.close();
                } catch ( IOException e ) {
                    e.printStackTrace();
                }
            }
        }

        return entries;
    }

    /**
     * Creates a formatted CSV output with all the data from GraphData
     *
     * @param graphData
     * @return
     */
    public String writeCSV(GraphData graphData) {
        String csv = "";
        String newline = System.getProperty("line.separator");
        NumberFormat numberFormat = new DecimalFormat("##.####");
        NumberFormat numberFormatPercentage = new DecimalFormat("##.##");

        List<String> labels = graphData.getLabels();
        for (int i = 0; i < labels.size(); ++i) {
            csv += labels.get(i) + ";";
        }
        csv += "Area;Common Area;Common Percentage;Scaled Area;Scaled Common Area;Scaled Common Percentage;" +
                "OWA Area;Common OWA Area;Common OWA Percentage;Scaled OWA Area;Scaled Common OWA Area;" +
                "Scaled Common OWA Percentage" + newline;


        List<Double> reference = graphData.getGraphTableData().getReference();
        for (int i = 0; i < reference.size(); ++i) {
            csv += reference.get(i) + ";";
        }
        csv += numberFormat.format( graphData.getGraphAreaData().getReferenceArea() ) + ";" +
                numberFormat.format( graphData.getGraphAreaData().getReferenceArea() ) + ";100;" +
                numberFormat.format( graphData.getGraphAreaDataScaled().getReferenceArea() ) + ";" +
                numberFormat.format( graphData.getGraphAreaDataScaled().getReferenceArea() )  + ";100;" +
                numberFormat.format( graphData.getGraphAreaDataOwa().getReferenceArea() ) + ";" +
                numberFormat.format( graphData.getGraphAreaDataOwa().getReferenceArea() ) + ";100;" +
                numberFormat.format( graphData.getGraphAreaDataScaledOwa().getReferenceArea() ) + ";" +
                numberFormat.format( graphData.getGraphAreaDataScaledOwa().getReferenceArea() ) + ";100" + newline;

        csv += newline;

        List<List<Double>> entries = graphData.getGraphTableData().getEntries();
        for (int i = 0; i < entries.size(); ++i) {
            for (int j = 0; j < entries.get(i).size(); ++j) {
                csv += entries.get(i).get(j) + ";";
            }
            csv += numberFormat.format( graphData.getGraphAreaData().getEntriesArea().get(i) ) + ";" +
                    numberFormat.format( graphData.getGraphAreaData().getCommonEntriesArea().get(i) ) + ";" +
                    numberFormatPercentage.format( graphData.getGraphAreaData().getCommonEntriesAreaPercentage().get(i) ) + ";" +
                    numberFormat.format( graphData.getGraphAreaDataScaled().getEntriesArea().get(i) ) + ";" +
                    numberFormat.format( graphData.getGraphAreaDataScaled().getCommonEntriesArea().get(i) ) + ";" +
                    numberFormatPercentage.format( graphData.getGraphAreaDataScaled().getCommonEntriesAreaPercentage().get(i) ) + ";" +
                    numberFormat.format( graphData.getGraphAreaDataOwa().getEntriesArea().get(i) ) + ";" +
                    numberFormat.format( graphData.getGraphAreaDataOwa().getCommonEntriesArea().get(i) ) + ";" +
                    numberFormatPercentage.format( graphData.getGraphAreaDataOwa().getCommonEntriesAreaPercentage().get(i) ) + ";" +
                    numberFormat.format( graphData.getGraphAreaDataScaledOwa().getEntriesArea().get(i) ) + ";" +
                    numberFormat.format( graphData.getGraphAreaDataScaledOwa().getCommonEntriesArea().get(i) ) + ";" +
                    numberFormatPercentage.format( graphData.getGraphAreaDataScaledOwa().getCommonEntriesAreaPercentage().get(i) ) +
                    newline;
        }

        return csv;
    }

    /**
     * Formats the java List into the object class GraphData
     *
     * @param csvList
     * @return
     */
    public GraphData formatGraphData(List<List<String>> csvList) {
        GraphData graphData = new GraphData();

        List<List<Double>> entries = graphData.getGraphTableData().getEntries();
        for (int i = 0; i < csvList.size(); ++i) {
            if (i == 0) {
                List<String> labels = csvList.get(i);
                graphData.setLabels(labels);
            }
            else {
                List<Double> entry = new ArrayList<>();
                for (int j = 0; j < csvList.get(0).size(); j++) {
                    if ( csvList.get(i).get(j).isEmpty() ) {
                        entry.add( 0.0 );
                    }
                    else {
                        entry.add( Double.valueOf(csvList.get(i).get(j)) );
                    }
                }
                entries.add(entry);
            }
        }
        graphData.getGraphTableData().setReferenceIndex( 0 );
        graphData.getGraphTableData().setEntries( entries );

        return graphData;
    }

    /**
     * Checks if the CSV format is correct, otherwise throws an exception
     *
     * @param entry
     * @param entryType
     * @throws NumberFormatException
     */
    private void checkFormat(List<String> entry, EntryType entryType) throws Exception {
        switch (entryType) {
            case LABEL_TYPE:

                break;
            case NUMERIC_TYPE:
                for (int i = 0; i < entry.size(); i++) {
                    if (!entry.get(i).isEmpty()) {
                        if ( Double.parseDouble(entry.get(i)) < 0) {
                            throw new Exception("The format of the csv is not correct. " +
                                    "Negatives numbers are not allowed.");
                        };
                    }
                }
                break;
        }
    }
}
