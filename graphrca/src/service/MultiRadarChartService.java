package service;

import ChartDirector.Chart;
import ChartDirector.ChartViewer;
import ChartDirector.LegendBox;
import ChartDirector.PolarChart;
import model.GraphData;

import java.awt.*;
import java.util.List;

public class MultiRadarChartService {

    private PolarChart polarChart;
    private GraphDataService graphDataService;

    public MultiRadarChartService( GraphDataService graphDataService ) {
        this.graphDataService = graphDataService;
    }

    /**
     * Creates the chart setting all its config values
     *
     */
    public void createChart() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double screenSizeWidth = screenSize.getWidth();
        double screenSizeHeight = screenSize.getHeight();

        polarChart = new PolarChart((int) (screenSizeWidth/1.3), (int) (screenSizeHeight/1.75), Chart.silverColor(),
                0x000000, 1);
        polarChart.addTitle("Radar Chart", "Times New Roman Bold Italic", 15, 0xffffff).setBackground(0x000080);

        /**
         * Set plot area at the center, with radius in pixels, and a white (#ffffff) background
         */
        polarChart.setPlotArea((int) (screenSizeWidth/1.3)/2, (int) (screenSizeHeight/1.75)/2,
                (int) (screenSizeWidth/1.3/2/3), 0xffffff);

        /**
         * Add a legend box at top right corner using 10pt Arial Bold font. Set the background to silver,
         * with 1 pixel 3D border effect.
         */
        LegendBox legendBox = polarChart.addLegend((int) (screenSizeWidth/1.4), 35, true, "Arial Bold", 10);
        legendBox.setAlignment(Chart.TopRight);
        legendBox.setBackground(Chart.silverColor(), Chart.Transparent, 1);
    }

    /**
     * Sets the object graphs into the chartViewer for comparison
     *
     * @param chartViewer
     * @param graphData
     * @param entryId
     */
    public void compareObjects(ChartViewer chartViewer, GraphData graphData, boolean isNaturalChartAxisOrder,
                               boolean isChartScaled, int entryId) {
        /**
         * Apply OWA order
         */
        double[] reference;
        double[] entry;
        String[] labels = new String[graphData.getLabels().size()];
        graphData.getLabels().toArray(labels);

        if ( isChartScaled ) {
            reference = graphData.getGraphTableDataScaled().getReferenceToDouble();
            entry = graphData.getGraphTableDataScaled().getEntryToDouble(entryId);
        }
        else {
            reference = graphData.getGraphTableData().getReferenceToDouble();
            entry = graphData.getGraphTableData().getEntryToDouble(entryId);
        }

        if ( !isNaturalChartAxisOrder ) {
            List<Integer> owaOrder;
            if ( isChartScaled ) {
                owaOrder = graphData.getOwaOrderScaled();
            }
            else {
                owaOrder = graphData.getOwaOrder();
            }

            reference = graphDataService.sortEntryToOwa( reference, owaOrder );
            entry = graphDataService.sortEntryToOwa( entry, owaOrder );
            labels = graphDataService.sortLabelsToOwa( graphData.getLabels(), owaOrder );
        }

        /**
         * Chart creation
         */
        polarChart.removeDynamicLayer();
        polarChart.addAreaLayer(reference, 0x80cc6666, "Reference Object");
        polarChart.addLineLayer(reference, 0xcc6666).setLineWidth(3);
        polarChart.addAreaLayer(entry,0x806666cc, "Compare Object");
        polarChart.addLineLayer(entry, 0x6666cc).setLineWidth(3);
        polarChart.angularAxis().setLabels(labels);

        chartViewer.setChart(polarChart);
    }

    /**
     * Extracts the image of the chart and saves it in the directory with the path url
     *
     * @param url
     */
    public void extractImage(String url) {
        polarChart.makeChart(url);
    }
}
