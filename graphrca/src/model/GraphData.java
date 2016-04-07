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

    private List<Double> referenceScaled;
    private List<List<Double>> entriesScaled;

    private double referenceAreaScaled;
    private List<Double> entriesAreaScaled;
    private List<Double> commonEntriesAreaScaled;
    private List<Double> commonEntriesAreaPercentageScaled;

    private double referenceAreaOwa;
    private List<Double> entriesAreaOwa;
    private List<Double> commonEntriesAreaOwa;
    private List<Double> commonEntriesAreaPercentageOwa;

    private double referenceAreaScaledOwa;
    private List<Double> entriesAreaScaledOwa;
    private List<Double> commonEntriesAreaScaledOwa;
    private List<Double> commonEntriesAreaPercentageScaledOwa;

    private List<Integer> owaOrder;
    private List<Integer> owaOrderScaled;

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

    public List<Double> getReferenceScaled() {
        return referenceScaled;
    }

    public void setReferenceScaled(List<Double> referenceScaled) {
        this.referenceScaled = referenceScaled;
    }

    public List<List<Double>> getEntriesScaled() {
        return entriesScaled;
    }

    public void setEntriesScaled(List<List<Double>> entriesScaled) {
        this.entriesScaled = entriesScaled;
    }

    public double getReferenceAreaScaled() {
        return referenceAreaScaled;
    }

    public void setReferenceAreaScaled(double referenceAreaScaled) {
        this.referenceAreaScaled = referenceAreaScaled;
    }

    public List<Double> getEntriesAreaScaled() {
        return entriesAreaScaled;
    }

    public void setEntriesAreaScaled(List<Double> entriesAreaScaled) {
        this.entriesAreaScaled = entriesAreaScaled;
    }

    public List<Double> getCommonEntriesAreaScaled() {
        return commonEntriesAreaScaled;
    }

    public void setCommonEntriesAreaScaled(List<Double> commonEntriesAreaScaled) {
        this.commonEntriesAreaScaled = commonEntriesAreaScaled;
    }

    public List<Double> getCommonEntriesAreaPercentageScaled() {
        return commonEntriesAreaPercentageScaled;
    }

    public void setCommonEntriesAreaPercentageScaled(List<Double> commonEntriesAreaPercentageScaled) {
        this.commonEntriesAreaPercentageScaled = commonEntriesAreaPercentageScaled;
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

    public void setCommonEntriesAreaPercentage(List<Double> commonEntriesAreaPercentage) {
        this.commonEntriesAreaPercentage = commonEntriesAreaPercentage;
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

    public double getReferenceAreaOwa() {
        return referenceAreaOwa;
    }

    public void setReferenceAreaOwa(double referenceAreaOwa) {
        this.referenceAreaOwa = referenceAreaOwa;
    }

    public List<Double> getEntriesAreaOwa() {
        return entriesAreaOwa;
    }

    public void setEntriesAreaOwa(List<Double> entriesAreaOwa) {
        this.entriesAreaOwa = entriesAreaOwa;
    }

    public List<Double> getCommonEntriesAreaOwa() {
        return commonEntriesAreaOwa;
    }

    public void setCommonEntriesAreaOwa(List<Double> commonEntriesAreaOwa) {
        this.commonEntriesAreaOwa = commonEntriesAreaOwa;
    }

    public List<Double> getCommonEntriesAreaPercentageOwa() {
        return commonEntriesAreaPercentageOwa;
    }

    public void setCommonEntriesAreaPercentageOwa(List<Double> commonEntriesAreaPercentageOwa) {
        this.commonEntriesAreaPercentageOwa = commonEntriesAreaPercentageOwa;
    }

    public double getReferenceAreaScaledOwa() {
        return referenceAreaScaledOwa;
    }

    public void setReferenceAreaScaledOwa(double referenceAreaScaledOwa) {
        this.referenceAreaScaledOwa = referenceAreaScaledOwa;
    }

    public List<Double> getEntriesAreaScaledOwa() {
        return entriesAreaScaledOwa;
    }

    public void setEntriesAreaScaledOwa(List<Double> entriesAreaScaledOwa) {
        this.entriesAreaScaledOwa = entriesAreaScaledOwa;
    }

    public List<Double> getCommonEntriesAreaScaledOwa() {
        return commonEntriesAreaScaledOwa;
    }

    public void setCommonEntriesAreaScaledOwa(List<Double> commonEntriesAreaScaledOwa) {
        this.commonEntriesAreaScaledOwa = commonEntriesAreaScaledOwa;
    }

    public List<Double> getCommonEntriesAreaPercentageScaledOwa() {
        return commonEntriesAreaPercentageScaledOwa;
    }

    public void setCommonEntriesAreaPercentageScaledOwa(List<Double> commonEntriesAreaPercentageScaledOwa) {
        this.commonEntriesAreaPercentageScaledOwa = commonEntriesAreaPercentageScaledOwa;
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
     * Tranforms the scaled reference list object to an array of double
     *
     * @return
     */
    public double[] getReferenceScaledToDouble() {
        double[] referenceScaledDouble = new double[referenceScaled.size()];
        for (int i = 0; i < referenceScaledDouble.length; i++) {
            referenceScaledDouble[i] = referenceScaled.get(i);
        }
        return referenceScaledDouble;
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
     * Gets an scaled entry as an array of double from the list of entries
     *
     * @param entryId
     * @return
     */
    public double[] getEntryScaledToDouble(int entryId) {
        double[] entryScaled = new double[entriesScaled.get(entryId).size()];
        for (int i = 0; i < entryScaled.length; i++) {
            entryScaled[i] = entriesScaled.get(entryId).get(i);
        }
        return entryScaled;
    }

    /**
     * Gets the position of the reference in the entries list
     *
     * @return
     */
    public int getReferenceIndex() {
        return entries.indexOf(reference);
    }
}
