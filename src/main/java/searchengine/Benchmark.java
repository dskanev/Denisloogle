package searchengine;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Denislav on 4/19/2019.
 */
public class Benchmark {

    // The instance variable
    private Index fastestIndex = new SimpleIndex();


    /**
     * The method that performs the benchmarking on which searchengine.Index is
     * better to apply
     * @param websites List<searchengine.Website>
     */
    void performBenchmarkFromList(List<Website> websites) {

        SimpleIndex simpleIndex = new SimpleIndex();
        InvertedIndexHashMap invertedIndexHash = new InvertedIndexHashMap();
        InvertedIndexTreeMap invertedIndexTree = new InvertedIndexTreeMap();

        //Create a list of all the indexes
        ArrayList<Index> listOfIndexes = new ArrayList<Index>();
        listOfIndexes.add(simpleIndex);
        listOfIndexes.add(invertedIndexHash);
        listOfIndexes.add(invertedIndexTree);

        ArrayList<String> queryWords = new ArrayList<String>() {{
            add("word1");
        }};

        long fastestTime = 999999999;
        for (Index index : listOfIndexes) {

            //Perform benchmark
            index.build(websites);

            // Start the time
            long startTime = System.nanoTime();

            // Look up
            for (String query: queryWords) {
                index.lookUp(query);
            }

            // Stop the time
            long elapsedTime = System.nanoTime() - startTime;

            // Calculate if the elapsedTime is faster than the previous fastest time
            if (fastestTime > elapsedTime) {
                fastestTime = elapsedTime;
                fastestIndex = index;
            }

            DebugHelper.log("Running queries on dataset with" + index.getClass() + " took " + (elapsedTime / 1000) + " microseconds.");

        }

    }


    /**
     * Returns the fastest searchengine.Index
     * @return searchengine.Index
     */
    public Index getFastestIndex() {
        DebugHelper.log("Fastest index: " + fastestIndex.getClass());
        return fastestIndex;
    }
}