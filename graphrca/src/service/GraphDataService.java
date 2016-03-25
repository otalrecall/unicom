package service;

import model.GraphData;

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
     * @param graphData
     */
    public void calculateAreaData(GraphData graphData) {
        double[] reference = graphData.getReferenceToDouble();
        double referenceArea = polygonAreaCalculatorService.calculatePolygonArea(reference);
        graphData.setReferenceArea( referenceArea );

        List<Double> entriesArea = new ArrayList<>();
        List<Double> commonEntriesArea = new ArrayList<>();
        List<Double> commonEntriesAreaPercentage = new ArrayList<>();
        for (int i = 0; i < graphData.getEntries().size(); ++i) {
            entriesArea.add( polygonAreaCalculatorService.calculatePolygonArea( graphData.getEntryToDouble(i)) );
            double commonEntryArea = polygonAreaCalculatorService.calculateCommonPolygonArea(reference,
                    graphData.getEntryToDouble(i));
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
        graphData.setEntriesArea( entriesArea );
        graphData.setCommonEntriesArea( commonEntriesArea );
        graphData.setCommonEntriesAreaPercentage( commonEntriesAreaPercentage );
    }

    /**
     * Calculates all the OWA areas for every polygon in the set of graphs graphData (reference and select objects, along with
     * their common area)
     *
     * @param graphData
     */
    public void calculateOwaAreaData(GraphData graphData) {
        double[] reference = sortEntryToOwa( graphData.getReferenceToDouble(), graphData.getOwaOrder() );
        double referenceOwaArea = polygonAreaCalculatorService.calculatePolygonArea(reference);
        graphData.setReferenceOwaArea( referenceOwaArea );

        List<Double> entriesOwaArea = new ArrayList<>();
        List<Double> commonEntriesOwaArea = new ArrayList<>();
        List<Double> commonEntriesOwaAreaPercentage = new ArrayList<>();
        for (int i = 0; i < graphData.getEntries().size(); ++i) {
            double[] entry = sortEntryToOwa( graphData.getEntryToDouble(i), graphData.getOwaOrder() );
            entriesOwaArea.add( polygonAreaCalculatorService.calculatePolygonArea(entry) );
            double commonEntryOwaArea = polygonAreaCalculatorService.calculateCommonPolygonArea( reference, entry );
            commonEntriesOwaArea.add( commonEntryOwaArea );

            double commonOwaAreaPercentage;
            if ( referenceOwaArea != 0 ) {
                commonOwaAreaPercentage = commonEntryOwaArea / referenceOwaArea * 100;
            }
            else {
                commonOwaAreaPercentage = 0;
            }

            commonEntriesOwaAreaPercentage.add( commonOwaAreaPercentage );
        }
        graphData.setEntriesOwaArea( entriesOwaArea );
        graphData.setCommonEntriesOwaArea( commonEntriesOwaArea );
        graphData.setCommonEntriesOwaAreaPercentage( commonEntriesOwaAreaPercentage );
    }

    /**
     * Calculates the OWA Order for the reference object of graphData and saves it in the object graphData
     *
     * @param graphData
     */
    public void calculateOwaOrder(GraphData graphData) {
        List<Integer> owaOrder = new ArrayList<>();
        List<Double> reference = new ArrayList<>(graphData.getReference());

        int referenceSize = reference.size();
        for (int i = 0; i < referenceSize; ++i) {
            int maxPosition = getMaxValuePosition(reference);
            reference.set(maxPosition, -1.0);
            owaOrder.add(maxPosition);
        }

        graphData.setOwaOrder(owaOrder);
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
     * Changes the reference object for the entry from the entries table with the index entryId
     *
     * @param graphData
     * @param entryId
     */
    public void changeReferenceObject(GraphData graphData, int entryId) {
        List<Double> newReference = graphData.getEntries().get(entryId);
        graphData.setReference( newReference );

        calculateAreaData( graphData );
        calculateOwaAreaData( graphData );
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
}
