package searchengine;

/**
 * Created by Denislav on 4/19/2019.
 */
public interface Score {
    /**
     * The method returns the calculated score
     * @param query String
     * @param website searchengine.Website
     * @param index searchengine.Index
     * @return double
     */
    double getScore(String query, Website website, Index index);
}
