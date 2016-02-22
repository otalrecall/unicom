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

        List<Double> entriesArea = new ArrayList<Double>();
        List<Double> commonEntriesArea = new ArrayList<Double>();
        List<Double> commonEntriesAreaPercentage = new ArrayList<Double>();
        for (int i = 0; i < graphData.getEntries().size(); ++i) {
            entriesArea.add( polygonAreaCalculatorService.calculatePolygonArea( graphData.getEntryToDouble(i)) );
            double commonEntryArea = polygonAreaCalculatorService.calculateCommonPolygonArea(reference,
                    graphData.getEntryToDouble(i));
            commonEntriesArea.add( commonEntryArea );
            commonEntriesAreaPercentage.add( commonEntryArea / referenceArea * 100);
        }
        graphData.setEntriesArea( entriesArea );
        graphData.setCommonEntriesArea( commonEntriesArea );
        graphData.setCommonEntriesAreaPercentage( commonEntriesAreaPercentage );
    }
}
