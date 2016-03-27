package controller;

import ChartDirector.ChartViewer;
import model.GraphData;
import service.GraphDataService;
import service.MultiRadarChartService;

public class SetNewGraphController {

    private MultiRadarChartService multiRadarChartService;
    private GraphDataService graphDataService;

    public SetNewGraphController() {
        graphDataService = new GraphDataService();
        multiRadarChartService = new MultiRadarChartService(graphDataService);
    }

    public void setNewGraph(ChartViewer chartViewer, GraphData graphData, boolean isNaturalChartAxisOrder,
                            boolean isChartScaled, int entryId) {
        multiRadarChartService.createChart();
        multiRadarChartService.compareObjects(chartViewer, graphData, isNaturalChartAxisOrder, isChartScaled, entryId);
    }

    public void changeReferenceObject(GraphData graphData, int entryId) {
        graphDataService.changeReferenceObject(graphData, entryId);
    }
}