package service;

import model.GraphData;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
        List<List<String>> entries = new ArrayList<List<String>>();

        try {
            br = new BufferedReader( new FileReader(path) );
            int lineCounter = 0;
            while ( ( line = br.readLine() ) != null ) {
                List<String> entry = Arrays.asList( line.split(csvSplitBy) );
                if (lineCounter == 0) {
                    if ( entry.size() < 2 ) {
                        throw new Exception("Not enough data. Objects must have more than one comparable attribute.");
                    }
                    checkFormat(entry, EntryType.LABEL_TYPE);

                } else {
                    checkFormat(entry, EntryType.NUMERIC_TYPE);
                }

                if (entry.size() > 0 ) {
                    entries.add(entry);
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

        List<String> labels = graphData.getLabels();
        for (int i = 0; i < labels.size(); ++i) {
            csv += labels.get(i) + ";";
        }
        csv += "Area;Common Area;Common Percentage" + newline;

        List<Double> reference = graphData.getReference();
        for (int i = 0; i < reference.size(); ++i) {
            csv += reference.get(i) + ";";
        }
        csv += String.format( "%.4f", graphData.getReferenceArea() ) + ";" +
                String.format( "%.4f", graphData.getReferenceArea() ) + ";100" + newline;

        List<List<Double>> entries = graphData.getEntries();
        List<Double> commonEntriesArea = graphData.getCommonEntriesArea();
        List<Double> commonEntriesAreaPercentage = graphData.getCommonEntriesAreaPercentage();
        for (int i = 0; i < entries.size(); ++i) {
            for (int j = 0; j < entries.get(i).size(); ++j) {
                csv += entries.get(i).get(j) + ";";
            }
            csv += String.format( "%.4f", graphData.getEntryArea(i) ) + ";" +
                    String.format( "%.4f", commonEntriesArea.get(i) ) + ";" +
                    String.format( "%.2f", commonEntriesAreaPercentage.get(i) ) + newline;
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

        List<List<Double>> entries = graphData.getEntries();
        for (int i = 0; i < csvList.size(); ++i) {
            if (i == 0) {
                List<String> labels = csvList.get(i);
                graphData.setLabels(labels);
            }
            else if (i == 1) {
                List<Double> reference = graphData.getReference();
                for(int j=0; j < csvList.get(i).size(); j++) {
                    if ( csvList.get(i).get(j).isEmpty() ) {
                        reference.add( 0.0 );
                    }
                    else {
                        reference.add( Double.valueOf(csvList.get(i).get(j)) );
                    }
                }
                graphData.setReference(reference);
            } else {
                List<Double> entry = new ArrayList<Double>();
                for(int j=0; j < csvList.get(i).size(); j++) {
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
        graphData.setEntries(entries);

        return graphData;
    }

    /**
     * Checks if the CSV format is correct, otherwise throws an exception
     *
     * @param entry
     * @param entryType
     * @throws NumberFormatException
     */
    private void checkFormat(List<String> entry, EntryType entryType) throws NumberFormatException {
        switch (entryType) {
            case LABEL_TYPE:

                break;
            case NUMERIC_TYPE:
                for (int i = 0; i < entry.size(); i++) {
                    if (!entry.get(i).isEmpty()) {
                        Double.parseDouble(entry.get(i));
                    }
                }
                break;
        }
    }
}
