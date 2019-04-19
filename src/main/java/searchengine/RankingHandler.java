package searchengine;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Denislav on 4/19/2019.
 */
public class RankingHandler {

    private HashMap<Website, Double> queryScores = new HashMap();
    private HashMap<Website, Double> subQueryScores = new HashMap();
    private Index idx = null;

    public RankingHandler(Index idx) {
        this.idx = idx;

    }

    /**
     * Assigns a score to each website
     *
     * @param word String
     * @param websites List
     * @param subQuery Boolean
     */
    public void saveScoreForWebsiteWithWord(String word, List<Website> websites, Boolean subQuery) {


        // Calculate score for website
        for (Website w : websites) {

            // Calculate score for website with word
            double scoreForWebsite = calculateScoreFor(word, w);

            // Check if it's a subQuery
            if (subQuery) {

                // Check if the subQueryScores contains the website - return early
                if (!subQueryScores.containsKey(w)) {
                    subQueryScores.put(w, scoreForWebsite);
                } else {
                    subQueryScores.put(w, subQueryScores.get(w) + scoreForWebsite);
                }

            } else {

                // Check if the queryScores contains the website - return early
                if (!queryScores.containsKey(w)) {
                    queryScores.put(w, scoreForWebsite);
                } else {

                    // As it's not a subQuery we want to just update the value IF the bm25newScore is greater/equal than the existing
                    if (queryScores.get(w) <= scoreForWebsite) {
                        queryScores.put(w, scoreForWebsite);
                    }

                }

            }

        }

    }

    /**
     * Creates a list of the two scores map and merges them together
     * Then sorts the list based on the scores and returns it.
     * @return sorted list of websites
     */
    public List<Website> getSortedList() {

        // Create a list of the queryScores + subQueryScores
        List<HashMap<Website, Double>> listOfQueryScores = Arrays.asList(queryScores, subQueryScores);

        // Merge the two maps
        Map<Website, Double> mergedMap = mergeListOfQueries(listOfQueryScores);

        // Sort the map into a list based on the ranking score
        List<Website>listOfWebistes = mergedMap.entrySet().stream().sorted((x, y) -> y.getValue().compareTo(x.getValue())).map(Map.Entry::getKey).collect(Collectors.toList());

        /*
        // Looping through sorted list of websites and prints title and relevant score using searchengine.DebugHelper
        // Used in the report for benchmarking purposes
        for (searchengine.Website w : listOfWebistes) {
            searchengine.DebugHelper.log("Title of website: " + w.getTitle() +  " With searchengine.BM25Score = " + mergedMap.get(w));
        }
        */

        // Return the list of websites
        return listOfWebistes;
    }

    /**
     * Merge the list of scores for query and scores for subQueries
     * @param queries List
     * @return map of searchengine.Website scores
     */
    private Map<Website, Double> mergeListOfQueries(List<HashMap<Website, Double>> queries) {

        Map<Website, Double> websiteScores = new HashMap<Website,Double>();

        // For all the queries - append/merge the scores to websiteScores
        for (HashMap<Website, Double> query : queries) {

            // Add the regular query scores
            for (Map.Entry<Website, Double> entry : query.entrySet()) {
                // Check if the queryScores contains the website - return early
                if (!websiteScores.containsKey(entry.getKey())) {
                    websiteScores.put(entry.getKey(), entry.getValue());
                } else {

                    // As it's not a subQuery we want to just update the value IF the newScore is greater/equal than the existing
                    if (websiteScores.get(entry.getKey()).doubleValue() <= entry.getValue()) {
                        websiteScores.put(entry.getKey(), entry.getValue());
                    }

                }
            }
        }

        return websiteScores;

    }

    /**
     * Calculate the score for website, based on the word (Currently using searchengine.BM25Score)
     * @param word String
     * @param website searchengine.Website
     * @return double
     */
    private double calculateScoreFor(String word, Website website) {

        double score = 0;

        // Get the searchengine.TFScore
        //searchengine.TFScore tfScore = new searchengine.TFScore();
        //score = tfScore.getScore(word, website, idx);

        // Get the searchengine.IDFScore
        //searchengine.IDFScore idfScore = new searchengine.IDFScore();
        //score = idfScore.getScore(word, website, idx);

        // Get the TFIDFScore
        //TFIDFScore tfidfScore = new TFIDFScore();
        //score = tfidfScore.getScore(word, website, idx);

        // Get the searchengine.BM25Score
        BM25Score bm25Score = new BM25Score();
        score = bm25Score.getScore(word, website, idx);

        return score;
    }

    /**
     * Clears both the score maps
     */
    public void clearScores() {
        subQueryScores.clear();
        queryScores.clear();
    }
}

