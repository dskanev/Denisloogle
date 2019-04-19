package searchengine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Denislav on 4/19/2019.
 */
public class InvertedIndex implements Index{


        private List<Website> listOfWebsites = null;
        protected Map<String, List<Website>> map;
        private double averageNumberOfWords = 0.0;

        /**
         * The method takes a list of websites and build the searchengine.Index
         * @param sites List<searchengine.Website>
         */

        public void build(List<Website> sites) {

            System.out.println("Building index...");

            listOfWebsites = sites;
            int totalNumberOfWords = 0;

            //For each website
            for (Website w: sites) {

                //For each word
                for (String word: w.getWords()) {

                    totalNumberOfWords += 1;

                    //If map already contains the word (key) append the website to the existing list of that key
                    if (map.containsKey(word.toLowerCase())) {

                        // Check that the website has NOT been added as value for the key
                        if (!map.get(word.toLowerCase()).contains(w)) {

                            // Add the website as value for the key
                            map.get(word.toLowerCase()).add(w);

                        }

                    } else {
                        List<Website> myList = new ArrayList<Website>();
                        myList.add(w);
                        map.put(word.toLowerCase(), myList);
                    }
                }
            }

            // Calculate the averageNumberOfWords
            averageNumberOfWords = totalNumberOfWords / listOfWebsites.size();

        }

        /**
         * The method the calculates the number of websites
         * @return int Integer
         */

        public int getNumberOfWebsites() {
            return listOfWebsites.size();
        }


        /**
         * The method that finds the websites, on which the query occurs on
         * @param query String
         * @return List<searchengine.Website>
         */

        public List<Website> lookUp(String query) {

            if (map.containsKey(query.toLowerCase())) {

                // Calculate the description
                List<Website> foundWebsites = map.get(query.toLowerCase());
                for (Website w : foundWebsites) {
                    w.generateDescription(query);
                }

                return map.get(query.toLowerCase());
            }

            return new ArrayList<Website>();

        }

        /**
         * The field is used to calculate the average number of words on a website
         */

        public double getAverageNumberOfWordsOnWebsite() {
            return averageNumberOfWords;
        }

        /**
         * The methods Overrides the toString method by
         * return the class name of the current object and the map
         * @return String
         */
        @Override
        public String toString() {
            return this.getClass().getName() + map;
        }
    }
