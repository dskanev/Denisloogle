package searchengine;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Denislav on 4/19/2019.
 */
@Configuration
@EnableAutoConfiguration
@Path("/")
public class SearchEngine extends ResourceConfig {
    private static QueryHandler queryHandler;

    public SearchEngine() {
        packages("searchengine");

    }

    /**
     * The main method of our search engine program.
     * Expects exactly one argument being provided. This
     * argument is the filename of the file containing the
     * websites.
     *
     * @param args command line arguments.
     */
    public static void main(String[] args) {

        // Check if filename as an argument for our main method
        if (args.length != 1) {
            System.out.println("** Error ** Filename is missing - Please provide an argument");
            return;
        }

        // Create an ArrayList of websites from the text file
        List<Website> sites = FileHelper.parseFile(args[0]);

        // Check that the websites have been parsed successfully - if not then return and terminate application
        if (sites.size() == 0) { return; }

        // Perform benchmark to determine the fastest index (searchengine.SimpleIndex vs. searchengine.InvertedIndexHashMap vs. searchengine.InvertedIndexTreeMap)
        //searchengine.Benchmark benchmark = new searchengine.Benchmark();
        //benchmark.performBenchmarkFromList(sites);

        // Setup up the searchengine.QueryHandler with the fastest index, and build the index
        Index idx = new InvertedIndexHashMap(); //benchmark.getFastestIndex();
        idx.build(sites);
        queryHandler = new QueryHandler(idx);

        SpringApplication.run(SearchEngine.class);

        // After setup ask for a query word
        System.out.println("Please provide a query word");


    }

    /**
     * This methods handles requests to GET requests at search.
     * It assumes that a GET request of the form "search?query=word" is made.
     *
     * @param response Http response object
     * @param query the query string
     * @return the list of websites matching the query
     */

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("search")
    public SearchResult search(@Context HttpServletResponse response, @QueryParam("query") String query) {
        // Set crossdomain access. Otherwise your browser will complain that it does not want
        // to load code from a different location.
        response.setHeader("Access-Control-Allow-Origin", "*");

        // Start the time
        long startTime = System.nanoTime();

        if (query == null) {
            return new SearchResult(new ArrayList<>(), System.nanoTime() - startTime);
        }

        System.out.println("Handling request for query word \"" + query + "\"");

        // Find matching websites
        List<Website> resultList = queryHandler.getMatchingWebsites(query);

        // Stop the time
        System.out.println("Query took: " + (System.nanoTime() - startTime / 1000) + " microseconds");
        System.out.println("Found " + resultList.size() + " websites.");

        return new SearchResult(resultList, System.nanoTime() - startTime);

    }

}
