package searchengine;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Denislav on 4/19/2019.
 */
public class QueryHandler {

    private Index idx = null;
    private RankingHandler rankingHandler;

    /**
     * The constructor
     * @param idx The index used by the searchengine.QueryHandler and the searchengine.RankingHandler
     */
    public QueryHandler(Index idx) {
        this.idx = idx;
        this.rankingHandler = new RankingHandler(idx);
    }

    /**
     * This method is getting matching websites to the search query
     * A "subquery" has the form "word1 word2 word3 ...".
     * A website matches a subquery if all the words occur on the website.
     * A website matches the whole query, if it matches at least one subquery.
     *
     * @param line the query
     * @return list of websites matching the query
     */
    public List<Website> getMatchingWebsites(String line) {

        // Clear the HashMap that contains Scoring for Websites within the searchengine.RankingHandler object
        rankingHandler.clearScores();

        // Create list for websites
        List<Website> results = new ArrayList<Website>();

        // URL-Filtering
        // Check if query contains "site:"
        if (line.toLowerCase().contains("site:")) {

            // Creates a new URLFilter object and passes on the line (query)
            UrlFilter urlFilter = new UrlFilter(line);

            //Separate into separate queries
            String[] query = urlFilter.getNewQuery().toLowerCase().split(" or ");
            results.addAll(evaluateFullQuery(query));

            //Create filtered list of websites
            List<Website> filteredWebsites = new ArrayList<Website>();

            //Check if the websites url contains the query's URL
            for (Website w : results){
                if (w.getUrl().toLowerCase().contains(urlFilter.getFilteredUrl())){
                    filteredWebsites.add(w);
                }
            }
            // Return the filtered list of webistes
            return filteredWebsites;

        } // Ends URL-Filtering


        // Separate the full query into separate queries
        String[] query = line.toLowerCase().split(" or ");
        results.addAll(evaluateFullQuery(query));



        // Return the filtered list of websites
        return results;

    }

    /**
     * Evaluate the full query of words
     * @param queryWords the array of query words
     * @return list of websites
     */
    private List<Website> evaluateFullQuery(String[] queryWords) {

        List<Website> results = new ArrayList<Website>();

        // For all words inside the queryWords array which was passed on from getMatchingWebsites
        // We have create a List<searchengine.Website> called foundWebsites (which can contain duplicates)
        for (String word : queryWords) {
            // Creating a list to store websites
            List<Website> foundWebsites = new ArrayList<Website>();
            // If the query contains spaces, we want to figure out which websites belongs,
            // to each word
            if (word.contains(" ")) {

                // e.g. "Denmark danish dk"
                foundWebsites = evaluateSubQuery(word.split(" "));

            } else {

                // e.g. "president" / "president OR usa"
                foundWebsites = idx.lookUp(word);

                // Save the score of the websites where the word was found
                saveScoreForWebsiteWith(word, foundWebsites, false);
            }
        }

        // Get the sorted list from the searchengine.RankingHandler
        List<Website> listOfWebsites = rankingHandler.getSortedList();

        return listOfWebsites;
    }

    /**
     * Evaluate a subquery such as e.g. "Danish Queen"
     * @param subWords the array of words in the subquery
     * @return list of websites
     */
    private List<Website> evaluateSubQuery(String[] subWords) {

        List<Website> results = new ArrayList<Website>();
        for (String word : subWords) {

            // Primarily used for URLFilter
            if (!word.equals("")) {

                // Look up for websites matching this word
                List<Website> foundWebsites = idx.lookUp(word);

                // Save the score of the websites where the word was found
                saveScoreForWebsiteWith(word, foundWebsites, true);

                // Add the found websites to the results array
                results.addAll(foundWebsites);

            }

        }

        return results;
    }

    /**
     * Save the score of to the rankingHandler, in order to rank the websites based on their scores
     * @param word query
     * @param websites websites where the query was found
     * @param subQuery indicate if the query is a subQuery (Used in the ranking helper to determine if multiply score, or get the highest score).
     */
    private void saveScoreForWebsiteWith(String word, List<Website> websites, Boolean subQuery) {
        rankingHandler.saveScoreForWebsiteWithWord(word, websites, subQuery);
    }

}