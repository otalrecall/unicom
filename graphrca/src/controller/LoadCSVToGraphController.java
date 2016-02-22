package controller;

import ChartDirector.ChartViewer;
import model.GraphData;
import service.CSVService;
import service.GraphDataService;
import service.MultiRadarChartService;

import javax.swing.*;
import java.util.List;

public class LoadCSVToGraphController {

    private MultiRadarChartService multiRadarChartService;
    private CSVService csvService;
    private GraphDataService graphDataService;
    private String loadCSVToGraphError;

    public LoadCSVToGraphController() {
        multiRadarChartService = new MultiRadarChartService();
        csvService = new CSVService();
        graphDataService = new GraphDataService();
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
    public GraphData loadCSVToGraph(ChartViewer chartViewer, JScrollPane graphicAreaScrollPane, JLabel similarityLabel, String path) {
        GraphData graphData = new GraphData();

        try {
            List<List<String>> entries = csvService.readCSV(path);
            graphData = csvService.formatGraphData(entries);

            graphDataService.calculateAreaData(graphData);

            String[] labels = new String[graphData.getLabels().size()];
            graphData.getLabels().toArray(labels);
            multiRadarChartService.createChart(chartViewer, labels);
            multiRadarChartService.compareObjects(chartViewer, graphicAreaScrollPane, similarityLabel, graphData, 0);
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
