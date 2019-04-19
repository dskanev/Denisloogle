package searchengine;

/**
 * Created by Denislav on 4/19/2019.
 */
public class IDFScore implements Score {

    /**
     * The method getScore returns the iDFScore
     * @param query String
     * @param website searchengine.Website
     * @param index searchengine.Index
     * @return double searchengine.Score
     */

    public double getScore(String query, Website website, Index index) {

        //Check how many websites the word occurs on
        double occurs = (double) index.lookUp(query).size();

        //Gets the total of websites in database
        double numberOfWebsites = (double) index.getNumberOfWebsites();

        //Calculate the IDF searchengine.Score and returns it
        return Math.log(numberOfWebsites / occurs) / Math.log(2);

    }
}

