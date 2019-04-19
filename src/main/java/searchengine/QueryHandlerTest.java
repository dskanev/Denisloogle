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
class QueryHandlerTest {

    private QueryHandler qh = null;


    /**
     * Before each test this method is being called to setup and build the indexes from new.
     */
    @BeforeEach
    void setUp() {
        List<Website> sites = new ArrayList<Website>();
        sites.add(new Website("1.com","example1", Arrays.asList("word1", "word2")));
        sites.add(new Website("2.com","example2", Arrays.asList("word2", "word3")));
        sites.add(new Website("3.com","example3", Arrays.asList("word3", "word4", "word5")));

        Index idx = new InvertedIndexTreeMap();
        idx.build(sites);

        qh = new QueryHandler(idx);
    }

    /**
     * After each test this method is being called to clear and release the indexes from memory
     */
    @AfterEach
    void tearDown() {

    }

    // Test for getting websites matching single words
    @Test
    void testSingleWord() {
        assertEquals(1, qh.getMatchingWebsites("word1").size());
        assertEquals("example1", qh.getMatchingWebsites("word1").get(0).getTitle());
        assertEquals(2, qh.getMatchingWebsites("word2").size());
    }

    // Test for getting websites matching multiple words
    @Test
    void testMultipleWords() {
        assertEquals(2, qh.getMatchingWebsites("word1 word2").size());
        assertEquals(1, qh.getMatchingWebsites("word4").size());
        assertEquals(2, qh.getMatchingWebsites("word4 word3 word5").size());
    }

    // Test for getting websites using 'OR' query (e.g. w1-w2 or w3-4)
    @Test
    void testORQueries() {
        assertEquals(3, qh.getMatchingWebsites("word2 OR word3").size());
        assertEquals(2, qh.getMatchingWebsites("word1 OR word4").size());

    }

    // Test for problematic input such as removing duplicates when the search query is the same, and query containing only whitespace
    @Test
    void testCornerCases() {
        assertEquals(1, qh.getMatchingWebsites("word1 OR word1").size());
        assertEquals(1, qh.getMatchingWebsites("word1 OR word1").size());
        assertEquals(0, qh.getMatchingWebsites("    ").size());
    }

}