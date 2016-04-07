package model;

import java.util.ArrayList;
import java.util.List;

public class GraphData {

    private List<String> labels;

    GraphTableData graphTableData;
    GraphTableData graphTableDataScaled;

    GraphAreaData graphAreaData;
    GraphAreaData graphAreaDataScaled;
    GraphAreaData graphAreaDataOwa;
    GraphAreaData graphAreaDataScaledOwa;

    private List<Integer> owaOrder;
    private List<Integer> owaOrderScaled;

    public GraphData() {
        labels = new ArrayList<>();
        graphTableData = new GraphTableData();
        graphTableDataScaled = new GraphTableData();
        graphAreaData = new GraphAreaData();
        graphAreaDataScaled = new GraphAreaData();
        graphAreaDataOwa = new GraphAreaData();
        graphAreaDataScaledOwa = new GraphAreaData();
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public GraphTableData getGraphTableData() {
        return graphTableData;
    }

    public void setGraphTableData(GraphTableData graphTableData) {
        this.graphTableData = graphTableData;
    }

    public GraphTableData getGraphTableDataScaled() {
        return graphTableDataScaled;
    }

    public void setGraphTableDataScaled(GraphTableData graphTableDataScaled) {
        this.graphTableDataScaled = graphTableDataScaled;
    }

    public GraphAreaData getGraphAreaData() {
        return graphAreaData;
    }

    public void setGraphAreaData(GraphAreaData graphAreaData) {
        this.graphAreaData = graphAreaData;
    }

    public GraphAreaData getGraphAreaDataScaled() {
        return graphAreaDataScaled;
    }

    public void setGraphAreaDataScaled(GraphAreaData graphAreaDataScaled) {
        this.graphAreaDataScaled = graphAreaDataScaled;
    }

    public GraphAreaData getGraphAreaDataOwa() {
        return graphAreaDataOwa;
    }

    public void setGraphAreaDataOwa(GraphAreaData graphAreaDataOwa) {
        this.graphAreaDataOwa = graphAreaDataOwa;
    }

    public GraphAreaData getGraphAreaDataScaledOwa() {
        return graphAreaDataScaledOwa;
    }

    public void setGraphAreaDataScaledOwa(GraphAreaData graphAreaDataScaledOwa) {
        this.graphAreaDataScaledOwa = graphAreaDataScaledOwa;
    }

    public List<Integer> getOwaOrder() {
        return owaOrder;
    }

    public void setOwaOrder(List<Integer> owaOrder) {
        this.owaOrder = owaOrder;
    }

    public List<Integer> getOwaOrderScaled() {
        return owaOrderScaled;
    }

    public void setOwaOrderScaled(List<Integer> owaOrderScaled) {
        this.owaOrderScaled = owaOrderScaled;
    }
}
