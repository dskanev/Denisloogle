package searchengine;

/**
 * Created by Denislav on 4/19/2019.
 */
public class BM25Score implements Score {


    private TFScore tfScore = new TFScore();
    private IDFScore idfScore = new IDFScore();
    private double k = 1.75;
    private double b = 0.75;

    /****
     * This method calculates the final searchengine.BM25Score and returns it
     * @param query String
     * @param website searchengine.Website
     * @param index searchengine.Index
     * @return double
     */


    public double getScore(String query, Website website, Index index) {

        double idf = idfScore.getScore(query, website, index); //Word appearence in avg. the greater the more unique
        double tf = tfScore.getScore(query, website, index); //Word appearence
        double dl = website.getWords().size();
        double avdl = index.getAverageNumberOfWordsOnWebsite();
        return idf * tf * (k+1) / (k * (1 - b + b * dl / avdl) + tf);

    }
}
