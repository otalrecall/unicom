package model;

import java.util.List;

public class GraphAreaData {
    private double referenceArea;
    private List<Double> entriesArea;
    private List<Double> commonEntriesArea;
    private List<Double> commonEntriesAreaPercentage;

    public double getReferenceArea() {
        return referenceArea;
    }

    public void setReferenceArea(double referenceArea) {
        this.referenceArea = referenceArea;
    }

    public List<Double> getEntriesArea() {
        return entriesArea;
    }

    public void setEntriesArea(List<Double> entriesArea) {
        this.entriesArea = entriesArea;
    }

    public List<Double> getCommonEntriesArea() {
        return commonEntriesArea;
    }

    public void setCommonEntriesArea(List<Double> commonEntriesArea) {
        this.commonEntriesArea = commonEntriesArea;
    }

    public List<Double> getCommonEntriesAreaPercentage() {
        return commonEntriesAreaPercentage;
    }

    public void setCommonEntriesAreaPercentage(List<Double> commonEntriesAreaPercentage) {
        this.commonEntriesAreaPercentage = commonEntriesAreaPercentage;
    }
}
