package controller;

import ChartDirector.ChartViewer;
import model.GraphData;
import service.MultiRadarChartService;

import javax.swing.*;

public class SetNewGraphController {

    private MultiRadarChartService multiRadarChartService;

    public SetNewGraphController() {
        multiRadarChartService = new MultiRadarChartService();
    }

    public void setNewGraph(ChartViewer chartViewer, GraphData graphData, int entryId) {
        String[] labels = new String[graphData.getLabels().size()];
        graphData.getLabels().toArray(labels);
        multiRadarChartService.createChart(labels);
        multiRadarChartService.compareObjects(chartViewer, graphData, entryId);
    }

}
