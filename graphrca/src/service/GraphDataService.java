package service;

import model.GraphAreaData;
import model.GraphData;
import model.GraphTableData;

import java.util.ArrayList;
import java.util.List;

public class GraphDataService {

    private PolygonAreaCalculatorService polygonAreaCalculatorService;

    public GraphDataService() {
        polygonAreaCalculatorService = new PolygonAreaCalculatorService();
    }

    /**
     * Calculates all the areas for every polygon in the set of graphs graphData (reference and select objects, along with
     * their common area)
     *
     * @param graphTableData
     * @param graphAreaData
     */
    public void calculateAreaData( GraphTableData graphTableData, GraphAreaData graphAreaData, boolean isOwaOrder,
                                  List<Integer> owaOrder ) {
        double[] reference;
        if (isOwaOrder) {
            reference = sortEntryToOwa( graphTableData.getReferenceToDouble(), owaOrder );
        } else {
            reference = graphTableData.getReferenceToDouble();
        }
        double referenceArea = polygonAreaCalculatorService.calculatePolygonArea(reference);
        graphAreaData.setReferenceArea( referenceArea );

        List<Double> entriesArea = new ArrayList<>();
        List<Double> commonEntriesArea = new ArrayList<>();
        List<Double> commonEntriesAreaPercentage = new ArrayList<>();
        for (int i = 0; i < graphTableData.getEntries().size(); ++i) {
            double[] entry;
            if ( isOwaOrder ) {
                entry = sortEntryToOwa( graphTableData.getEntryToDouble(i), owaOrder );
            } else {
                entry = graphTableData.getEntryToDouble(i);
            }
            entriesArea.add( polygonAreaCalculatorService.calculatePolygonArea( entry ) );
            double commonEntryArea = polygonAreaCalculatorService.calculateCommonPolygonArea( reference, entry );
            commonEntriesArea.add( commonEntryArea );

            double commonAreaPercentage;
            if ( referenceArea != 0 ) {
                commonAreaPercentage = commonEntryArea / referenceArea * 100;
            }
            else {
                commonAreaPercentage = 0;
            }
            commonEntriesAreaPercentage.add( commonAreaPercentage );
        }
        graphAreaData.setEntriesArea( entriesArea );
        graphAreaData.setCommonEntriesArea( commonEntriesArea );
        graphAreaData.setCommonEntriesAreaPercentage( commonEntriesAreaPercentage );
    }

    /**
     * Calculates the OWA Order for the reference object and the scaled reference object of graphData
     * and saves them in the object graphData
     *
     * @param graphData
     */
    public void calculateOwaOrder(GraphData graphData) {
        List<Double> reference = new ArrayList<>(graphData.getGraphTableData().getReference());
        List<Double> referenceScaled = new ArrayList<>(graphData.getGraphTableDataScaled().getReference());

        graphData.setOwaOrder( calculateOwaOrderInList(reference) );
        graphData.setOwaOrderScaled( calculateOwaOrderInList( referenceScaled ) );
    }

    /**
     * Sorts the positions of the array entry under the OWA Order
     *
     * @param entry
     * @param owaOrder
     * @return
     */
    public double[] sortEntryToOwa(double[] entry, List<Integer> owaOrder) {
        double[] newEntry = new double[entry.length];
        for ( int i = 0; i < entry.length; ++i ) {
            newEntry[i] = entry[ owaOrder.get(i) ];
        }

        return newEntry;
    }

    /**
     * Sorts the labels under the OWA Order
     *
     * @param labels
     * @param owaOrder
     * @return
     */
    public String[] sortLabelsToOwa(List<String> labels, List<Integer> owaOrder) {
        String[] newLabels = new String[labels.size()];
        for ( int i = 0; i < labels.size(); ++i ) {
            newLabels[i] = labels.get(owaOrder.get(i));
        }

        return newLabels;
    }

