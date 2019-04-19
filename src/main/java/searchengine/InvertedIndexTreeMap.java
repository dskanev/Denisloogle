package searchengine;

import java.util.TreeMap;

/**
 * Created by Denislav on 4/19/2019.
 */
public class InvertedIndexTreeMap extends InvertedIndex {
    /**
     * The method searchengine.InvertedIndexTreeMap sets the map to a TreeMap
     */
    public InvertedIndexTreeMap() {
        this.map = new TreeMap();
    }
}