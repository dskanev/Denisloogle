package searchengine;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by Denislav on 4/19/2019.
 */
class FileHelperTest {

    /**
     *  Test that we can parse a valid file
     */
    @Test
    void parseValidFile() {
        List<Website> sites = FileHelper.parseFile("test-resources/test-file.txt");
        assertEquals(6, sites.size());
        assertEquals("United States", sites.get(0).getTitle());
        assertEquals("Denmark", sites.get(1).getTitle());
        assertTrue(sites.get(0).containsWord("the"));
        assertFalse(sites.get(0).containsWord("word_not_contained"));
    }

    /**
     * Test parsing of invalid file
     */
    @Test
    void parseInvalidFile()  {
        List<Website> sites = FileHelper.parseFile("test-resources/test-file-with-errors.txt");
        assertEquals(3, sites.size());
        assertEquals("title1", sites.get(0).getTitle());
        assertEquals("title2", sites.get(1).getTitle());
        assertTrue(sites.get(0).containsWord("word1"));
        assertFalse(sites.get(0).containsWord("word_not_contained"));
    }

    /**
     * Test txt file is not found
     */
    @Test
    void parseTextFileNotFound() {
        List<Website> sites = FileHelper.parseFile("txt_file_not_existing");
        assertEquals(0, sites.size());
    }

}