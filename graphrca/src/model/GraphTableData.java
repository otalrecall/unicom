package model;

import java.util.ArrayList;
import java.util.List;

public class GraphTableData {
    private int referenceIndex;
    private List<List<Double>> entries;

    public GraphTableData() {
        entries = new ArrayList<>();
    }

    public int getReferenceIndex() {
        return referenceIndex;
    }

    public List<Double> getReference() {
        return entries.get(referenceIndex);
    }

    public void setReferenceIndex(int referenceIndex) {
        this.referenceIndex = referenceIndex;
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
        List<Double> reference = getReference();
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
}
