package controller;

import ChartDirector.ChartViewer;
import model.GraphData;
import service.CSVService;
import service.GraphDataService;
import service.MultiRadarChartService;

import java.util.List;

public class LoadCSVToGraphController {

    private MultiRadarChartService multiRadarChartService;
    private CSVService csvService;
    private GraphDataService graphDataService;
    private String loadCSVToGraphError;

    public LoadCSVToGraphController() {
        csvService = new CSVService();
        graphDataService = new GraphDataService();
        multiRadarChartService = new MultiRadarChartService(graphDataService);
        loadCSVToGraphError = "";
    }

    public String getLoadCSVToGraphError() {
        return loadCSVToGraphError;
    }

    /**
     * Cleans the errors of the csv and graph loader
     */
    public void cleanLoadCSVToGraphErrors() {
        loadCSVToGraphError = "";
    }

    /**
     * Loads the csv file and the graph with the reference object and the first object to compare
     *
     * @param chartViewer
     * @param path
     * @return
     */
    public GraphData loadCSVToGraph(ChartViewer chartViewer, String path) {
        GraphData graphData = new GraphData();

        try {
            List<List<String>> entries = csvService.readCSV(path);
            graphData = csvService.formatGraphData(entries);

            graphDataService.calculateScaledChartAndAreaData(graphData);
            graphDataService.calculateAreaData(graphData);

            graphDataService.calculateOwaOrder(graphData);

            multiRadarChartService.createChart();
            multiRadarChartService.compareObjects(chartViewer, graphData, true, false, 0);
            chartViewer.setVisible(true);
        }
        catch (NumberFormatException nfe) {
            loadCSVToGraphError = "The format of the csv is not correct. One of the cells is not a number.";
        }
        catch ( Exception e ) {
            loadCSVToGraphError = e.getMessage();
        }

        return graphData;
    }


}
