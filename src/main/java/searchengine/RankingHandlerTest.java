package searchengine;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by Denislav on 4/19/2019.
 */
class RankingHandlerTest {

    // The searchengine.RankingHandler is currently using the 'searchengine.BM25Score'
    private RankingHandler rh = null;
    private Index idx = new InvertedIndexTreeMap();

    private List<Website> sites = new ArrayList();

    /**
     * Before each test this method is being called to setup and build the indexes from new.
     */
    @BeforeEach
    void setUp() {

        sites = new ArrayList();
        sites.add(new Website("1.com","example1", Arrays.asList("uniqueWord", "regularWord")));
        sites.add(new Website("2.com","example2", Arrays.asList("regularWord", "regularWord")));
        sites.add(new Website("3.com","example3", Arrays.asList("regularWord, regularWord", "regularWord", "regularWord", "uniqueWord")));

        idx = new InvertedIndexTreeMap();
        idx.build(sites);

        rh = new RankingHandler(idx);

    }

    /**
     * After each test this method is being called to clear and release the indexes from memory
     */
    @AfterEach
    void tearDown() {
        sites = null;
    }

    /**
     * Test a valid query ranking is correct
     */
    @Test
    void testQueryRanking() {

        rh.saveScoreForWebsiteWithWord("uniqueWord", idx.lookUp("uniqueWord"), false);
        List<Website> sortedListOfWebsites = rh.getSortedList();

        int counter = 0;

        //Check that websites are in the order we want to.
        for (Website w:sortedListOfWebsites) {

            //Check that the website is matching the one expected
            if (counter == 0) {
                assertEquals(sites.get(0), w); //Website1
            } else if (counter == 1) {
                assertEquals(sites.get(2), w); //Website3
            }
            counter++;
        }

    }

    /**
     * Test a valid query ranking is correct for subQuery
     */
    @Test
    void testSubQueryRanking() {

        List<String> subQuery = Arrays.asList("regularWord", "uniqueWord");

        for (String query : subQuery) {
            rh.saveScoreForWebsiteWithWord(query, idx.lookUp(query), true);
        }
        List<Website> sortedListOfWebsites = rh.getSortedList();


        int counter = 0;

        //Check that websites are in the order we want to.
        for (Website w:sortedListOfWebsites) {

            //Check that the website is matching the one expected
            if (counter == 0) {
                assertEquals(sites.get(0), w); //Website1
            } else if (counter == 1) {
                assertEquals(sites.get(2), w); //Website3
            } else if (counter == 2) {
                assertEquals(sites.get(1), w); //Website2
            }

            counter++;
        }

    }

}