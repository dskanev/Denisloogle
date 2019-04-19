package searchengine;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Denislav on 4/19/2019.
 */
public class SimpleIndex implements Index {
    // The instance variables
    private List<Website> sites = null;
    private double averageNumberOfWords = 0.0;

    /**
     * The method takes a list of websites and build the List of Websites
     * @param sites List<searchengine.Website>
     */
    public void build(List<Website> sites) {
        this.sites = sites;
    }

    /**
     * The method that finds the websites, on which the query occurs on
     * @param query String
     * @return List<searchengine.Website>
     */
    public List<Website> lookUp(String query) {

        List<Website> result = new ArrayList<Website>();

        for (Website w: sites) {
            if(w.containsWord(query)){
                result.add(w);
            }

        }

        return result;
    }

    /**
     * The field is used to calculate the average number of words on a website
     */
    public double getAverageNumberOfWordsOnWebsite() {
        return averageNumberOfWords;
    }

    /**
     * The method the calculates the number of websites
     * @return int Integer
     */
    public int getNumberOfWebsites() {
        return sites.size();
    }


    /**
     * The methods Overrides the toString method by
     * phrasing it differently
     * @return String
     */
    @Override
    public String toString() {
        return "searchengine.SimpleIndex{" +
                "sites=" + sites +
                '}';
    }
}
