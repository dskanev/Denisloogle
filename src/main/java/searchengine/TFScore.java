package searchengine;

/**
 * Created by Denislav on 4/19/2019.
 */
public class TFScore implements Score {

    /**
     * The method calculate the searchengine.TFScore which is the term frequency (e.g. number of times word occur)
     * @param query String
     * @param website searchengine.Website
     * @param index searchengine.Index
     * @return double
     */

    public double getScore(String query, Website website, Index index) {

        double numberOfTimesWordOccour = 0;

        // For each word in words on the website
        for (String word : website.getWords()) {
            // If the word and the s is the same, then increase numberOfTimesWordOccour
            if (word.equals(query)) {
                numberOfTimesWordOccour++;
            }
        }
        return numberOfTimesWordOccour;
    }

}
