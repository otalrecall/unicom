package controller;

import service.MultiRadarChartService;

public class ExtractChartController {

    private MultiRadarChartService multiRadarChartService;

    public ExtractChartController(LoadCSVToGraphController loadCSVToGraphController) {
        multiRadarChartService = loadCSVToGraphController.getMultiRadarChartService();
    }

    public void extractChart(String url) {
        multiRadarChartService.extractImage(url);
    }
}
