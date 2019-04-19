package searchengine;

import java.util.List;

/**
 * Created by Denislav on 4/19/2019.
 */
public class SearchResult {

    public List<Website> resultList;
    public double queryTime;

    /**
     * The method sets the resultList and queryTime
     * @param resultList List<searchengine.Website>
     * @param queryTime double
     */
    public SearchResult(List<Website> resultList, double queryTime) {
        this.resultList = resultList;
        this.queryTime = queryTime;
    }

}