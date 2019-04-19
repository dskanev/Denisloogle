package searchengine;

import java.util.List;

/**
 * Created by Denislav on 4/19/2019.
 */
public class Website {
    private String title;
    private String url;
    private List<String> words;
    private String description;

    public Website(String url, String title, List<String> words) {
        this.url = url;
        this.title = title;
        this.words = words;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public List<String> getWords() {
        return words;
    }

    public String getDescription() {
        return description;
    }

    public void generateDescription(String word){
        int wordIndex = words.indexOf(word);
        List<String> listOfStrings = null;
        description = "";

        if (words.size() < 30) {
            for (String s : words) {
                description += " " + s;
            }
            description += " ...";
            return;
        }

        if (wordIndex <= 15) {
            listOfStrings = words.subList(0, 30);
        } else if (wordIndex > 15 && wordIndex < words.size()-15) {
            listOfStrings = words.subList(wordIndex - 15, wordIndex + 15);
        } else {
            listOfStrings = words.subList(wordIndex - 15, words.size()-1);
        }

        for (String s : listOfStrings) {
            description += " " + s;
        }

        description += " ...";
    }
    /**
     * Method to return boolean of true or false if the words list contains the query
     * @param word String
     * @return Boolean
     */
    public Boolean containsWord(String word) {
        return words.contains(word);
    }

    /**
     * The methods Overrides the toString method by
     * phrasing it differently
     * @return String
     */
    @Override
    public String toString() {
        return "Website{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", words=" + words +
                '}';
    }
    }

