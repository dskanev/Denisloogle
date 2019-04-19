package searchengine;

import java.util.HashMap;

/**
 * Created by Denislav on 4/19/2019.
 */
public class InvertedIndexHashMap extends InvertedIndex {
    /**
     * The method searchengine.InvertedIndexTreeMap sets the map to a TreeMap
     */
    public InvertedIndexHashMap() {
        this.map = new HashMap();
    }
}