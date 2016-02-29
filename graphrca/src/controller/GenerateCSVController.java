package controller;

import model.GraphData;
import service.CSVService;

public class GenerateCSVController {

    private CSVService csvService;

    public GenerateCSVController() {
        csvService = new CSVService();
    }

    public String generateCSVController(GraphData graphData) {
        String csv = csvService.writeCSV(graphData);

        return csv;
    }
}
