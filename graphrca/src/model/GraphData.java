package model;

import java.util.ArrayList;
import java.util.List;

public class GraphData {

    private List<String> labels;
    private List<Double> reference;
    private List<List<Double>> entries;

    private double referenceArea;
    private List<Double> entriesArea;
    private List<Double> commonEntriesArea;
    private List<Double> commonEntriesAreaPercentage;

    private List<Integer> owaOrder;

    private double referenceOwaArea;
    private List<Double> entriesOwaArea;
    private List<Double> commonEntriesOwaArea;
    private List<Double> commonEntriesOwaAreaPercentage;

    public GraphData() {
        labels = new ArrayList<>();
        reference = new ArrayList<>();
        entries = new ArrayList<>();
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public List<Double> getReference() {
        return reference;
    }

    public void setReference(List<Double> reference) {
        this.reference = reference;
    }

    public List<List<Double>> getEntries() {
        return entries;
    }

    public void setEntries(List<List<Double>> entries) {
        this.entries = entries;
    }

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

    public List<Integer> getOwaOrder() {
        return owaOrder;
    }

    public void setOwaOrder(List<Integer> owaOrder) {
        this.owaOrder = owaOrder;
    }

    public double getReferenceOwaArea() {
        return referenceOwaArea;
    }

    public void setReferenceOwaArea(double referenceOwaArea) {
        this.referenceOwaArea = referenceOwaArea;
    }

    public List<Double> getEntriesOwaArea() {
        return entriesOwaArea;
    }

    public void setEntriesOwaArea(List<Double> entriesOwaArea) {
        this.entriesOwaArea = entriesOwaArea;
    }

    public List<Double> getCommonEntriesOwaArea() {
        return commonEntriesOwaArea;
    }

    public void setCommonEntriesOwaArea(List<Double> commonEntriesOwaArea) {
        this.commonEntriesOwaArea = commonEntriesOwaArea;
    }

    public List<Double> getCommonEntriesOwaAreaPercentage() {
        return commonEntriesOwaAreaPercentage;
    }

    public void setCommonEntriesOwaAreaPercentage(List<Double> commonEntriesOwaAreaPercentage) {
        this.commonEntriesOwaAreaPercentage = commonEntriesOwaAreaPercentage;
    }

    public void setCommonEntriesAreaPercentage(List<Double> commonEntriesAreaPercentage) {
        this.commonEntriesAreaPercentage = commonEntriesAreaPercentage;
    }

    /**
     * Tranforms the reference list object to an array of double
     *
     * @return
     */
    public double[] getReferenceToDouble() {
        double[] referenceDouble = new double[reference.size()];
        for (int i = 0; i < referenceDouble.length; i++) {
            referenceDouble[i] = reference.get(i);
        }
        return referenceDouble;
    }

    /**
     * Gets an entry as an array of double from the list of entries
     *
     * @param entryId
     * @return
     */
    public double[] getEntryToDouble(int entryId) {
        double[] entry = new double[entries.get(entryId).size()];
        for (int i = 0; i < entry.length; i++) {
            entry[i] = entries.get(entryId).get(i);
        }
        return entry;
    }

    /**
     * Gets the area of a given entry id
     *
     * @param entryId
     * @return
     */
    public double getEntryArea(int entryId) {
        return entriesArea.get(entryId);
    }

    /**
     * Gets the OWA area of a given entry id
     *
     * @param entryId
     * @return
     */
    public double getEntryOwaArea(int entryId) {
        return entriesOwaArea.get(entryId);
    }

    /**
     * Gets the common area of a given entry id
     *
     * @param entryId
     * @return
     */
    public double getCommonEntryArea(int entryId) {
        return commonEntriesArea.get(entryId);
    }

    /**
     * Gets the common OWA area of a given entry id
     *
     * @param entryId
     * @return
     */
    public double getCommonEntryOwaArea(int entryId) {
        return commonEntriesOwaArea.get(entryId);
    }

    /**
     * Gets the percentage of common area of a given entry id
     *
     * @param entryId
     * @return
     */
    public double getCommonEntryAreaPercentage(int entryId) {
        return commonEntriesAreaPercentage.get(entryId);
    }

    /**
     * Gets the percentage of common OWA area of a given entry id
     *
     * @param entryId
     * @return
     */
    public double getCommonEntryOwaAreaPercentage(int entryId) {
        return commonEntriesOwaAreaPercentage.get(entryId);
    }
}
