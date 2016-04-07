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
        graphData.setReferenceAreaOwa( referenceOwaArea );

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
        graphData.setEntriesAreaOwa( entriesOwaArea );
        graphData.setCommonEntriesAreaOwa( commonEntriesOwaArea );
        graphData.setCommonEntriesAreaPercentageOwa( commonEntriesOwaAreaPercentage );
    }

    /**
     * Calculates all the Scaled areas for every polygon in the set of graphs graphData (reference and select objects, along with
     * their common area)
     *
     * @param graphData
     */
    private void calculateScaleAreaData(GraphData graphData) {
        double[] referenceScaled = graphData.getReferenceScaledToDouble();
        double referenceAreaScaled = polygonAreaCalculatorService.calculatePolygonArea(referenceScaled);
        graphData.setReferenceAreaScaled( referenceAreaScaled );

        List<Double> entriesAreaScaled = new ArrayList<>();
        List<Double> commonEntriesAreaScaled = new ArrayList<>();
        List<Double> commonEntriesAreaPercentageScaled = new ArrayList<>();
        for (int i = 0; i < graphData.getEntries().size(); ++i) {
            entriesAreaScaled.add( polygonAreaCalculatorService.calculatePolygonArea( graphData.getEntryScaledToDouble(i)) );
            double commonEntryAreaScaled = polygonAreaCalculatorService.calculateCommonPolygonArea(referenceScaled,
                    graphData.getEntryScaledToDouble(i));
            commonEntriesAreaScaled.add( commonEntryAreaScaled );

            double commonAreaPercentageScaled;
            if ( referenceAreaScaled != 0 ) {
                commonAreaPercentageScaled = commonEntryAreaScaled / referenceAreaScaled * 100;
            }
            else {
                commonAreaPercentageScaled = 0;
            }
            commonEntriesAreaPercentageScaled.add( commonAreaPercentageScaled );
        }
        graphData.setEntriesAreaScaled( entriesAreaScaled );
        graphData.setCommonEntriesAreaScaled( commonEntriesAreaScaled );
        graphData.setCommonEntriesAreaPercentageScaled( commonEntriesAreaPercentageScaled );
    }


    /**
     * Calculates all the Scaled OWA areas for every polygon in the set of graphs graphData (reference and select objects,
     * along with their common area)
     *
     * @param graphData
     */
    public void calculateScaleOwaAreaData(GraphData graphData) {
        double[] reference = sortEntryToOwa( graphData.getReferenceScaledToDouble(), graphData.getOwaOrder() );
        double referenceOwaAreaScaled = polygonAreaCalculatorService.calculatePolygonArea(reference);
        graphData.setReferenceAreaScaledOwa( referenceOwaAreaScaled );

        List<Double> entriesOwaAreaScaled = new ArrayList<>();
        List<Double> commonEntriesOwaAreaScaled = new ArrayList<>();
        List<Double> commonEntriesOwaAreaPercentageScaled = new ArrayList<>();
        for (int i = 0; i < graphData.getEntries().size(); ++i) {
            double[] entry = sortEntryToOwa( graphData.getEntryScaledToDouble(i), graphData.getOwaOrder() );
            entriesOwaAreaScaled.add( polygonAreaCalculatorService.calculatePolygonArea(entry) );
            double commonEntryOwaAreaScaled = polygonAreaCalculatorService.calculateCommonPolygonArea( reference, entry );
            commonEntriesOwaAreaScaled.add( commonEntryOwaAreaScaled );

            double commonOwaAreaPercentageScaled;
            if ( referenceOwaAreaScaled != 0 ) {
                commonOwaAreaPercentageScaled = commonEntryOwaAreaScaled / referenceOwaAreaScaled * 100;
            }
            else {
                commonOwaAreaPercentageScaled = 0;
            }

            commonEntriesOwaAreaPercentageScaled.add( commonOwaAreaPercentageScaled );
        }
        graphData.setEntriesAreaScaledOwa( entriesOwaAreaScaled );
        graphData.setCommonEntriesAreaScaledOwa( commonEntriesOwaAreaScaled );
        graphData.setCommonEntriesAreaPercentageScaledOwa( commonEntriesOwaAreaPercentageScaled );
    }

    /**
     * Calculates the OWA Order for the reference object and the scaled reference object of graphData
     * and saves them in the object graphData
     *
     * @param graphData
     */
    public void calculateOwaOrder(GraphData graphData) {
        List<Double> reference = new ArrayList<>(graphData.getReference());
        List<Double> referenceScaled = new ArrayList<>(graphData.getReferenceScaled());

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
     * Scalates all entries and reference object from graphdata and its area results
     *
     * @param graphData
     */
    public void calculateScaledChartAndAreaData(GraphData graphData) {
        List<List<Double>> entries = graphData.getEntries();

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

            graphData.setEntriesScaled( entriesScaledList );
            graphData.setReferenceScaled( entriesScaledList.get(0) );

            calculateScaleAreaData(graphData);
        }
    }

    /**
     * Changes the reference object for the entry from the entries table with the index entryId
     *
     * @param graphData
     * @param entryId
     */
    public void changeReferenceObject(GraphData graphData, int entryId) {
        List<Double> newReference = graphData.getEntries().get(entryId);
        List<Double> newReferenceScaled = graphData.getEntriesScaled().get(entryId);
        if ( newReference != graphData.getReference() ) {
            graphData.setReference( newReference );
            graphData.setReferenceScaled( newReferenceScaled );

            calculateOwaOrder( graphData );
            calculateAreaData( graphData );
            calculateScaleAreaData( graphData );
            calculateOwaAreaData( graphData );
            calculateScaleOwaAreaData( graphData );
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
