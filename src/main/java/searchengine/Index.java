package searchengine;

import java.util.List;

/**
 * Created by Denislav on 4/19/2019.
 */
public interface Index {


        /**
         * The method takes a list of websites and build the searchengine.Index
         * @param sites List<searchengine.Website>
         */
        void build(List<Website> sites);

        /**
         * The method the calculates the number of websites
         * @return int Integer
         */
        int getNumberOfWebsites();

        /**
         * The method that finds the websites, on which the query occurs on
         * @param query String
         * @return List<searchengine.Website>
         */
        List<Website> lookUp(String query);

        /**
         * The field is used to calculate the average number of words on a website
         */
        double averageNumberOfWords = 0.0;

        /**
         * The method returns the average number of words on a website
         * and is used for calculating scores
         * @return double
         */
        double getAverageNumberOfWordsOnWebsite();

    }