    /**
     * Scalates all entries and reference object from graphdata
     *
     * @param graphData
     */
    public void scaleTableData(GraphData graphData) {
        List<List<Double>> entries = graphData.getGraphTableData().getEntries();

        if ( entries.size() > 0 ) {
            double[][] entriesScaled = new double[entries.size()][entries.get(0).size()];

            for (int i = 0; i < entries.get(0).size(); ++i) {
                List <Double> columnEntries = new ArrayList<>();
                for (int j = 0; j < entries.size(); ++j) {
                    columnEntries.add(entries.get(j).get(i));
                }

                double minValue = getMinimumValue(columnEntries);
                double maxValue = getMaximumValue(columnEntries);

                for (int j = 0; j < entries.size(); ++j) {
                    entriesScaled[j][i] = scaleValue( entries.get(j).get(i), minValue, maxValue );
                }
            }

            /**
             * Convert double[][] to list
             */
            List<List<Double>> entriesScaledList = new ArrayList<>();
            for (int i = 0; i < entriesScaled.length; ++i){
                List<Double> entryScaledList = new ArrayList<>();
                for (int j = 0; j < entriesScaled[i].length; ++j){
                    entryScaledList.add( entriesScaled[i][j] );
                }
                entriesScaledList.add( entryScaledList );
            }

            graphData.getGraphTableDataScaled().setEntries( entriesScaledList );
            graphData.getGraphTableDataScaled().setReferenceIndex( 0 );
        }
    }

    /**
     * Changes the reference object for the entry from the entries table with the index entryId
     *
     * @param graphData
     * @param entryId
     */
    public void changeReferenceObject(GraphData graphData, int entryId) {
        if ( entryId != graphData.getGraphTableData().getReferenceIndex() ) {
            graphData.getGraphTableData().setReferenceIndex( entryId );
            graphData.getGraphTableDataScaled().setReferenceIndex( entryId );

            calculateOwaOrder( graphData );

            calculateAreaData( graphData.getGraphTableData(), graphData.getGraphAreaData(), false,
                    graphData.getOwaOrder() );
            calculateAreaData( graphData.getGraphTableDataScaled(), graphData.getGraphAreaDataScaled(), false,
                    graphData.getOwaOrderScaled() );
            calculateAreaData( graphData.getGraphTableData(), graphData.getGraphAreaDataOwa(), true,
                    graphData.getOwaOrder() );
            calculateAreaData( graphData.getGraphTableDataScaled(), graphData.getGraphAreaDataScaledOwa(), true,
                    graphData.getOwaOrderScaled() );
        }
    }

    /**
     * Calculates the OWA Order for the list
     *
     * @param list
     * @return
     */
    private List<Integer> calculateOwaOrderInList(List<Double> list) {
        List<Integer> owaOrder = new ArrayList<>();;
        int referenceSize = list.size();
        for (int i = 0; i < referenceSize; ++i) {
            int maxPosition = getMaxValuePosition(list);
            list.set(maxPosition, -1.0);
            owaOrder.add(maxPosition);
        }

        return owaOrder;
    }

    /**
     * Returns the position of the maximum value of the list
     *
     * @param list
     * @return
     */
    private int getMaxValuePosition(List<Double> list) {
        int maxValuePosition = 0;
        for (int i = 0; i < list.size(); ++i) {
            double number = list.get(i);
            if ( (number > list.get(maxValuePosition)) ) {
                maxValuePosition = i;
            }
        }

        return maxValuePosition;
    }

    /**
     * Returns the minimum value of the list, returns -1 otherwise
     *
     * @param list
     * @return
     */
    private double getMinimumValue(List<Double> list) {
        double minValue = -1;

        for (int i = 0; i < list.size(); ++i) {
            if (i == 0) {
                minValue = list.get(i);
            } else if (minValue > list.get(i)) {
                minValue = list.get(i);
            }
        }

        return minValue;
    }

    /**
     * Returns the maximum value of the list, returns -1 otherwise
     *
     * @param list
     * @return
     */
    private double getMaximumValue(List<Double> list) {
        double maxValue = -1;

        for (int i = 0; i < list.size(); ++i) {
            if (i == 0) {
                maxValue = list.get(i);
            } else if (maxValue < list.get(i)) {
                maxValue = list.get(i);
            }
        }

        return maxValue;
    }

    /**
     * Scales the value between [min, max]
     *
     * @param value
     * @param min
     * @param max
     * @return
     */
    private double scaleValue( double value, double min, double max ) {
        double scaledValue;
        if ( max != min) {
            scaledValue =  (value - min)/(max - min)*100;
        }
        else {
            scaledValue = 0;
        }

        return scaledValue;
    }
}
