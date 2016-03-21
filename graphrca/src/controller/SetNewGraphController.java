package controller;

import ChartDirector.ChartViewer;
import model.GraphData;
import service.GraphDataService;
import service.MultiRadarChartService;

import javax.swing.*;

public class SetNewGraphController {

    private MultiRadarChartService multiRadarChartService;
    private GraphDataService graphDataService;

    public SetNewGraphController() {
        graphDataService = new GraphDataService();
        multiRadarChartService = new MultiRadarChartService(graphDataService);
    }

    public void setNewGraph(ChartViewer chartViewer, GraphData graphData, boolean isNaturalChartAxisOrder, int entryId) {
        multiRadarChartService.createChart();
        multiRadarChartService.compareObjects(chartViewer, graphData, isNaturalChartAxisOrder, entryId);
    }

}
