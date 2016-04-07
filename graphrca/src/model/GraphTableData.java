package model;

import java.util.ArrayList;
import java.util.List;

public class GraphTableData {
    private List<Double> reference;
    private List<List<Double>> entries;

    public GraphTableData() {
        reference = new ArrayList<>();
        entries = new ArrayList<>();
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
     * Gets the position of the reference in the entries list
     *
     * @return
     */
    public int getReferenceIndex() {
        return entries.indexOf(reference);
    }
}
