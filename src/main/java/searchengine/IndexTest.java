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
class IndexTest {

    private Index simpleIndex = null;
    private Index invertedIndexHashMap = null;
    private Index invertedIndexTreeMap = null;

    /**
     * Before each test this method is being called to setup and build the indexes from new.
     */
    @BeforeEach
    void setUp() {
        List<Website> sites = new ArrayList<Website>();
        sites.add(new Website("example1.com", "example1", Arrays.asList("word1", "word2", "word1")));
        sites.add(new Website("example2.com", "example2", Arrays.asList("word2", "word3")));

        // Build simple index
        simpleIndex = new SimpleIndex();
        simpleIndex.build(sites);

        // Build inverted hashmap index
        invertedIndexHashMap = new InvertedIndexHashMap();
        invertedIndexHashMap.build(sites);

        // Build inverted treemap index
        invertedIndexTreeMap = new InvertedIndexTreeMap();
        invertedIndexTreeMap.build(sites);

    }

    /**
     * After each test this method is being called to clear and release the indexes from memory
     */
    @AfterEach
    void tearDown() {
        simpleIndex = null;
        invertedIndexHashMap = null;
        invertedIndexTreeMap = null;
    }

    /**
     * Test that the built simple index matches the expected outcome
     */
    @Test
    void buildSimpleIndex() {
        assertEquals("searchengine.SimpleIndex{sites=[searchengine.Website{title='example1', url='example1.com', words=[word1, word2, word1]}, searchengine.Website{title='example2', url='example2.com', words=[word2, word3]}]}", simpleIndex.toString());
    }

    /**
     * Test that the built inverted hashmap index matches the expected outcome
     */
    @Test
    void buildInvertedIndexHashMap() {
        assertEquals("searchengine.searchengine.InvertedIndexHashMap{word1=[searchengine.Website{title='example1', url='example1.com', words=[word1, word2, word1]}], word3=[searchengine.Website{title='example2', url='example2.com', words=[word2, word3]}], word2=[searchengine.Website{title='example1', url='example1.com', words=[word1, word2, word1]}, searchengine.Website{title='example2', url='example2.com', words=[word2, word3]}]}", invertedIndexHashMap.toString());
    }

    /**
     * Test that the built inverted treemap index matches the expected outcome
     */
    @Test
    void buildInvertedIndexTreeMap() {
        assertEquals("searchengine.searchengine.InvertedIndexTreeMap{word1=[searchengine.Website{title='example1', url='example1.com', words=[word1, word2, word1]}], word2=[searchengine.Website{title='example1', url='example1.com', words=[word1, word2, word1]}, searchengine.Website{title='example2', url='example2.com', words=[word2, word3]}], word3=[searchengine.Website{title='example2', url='example2.com', words=[word2, word3]}]}", invertedIndexTreeMap.toString());
    }

    /**
     * Test lookup function for all the built indexes
     */
    @Test
    void lookUpIndexes() {
        lookup(simpleIndex);
        lookup(invertedIndexHashMap);
        lookup(invertedIndexTreeMap);
    }

    private void lookup(Index index) {
        assertEquals(1, index.lookUp("word1").size());
        assertEquals(2, index.lookUp("word2").size());
        assertEquals(0, index.lookUp("word4").size());
    }

}