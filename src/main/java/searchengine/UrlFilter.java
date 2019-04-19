package searchengine;

/**
 * Created by Denislav on 4/19/2019.
 */
public class UrlFilter {
    private String url;
    private String query;

    public UrlFilter(String line) {
        // We create a String[] to store the line we split around spaces
        String[] query = line.toLowerCase().split(" ");
        String siteLine = "";

        // Then we loop through each String in the list,
        // And when we reach the line with "site:" we assign that to siteLine and break the loop
        for (String s : query) {
            if (s.startsWith("site:")) {
                siteLine = s;
                break;
            }
        }

        // We then make another loop to replace the siteLine with nothing in the element list
        for (int i = 0; i < query.length; i++) {
            if (query[i].equals(siteLine)) {
                query[i] = "";
                break;
            }
        }

        // Then we at last set the url instance variable to siteLine
        this.url = siteLine.replace("site:", "");
        this.query = String.join(" ", query);
    }

    // Getters
    public String getFilteredUrl() {
        return url;
    }
    public String getNewQuery() {
        return query;
    }
}
